/*
 * Copyright Samuel Halliday 2009
 * 
 * This file is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this file.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package uk.me.fommil.ff.physics;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import org.ode4j.math.DVector3;
import org.ode4j.ode.DGeom.DNearCallback;
import uk.me.fommil.ff.Direction;
import uk.me.fommil.ff.Pitch;
import uk.me.fommil.ff.PlayerStats;
import uk.me.fommil.ff.Tactics;
import uk.me.fommil.ff.Tactics.BallZone;
import uk.me.fommil.ff.Tactics.PlayerZone;
import uk.me.fommil.ff.Team;
import uk.me.fommil.ff.physics.Player.PlayerState;

/**
 * The model (M) and controller (C) for game play.
 * The coordinate system is a left-handed system with X = East, Y = North, Z = Sky.
 *
 * @author Samuel Halliday
 */
public class GamePhysics extends Physics {

	private static final Logger log = Logger.getLogger(GamePhysics.class.getName());

	static final double MIN_SPEED = 0.1;

	private final GoalkeeperController goalkeeperController;

	@Deprecated // DEBUGGING
	private void debugNaNs() {
		ball.getPosition();
		ball.getVelocity();
		for (Player player : getPlayers()) {
			player.getPosition();
			player.getVelocity();
		}
	}

	static double toAngle(DVector3 vector, double fallback) {
		if (vector.length() == 0)
			return fallback;
		return dePhase(Math.PI / 2 - Math.atan2(vector.get1(), vector.get0()));
	}

	private static double dePhase(double d) {
		if (d > Math.PI)
			return dePhase(d - 2 * Math.PI);
		if (d <= -Math.PI)
			return dePhase(d + 2 * Math.PI);
		return d;
	}

	private final Team a, b;

	private final Ball ball;

	private final List<Player> as = Lists.newArrayListWithCapacity(11);
	private final List<Player> bs = Lists.newArrayListWithCapacity(11);

	private final Pitch pitch;

	private Player selected;

	private volatile Collection<Action> actions = Collections.emptyList();

	private volatile Collection<Aftertouch> aftertouches = Collections.emptyList();

	private final Map<Player, Double> grounded = Maps.newHashMap();

	private final Collection<Goalpost> goals = Lists.newArrayList();

	/**
	 * @param a
	 * @param b
	 * @param pitch
	 */
	public GamePhysics(Team a, Team b, Pitch pitch) {
		super(9.81);
		this.a = a;
		this.b = b;
		this.pitch = pitch;

		goalkeeperController = new GoalkeeperController(pitch);

		ball = new Ball(world, space);
		Position centre = pitch.getCentre();
		ball.setPosition(centre);

		goals.add(new Goalpost(world, space, pitch, Direction.NORTH));
		goals.add(new Goalpost(world, space, pitch, Direction.SOUTH));

		BallZone bz = ball.getZone(pitch);

		List<PlayerStats> aPlayers = a.getPlayers();
		Tactics tactics = a.getCurrentTactics();
		Goalkeeper goalkeeper = new Goalkeeper(1, a, aPlayers.get(0), world, space);
		goalkeeper.setPosition(pitch.getGoalBottom());
		goalkeeper.setOpponent(Direction.NORTH);
		as.add(goalkeeper);
		for (int i = 2; i <= 11; i++) {
			Position p = tactics.getZone(bz, i).getCentre(pitch, Pitch.Facing.NORTH);
			Player pma = new Player(i, a, aPlayers.get(i - 1), world, space);
			pma.setPosition(p);
			as.add(pma);
		}
		selected = as.get(9);

		// TODO: remove duplication
		List<PlayerStats> bPlayers = a.getPlayers();
		tactics = b.getCurrentTactics();
		goalkeeper = new Goalkeeper(1, b, bPlayers.get(0), world, space);
		goalkeeper.setPosition(pitch.getGoalTop());
		goalkeeper.setOpponent(Direction.SOUTH);
		bs.add(goalkeeper);
		for (int i = 2; i <= 11; i++) {
			Position p = tactics.getZone(bz, i).getCentre(pitch, Pitch.Facing.SOUTH);
			Player pma = new Player(i, b, bPlayers.get(i - 1), world, space);
			pma.setPosition(p);
			bs.add(pma);
		}
	}

