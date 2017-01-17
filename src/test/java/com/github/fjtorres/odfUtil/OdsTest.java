package com.github.fjtorres.odfUtil;

import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fjtorres.odfUtil.ods.Ods;

public class OdsTest {

	private static Ods ods;

	@BeforeClass
	public static void setUp() throws Exception {

		ods = new Ods(Paths.get(ClassLoader.getSystemResource("TEST.ods").toURI()));
	}
	
	@AfterClass
	public static void tearDown() {
		
		ods = null;
	}
	
	@Test
	public void getCellValueAsStringTest () {
		
		Assert.assertEquals("Cell 1 - 1", ods.getCellValueAsString("Sheet1", "Column 1", 1));
	}
	
	@Test
	public void getCellValueAsStringSpecificHeaderRowTest () {
		
		Assert.assertEquals("Cell 1 - 1", ods.getCellValueAsString("Sheet1", 0, "Column 1", 1));
		Assert.assertEquals("Cell 2 - 2", ods.getCellValueAsString("Sheet1", 0, "Column 2", 2));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getCellValueAsStringInvalidHeaderIndexTest () {
		
		ods.getCellValueAsString("Sheet1", -1, "Column 1", 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getCellValueAsStringInvalidRowIndexTest () {
		
		ods.getCellValueAsString("Sheet1", 0, "Column 1", 0);
	}
}
