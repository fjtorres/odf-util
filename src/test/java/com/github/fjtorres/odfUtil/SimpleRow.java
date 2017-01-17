package com.github.fjtorres.odfUtil;

/**
 * Sample row class.
 * @author fjtorres
 *
 */
public class SimpleRow {

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
