package com.github.fjtorres.odfUtil;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fjtorres.odfUtil.ods.IRowMapper;
import com.github.fjtorres.odfUtil.ods.Ods;

public class OdsTest {

	private static Ods ods;

	private final IRowMapper<SimpleRow> mapper = new IRowMapper<SimpleRow>() {

		@Override
		public SimpleRow map(Map<String, Object> columns) {
			return new SimpleRow((String) columns.get("Column 1"), (String) columns.get("Column 2"));
		}

	};

	@BeforeClass
	public static void setUp() throws Exception {

		ods = new Ods(Paths.get(ClassLoader.getSystemResource("TEST.ods").toURI()));
	}

	@AfterClass
	public static void tearDown() {

		ods = null;
	}

	@Test
	public void getCellValueAsStringTest() {

		Assert.assertEquals("Cell 1 - 1", ods.getCellValueAsString("Sheet1", "Column 1", 1));
	}

	@Test
	public void getCellValueAsStringSpecificHeaderRowTest() {

		Assert.assertEquals("Cell 1 - 1", ods.getCellValueAsString("Sheet1", 0, "Column 1", 1));
		Assert.assertEquals("Cell 2 - 2", ods.getCellValueAsString("Sheet1", 0, "Column 2", 2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getCellValueAsStringInvalidHeaderIndexTest() {

		ods.getCellValueAsString("Sheet1", -1, "Column 1", 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getCellValueAsStringInvalidRowIndexTest() {

		ods.getCellValueAsString("Sheet1", 0, "Column 1", 0);
	}

	@Test
	public void getAllRowsTest() {

		List<SimpleRow> rows = ods.readAllRows("Sheet1", 0, mapper);

		Assert.assertEquals(2, rows.size());
		Assert.assertEquals("Cell 1 - 1", rows.get(0).getColumn1());
		Assert.assertEquals("Cell 1 - 2", rows.get(0).getColumn2());
		Assert.assertEquals("Cell 2 - 1", rows.get(1).getColumn1());
		Assert.assertEquals("Cell 2 - 2", rows.get(1).getColumn2());
	}

	@Test
	public void getSingleRowTest() {

		SimpleRow row = ods.readRow("Sheet1", 0, 1, mapper);

		Assert.assertEquals("Cell 1 - 1", row.getColumn1());
		Assert.assertEquals("Cell 1 - 2", row.getColumn2());
	}
}
