package com.github.fommil.ff.swos;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fommil.ff.swos.SwosUtils;

public class SwosUtilsTest {
	
	@BeforeClass
	public static void setUp() throws IOException
	{
		SwosUtils.main(null);
	}
	
	@Test
	public void verifyGamePalImageTest()
	{

		java.io.File folder = new java.io.File("data/sprites");

		// this is the filter 
		java.io.FilenameFilter pitchFilter = new java.io.FilenameFilter() {
		 public boolean accept(File dir, String name) {
		   return name.toLowerCase().contains("gamepal");
		 }
		};

		// list the files in the data folder
		String[] filenames = folder.list(pitchFilter);
		
		assertTrue("Error, Number of pitch created is not matching", filenames.length == 1);
		
	}
	
	
	@Test
	public void verifyPalImageTest()
	{

		java.io.File folder = new java.io.File("data/sprites");

		// this is the filter 
		java.io.FilenameFilter pitchFilter = new java.io.FilenameFilter() {
		 public boolean accept(File dir, String name) {
		   return name.toLowerCase().contentEquals("pal.png");
		 }
		};

		// list the files in the data folder
		String[] filenames = folder.list(pitchFilter);
		
		assertTrue("Error, Number of pitch created is not matching", filenames.length == 1);
		
	}
	
	
	@Test
	public void fileToByteTest() throws IOException
	{
	
		byte[] bytes=SwosUtils.getBytes(new File("data/sprites/gamepal.png"));
		
		assertTrue("Error, Number of pitch created is not matching", bytes.length > 1);
		
	}

}
