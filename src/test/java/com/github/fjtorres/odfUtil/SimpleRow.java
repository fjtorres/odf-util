package com.github.fjtorres.odfUtil;

import java.util.Map;

import com.github.fjtorres.odfUtil.ods.IRowMapper;

/**
 * Sample row class.
 * @author fjtorres
 *
 */
public class SimpleRow {

	public static final IRowMapper<SimpleRow> mapper = new IRowMapper<SimpleRow>() {

		@Override
		public SimpleRow map(Map<String, Object> columns) {
			return new SimpleRow((String) columns.get("Column 1"), (String) columns.get("Column 2"));
		}

	};
	
	/**
	 * Column 1 value.
	 */
	private final String column1;

	/**
	 * Column 2 value.
	 */
	private final String column2;

	/**
	 * Constructor.
	 * 
	 * @param pColumn1
	 * @param pColumn2
	 */
	public SimpleRow(String pColumn1, String pColumn2) {
		this.column1 = pColumn1;
		this.column2 = pColumn2;
	}

	public String getColumn1() {
		return column1;
	}

	public String getColumn2() {
		return column2;
	}
}
