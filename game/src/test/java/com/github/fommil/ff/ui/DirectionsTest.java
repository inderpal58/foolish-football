package com.github.fommil.ff.ui;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.fommil.ff.Direction;

//class to verify angles return exact direction
public class DirectionsTest {
	
	@Test
	public void SouthTest()
	{
		assertTrue(Direction.valueOf(Math.PI).equals(Direction.SOUTH));
	}
	
	@Test
	public void AllSouthTest()
	{
		assertTrue(Direction.valueOf(-Math.PI).equals(Direction.SOUTH));
	}
	
	
	@Test
	public void NORTHTest()
	{
		assertTrue(Direction.valueOf(0).equals(Direction.NORTH));
	}
	
	@Test
	public void WESTTest()
	{
		assertTrue(Direction.valueOf(-Math.PI / 2).equals(Direction.WEST));
	}
	@Test
	public void EASTTest()
	{
		assertTrue(Direction.valueOf(Math.PI / 2).equals(Direction.EAST));
	}
	@Test
	public void NORTH_EASTTest()
	{
		assertTrue(Direction.valueOf(Math.PI / 4).equals(Direction.NORTH_EAST));
	}
	@Test
	public void NORTH_WESTTest()
	{
		assertTrue(Direction.valueOf(-Math.PI / 4).equals(Direction.NORTH_WEST));
	}
	@Test
	public void sSOUTH_EASTTest()
	{
		assertTrue(Direction.valueOf(3 * Math.PI / 4).equals(Direction.SOUTH_EAST));
	}
	@Test
	public void sOUTH_WESTTest()
	{
		assertTrue(Direction.valueOf(-3 * Math.PI / 4).equals(Direction.SOUTH_WEST));
	}

}
