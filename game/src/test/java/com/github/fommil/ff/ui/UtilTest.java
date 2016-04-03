package com.github.fommil.ff.ui;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.fommil.ff.Utils;

public class UtilTest {
	
	@Test
	public void boundedTest()
	{
		assertEquals(Utils.bounded(2.0,2.0,4.0), 2.0, .0001);
	}
	
	@Test
	public void roundTest()
	{
		assertEquals(Utils.round(2.0001),2.0, .0001);
	}

}
