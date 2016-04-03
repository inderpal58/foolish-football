package com.github.fommil.ff.ui;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fommil.ff.Pitch;
import com.github.fommil.ff.physics.Position;

public class PitchTest {
	
	private static final double EPSILON = 0.0001;
	static Pitch pitch;
	
	@BeforeClass
	public static void setUp()
	{
		pitch = new Pitch();
	}
	
	@Test
	public void getPitchLowerLeftTest()
	{
		assertTrue(pitch.getPitchLowerLeft().equals(new Position(8.1,11.100000000000001,0.0)));
	}
	
	@Test
	public void getPitchUpperRightTest()
	{
		assertTrue(pitch.getPitchUpperRight().equals(new Position(59.0, 75.10000000000001, 0.0)));
	}
	
	@Test
	public void getCentreTest()
	{
		assertTrue(pitch.getCentre().equals(new Position(33.6, 43.1, 0.0)));
	}
	
	@Test
	public void getGoalWidthTest()
	{
		assertEquals(pitch.getGoalWidth(),7.1, EPSILON);
	}
	
	@Test
	public void getGoalHeightTest() {
		assertEquals(pitch.getGoalHeight(),3, EPSILON);
	}
	
	@Test
	public void getGoalDepthTest() {
		assertEquals(pitch.getGoalDepth(),1.2, EPSILON);
	}
	
	@Test
	public void getGoalThicknessTest() {
		assertEquals(pitch.getGoalThickness(),.2, EPSILON);
	}
	
	@Test
	public void getGoalTopTest() 
	{
		assertTrue(pitch.getGoalTop().equals(new Position(33.550000000000004, 75.7, 0.0)));
	}
	
	@Test
	public void getGoalBottomTest() 
	{
		assertTrue(pitch.getGoalBottom().equals(new Position(33.550000000000004, 10.5, 0.0)));
		
	}
	
	@Test
	public void getPenaltySpotTopTest() 
	{
		assertTrue(pitch.getPenaltySpotTop().equals(new Position(33.6,69.3,0.0)));
	
	}
	
	@Test
	public void getPenaltySpotBottomTest() 
	{
		assertTrue(pitch.getPenaltySpotBottom().equals(new Position(33.6, 16.900000000000002, 0.0)));
	}
}
