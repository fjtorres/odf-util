package com.github.fjtorres.odfUtil;

import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.fjtorres.odfUtil.ods.Sheet;

public class SheetTest {

	@Test
	public void largeFileTest() throws Exception {

		final int largeSize = 10000;

		Sheet largeSheet = new Sheet(Paths.get(ClassLoader.getSystemResource("TEST_LARGE.ods").toURI()), "Sheet1");

		List<ComplexRow> rows = largeSheet.readAllRows(0, ComplexRow.mapper);
		Assert.assertEquals(largeSize, rows.size());

		Assert.assertEquals("Cell 1 - 1", rows.get(0).getColumn1());
		Assert.assertEquals("Cell 1 - 2", rows.get(0).getColumn2());
		Assert.assertEquals("Cell " + largeSize + " - 1", rows.get(largeSize - 1).getColumn1());
		Assert.assertEquals("Cell " + largeSize + " - 19", rows.get(largeSize - 1).getColumn19());
	}
}