	@Override
	protected DNearCallback getCollisionCallback() {
		GameCollisionHandler handler = new GameCollisionHandler();
		return new CollisionCallback(world, joints, handler);
	}

	/**
	 * Controller.
	 *
	 * @param actions
	 * @param aftertouches
	 */
	public void setUserActions(Collection<Action> actions, Collection<Aftertouch> aftertouches) {
		Preconditions.checkNotNull(actions);
		Preconditions.checkNotNull(aftertouches);
		this.actions = Sets.newHashSet(actions);
		this.aftertouches = Sets.newHashSet(aftertouches);
	}

	@Override
	protected void beforeStep() {
		debugNaNs();

//		for (Goalpost goal : goals) {
//			if (goal.isInside(ball))
//				log.info("GOAL TO " + goal.getFacing());
//		}

		if (actions.contains(Action.CHANGE))
			updateSelected();

		Position bp = ball.getPosition();
		BallZone bz = ball.getZone(pitch);
		for (Player p : getPlayers()) {
			transition(p);
			if (p == selected)
				continue;
			Position target = bp;
			if (p instanceof Goalkeeper) {
				goalkeeperController.autoPilot((Goalkeeper) p, ball);
				continue;
			}
			double near = Math.min(10, bp.distance(selected.getPosition()));
			if (bp.distance(p.getPosition()) > near) {
				// FIXME: tactics placer not working for south facing teams
				PlayerZone pz = p.getTeam().getCurrentTactics().getZone(bz, p.getShirt());
				if (p.getTeam() == a)
					target = pz.getCentre(pitch, Pitch.Facing.NORTH);
				else
					target = pz.getCentre(pitch, Pitch.Facing.SOUTH);
			}
			p.autoPilot(target);
		}

		selected.setActions(actions);
		ball.setAftertouch(aftertouches);
		ball.setDamping(0);
	}

	@Override
	protected void afterStep() {
		if (ball.getVelocity().speed() < MIN_SPEED)
			ball.setVelocity(new DVector3()); // stops small movements
		switch (selected.getState()) {
			case RUN:
				selected.control(ball);
				break;
			case KICK:
				selected.kick(ball);
				break;
			case THROWING:
				selected.throwIn(ball);
		}
	}

	private void updateSelected() {
		assert selected != null;
		Player closest = selected;
		double distance = Double.MAX_VALUE;
		for (Player model : as) {
			switch (model.getState()) {
				case GROUND:
				case INJURED:
				case HEAD_START:
				case HEAD_MID:
				case HEAD_END:
				case TACKLE:
					continue;
			}
			double ds2 = model.getPosition().distance(ball.getPosition());
			if (ds2 < distance) {
				distance = ds2;
				closest = model;
			}
		}
		selected = closest;
	}

	@SuppressWarnings("fallthrough")
	private void transition(Player p) {
		// TODO: should be in the Player class, not sure how
		// TODO: really needs a cleanup
		switch (p.getState()) {
			case TACKLE:
				if (p.getVelocity().speed() > MIN_SPEED)
					break;
			case GROUND:
				if (!grounded.containsKey(p)) {
					grounded.put(p, time);
				} else if ((time - grounded.get(p)) > 2) {
					if (p.getState() == PlayerState.GROUND && new Random().nextBoolean()) {
						p.setState(PlayerState.INJURED);
						return;
					}
					p.setState(PlayerState.RUN);
					grounded.remove(p);
					break;
				}
			case INJURED:
				if (!grounded.containsKey(p)) {
					log.warning("WASN'T LISTED IN GROUNDED");
					grounded.put(p, time);
				} else if ((time - grounded.get(p)) > 5) {
					p.setState(PlayerState.RUN);
					grounded.remove(p);
				}
		}
	}

	// <editor-fold defaultstate="collapsed" desc="BOILERPLATE GETTERS/SETTERS">
	public Ball getBall() {
		return ball;
	}

	public Iterable<Player> getPlayers() {
		return Iterables.concat(as, bs);
	}

	public Player getSelected() {
		return selected;
	}

	public double getTimestamp() {
		return time;
	}

	public Team getTeamA() {
		return a;
	}

	public Team getTeamB() {
		return b;
	}

	public Pitch getPitch() {
		return pitch;
	}
	// </editor-fold>
}
