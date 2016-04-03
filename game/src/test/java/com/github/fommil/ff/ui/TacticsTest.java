package com.github.fommil.ff.ui;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fommil.ff.Direction;
import com.github.fommil.ff.Pitch;
import com.github.fommil.ff.Tactics;
import com.github.fommil.ff.physics.Position;

public class TacticsTest {
	
	
	static Position position;
	static Pitch pitch;
	static Tactics.BallZone ball;
	static Tactics.PlayerZone player;	
	static Tactics tactices;
	
	@BeforeClass
	public static void setUp()
	{
		pitch = new Pitch();
		position = new Position(2,2,2);
		ball = new Tactics.BallZone(position, pitch);
		player = new Tactics.PlayerZone(2,2);
		tactices = new Tactics("One");
	}
	
	@Test
	public void verifyBallPositionTest()
	{
		Tactics.BallZone ball = new Tactics.BallZone(2,2);
		assertTrue(ball.toString().equals("(2, 2)"));
	}
	
	@Test
	public void verifyBallPositionInPitchTest()
	{
		assertTrue(ball.toString().equals("(4, 0)"));
	}
	
	@Test
	public void verifyPlayerPositionTest()
	{
		assertTrue(player.toString().equals("(2, 2)"));
	}
	
	@Test
	public void verifyPlayerPositionInPitchTest()
	{
		assertTrue(player.getCentre(pitch).equals(new Position(50.516666666666666, 21.1, 0.0)));
	}
	
	@Test
	public void verifyPlayerZoneTest()
	{
		tactices.set(ball, 2, player);
		assertTrue(tactices.getZone(ball, 2, Direction.NORTH).equals(player));
	}

}
