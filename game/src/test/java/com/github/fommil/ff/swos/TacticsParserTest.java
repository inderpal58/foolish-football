package com.github.fommil.ff.swos;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

import com.github.fommil.ff.Main;
import com.github.fommil.ff.swos.TacticsParser;;

public class TacticsParserTest {
	
	
	@Test
	public void getSwosTacticsReturnCountTest() throws IOException
	{
		assertTrue("Error, No Tactics", TacticsParser.getSwosTactics(Main.SWOS).size() == 12);	
	}

	

	@Test  (expected=IllegalArgumentException.class) 
	public void parseTacsTest() throws IOException
	{
		TacticsParser tp  = new TacticsParser();
		byte[] b = new byte[369];
		assertTrue("Error, Something wrong", tp.parseTacs(b).getName().length() ==  0);	
	}
}
