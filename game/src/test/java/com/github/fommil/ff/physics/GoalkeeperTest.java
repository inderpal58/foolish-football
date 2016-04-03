package com.github.fommil.ff.physics;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

import com.github.fommil.ff.Direction;
import com.github.fommil.ff.PlayerStats;

public class GoalkeeperTest {

	@Test
	public void createGoalKeeper() {
		Goalkeeper gk = new DummyPhysics().createGoalkeeper(1,new PlayerStats());
		assertEquals(gk.getShirt(),1);
	}
	
	@Test
	public void createActions() {
		Collection<Action> actions = new HashSet<Action>();
		Goalkeeper gk = new DummyPhysics().createGoalkeeper(1,new PlayerStats());

		actions.add(Action.RIGHT);
		actions.add(Action.LEFT);
		actions.add(Action.DIVE);
		actions.add(Action.UP);
		actions.add(Action.TACKLE);
		gk.setActions(actions);
		assertEquals(
				gk.getVelocity(),
				new Velocity(0.0,0.0,5.0)
		);		
	}
	
	@Test
	public void goalKeeperState_DIVE_START() {
		Collection<Action> actions = new HashSet<Action>();
		Goalkeeper gk = new DummyPhysics().createGoalkeeper(1,new PlayerStats());

		actions.add(Action.RIGHT);
		actions.add(Action.LEFT);
		actions.add(Action.DIVE);
		actions.add(Action.UP);
		actions.add(Action.TACKLE);
		gk.setActions(actions);
		assertEquals(gk.getGkState(),Goalkeeper.GoalkeeperState.DIVE_START);
		
	}
	
	@Test
	public void diveGoalKeeper() {
		Goalkeeper gk = new DummyPhysics().createGoalkeeper(1,new PlayerStats());
		gk.dive(Direction.EAST);
		assertEquals(
				gk.getVelocity(),
				new Velocity(10.0,0.0,5.0)
		);	
		gk.dive(Direction.WEST);
		assertEquals(
				gk.getVelocity(),
				new Velocity(10.0,0.0,5.0)
		);	
		
	}

	@Test
	public void getTolerance() {
		Goalkeeper gk = new DummyPhysics().createGoalkeeper(1,new PlayerStats());
		assertTrue(gk.getAutoPilotTolerance()-0.1 == 0);
		
	}

}
