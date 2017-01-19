package com.github.fjtorres.odfUtil;

import java.util.Map;

import com.github.fjtorres.odfUtil.ods.IRowMapper;

public class ComplexRow {

	public static final IRowMapper<ComplexRow> mapper = new IRowMapper<ComplexRow>() {

		@Override
		public ComplexRow map(Map<String, Object> columns) {
			
			ComplexRow row = new ComplexRow();
			row.setColumn1((String) columns.get("Column 1"));
			row.setColumn2((String) columns.get("Column 2"));
			row.setColumn3((String) columns.get("Column 3"));
			row.setColumn4((String) columns.get("Column 4"));
			row.setColumn5((String) columns.get("Column 5"));
			row.setColumn6((String) columns.get("Column 6"));
			row.setColumn7((String) columns.get("Column 7"));
			row.setColumn8((String) columns.get("Column 8"));
			row.setColumn9((String) columns.get("Column 9"));
			row.setColumn10((String) columns.get("Column 10"));
			row.setColumn11((String) columns.get("Column 11"));
			row.setColumn12((String) columns.get("Column 12"));
			row.setColumn13((String) columns.get("Column 13"));
			row.setColumn14((String) columns.get("Column 14"));
			row.setColumn15((String) columns.get("Column 15"));
			row.setColumn16((String) columns.get("Column 16"));
			row.setColumn17((String) columns.get("Column 17"));
			row.setColumn18((String) columns.get("Column 18"));
			row.setColumn19((String) columns.get("Column 19"));
			
			return row;
		}

	};
	
	/**
	 * Column 1 value.
	 */
	private String column1;

	/**
	 * Column 2 value.
	 */
	private String column2;

	/**
	 * Column 3 value.
	 */
	private String column3;

	/**
	 * Column 4 value.
	 */
	private String column4;

	/**
	 * Column 5 value.
	 */
	private String column5;

	/**
	 * Column 6 value.
	 */
	private String column6;

	/**
	 * Column 7 value.
	 */
	private String column7;

	/**
	 * Column 8 value.
	 */
	private String column8;

	/**
	 * Column 9 value.
	 */
	private String column9;

	/**
	 * Column 10 value.
	 */
	private String column10;

	/**
	 * Column 11 value.
	 */
	private String column11;

	/**
	 * Column 12 value.
	 */
	private String column12;

	/**
	 * Column 13 value.
	 */
	private String column13;

	/**
	 * Column 14 value.
	 */
	private String column14;

	/**
	 * Column 15 value.
	 */
	private String column15;

	/**
	 * Column 16 value.
	 */
	private String column16;

	/**
	 * Column 17 value.
	 */
	private String column17;

	/**
	 * Column 18 value.
	 */
	private String column18;

	/**
	 * Column 19 value.
	 */
	private String column19;

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getColumn3() {
		return column3;
	}

	public void setColumn3(String column3) {
		this.column3 = column3;
	}

	public String getColumn4() {
		return column4;
	}

	public void setColumn4(String column4) {
		this.column4 = column4;
	}

	public String getColumn5() {
		return column5;
	}

	public void setColumn5(String column5) {
		this.column5 = column5;
	}

	public String getColumn6() {
		return column6;
	}

	public void setColumn6(String column6) {
		this.column6 = column6;
	}

	public String getColumn7() {
		return column7;
	}

	public void setColumn7(String column7) {
		this.column7 = column7;
	}

	public String getColumn8() {
		return column8;
	}

	public void setColumn8(String column8) {
		this.column8 = column8;
	}

	public String getColumn9() {
		return column9;
	}

	public void setColumn9(String column9) {
		this.column9 = column9;
	}

	public String getColumn10() {
		return column10;
	}

	public void setColumn10(String column10) {
		this.column10 = column10;
	}

	public String getColumn11() {
		return column11;
	}

	public void setColumn11(String column11) {
		this.column11 = column11;
	}

	public String getColumn12() {
		return column12;
	}

	public void setColumn12(String column12) {
		this.column12 = column12;
	}

	public String getColumn13() {
		return column13;
	}

	public void setColumn13(String column13) {
		this.column13 = column13;
	}

	public String getColumn14() {
		return column14;
	}

	public void setColumn14(String column14) {
		this.column14 = column14;
	}

	public String getColumn15() {
		return column15;
	}

	public void setColumn15(String column15) {
		this.column15 = column15;
	}

	public String getColumn16() {
		return column16;
	}

	public void setColumn16(String column16) {
		this.column16 = column16;
	}

	public String getColumn17() {
		return column17;
	}

	public void setColumn17(String column17) {
		this.column17 = column17;
	}

	public String getColumn18() {
		return column18;
	}

	public void setColumn18(String column18) {
		this.column18 = column18;
	}

	public String getColumn19() {
		return column19;
	}

	public void setColumn19(String column19) {
		this.column19 = column19;
	}
}
