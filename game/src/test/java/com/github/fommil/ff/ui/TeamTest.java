package com.github.fommil.ff.ui;

import java.awt.Color;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fommil.ff.Tactics;
import com.github.fommil.ff.Team;
import com.github.fommil.ff.Team.Colours;

public class TeamTest {
	
	static Colours colors;
	static Team team;
	static Tactics tactices;
	
	@BeforeClass
	public static void setUp()
	{
		colors = new Colours(Color.black, Color.blue, Color.cyan, Color.GREEN);
		team = new Team();
		tactices = new Tactics("One");
	}

	@Test
	public void verifyColorsTest()
	{
		assertTrue(colors.getPrimary().equals(Color.black));
		assertTrue(colors.getSecondary().equals(Color.blue));
		assertTrue(colors.getShorts().equals(Color.cyan));
		assertTrue(colors.getSocks().equals(Color.GREEN));
	}
		
	@Test
	public void verifyModifiedHomeKitTest()
	{
		team.setHomeKit(new Colours(Color.RED, Color.RED, Color.BLUE, Color.RED));
		assertTrue(team.getHomeKit().getPrimary().equals(
				(new Colours(Color.RED, Color.RED, Color.BLUE, Color.RED).getPrimary())));
	}
	
	@Test
	public void verifyModifiedAwayKitTest()
	{
		team.setAwayKit(new Colours(Color.RED, Color.RED, Color.BLUE, Color.RED));
		assertTrue(team.getAwayKit().getPrimary().equals(
				(new Colours(Color.RED, Color.RED, Color.BLUE, Color.RED)).getPrimary()));
	}
	
	@Test
	public void verifyTacticsTest()
	{
		team.setCurrentTactics(tactices);
		assertTrue(team.getCurrentTactics().equals(tactices));
	}
	
	
}
