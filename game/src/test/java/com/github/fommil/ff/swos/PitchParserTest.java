package com.github.fommil.ff.swos;

import static org.junit.Assert.*;

import java.io.File;
import org.junit.Test;

import com.github.fommil.ff.swos.PitchParser;

public class PitchParserTest {
	
	@Test
	public void verifyNumberOfPitchesCreatedTest() throws Exception
	{
		//start parsing
		PitchParser.main(null);
		
		java.io.File folder = new java.io.File("data/sprites");

		// this is the filter 
		java.io.FilenameFilter pitchFilter = new java.io.FilenameFilter() {
		 public boolean accept(File dir, String name) {
		   return name.toLowerCase().contains("pitch");
		 }
		};

		// list the files in the data folder
		String[] filenames = folder.list(pitchFilter);
		
		assertTrue("Error, Number of pitch created is not matching", filenames.length == 6);
		
	
	}
	
	

}
