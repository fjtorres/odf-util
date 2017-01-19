package com.github.fjtorres.odfUtil.ods;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;

/**
 * Wrapper class to work with ODS files.
 * 
 * @author fjtorres
 *
 */
public class Ods {

	/**
	 * Document instance.
	 */
	private SpreadsheetDocument document;

	/**
	 * Map with sheets headers position.
	 */
	private Map<String, Map<String, Integer>> sheetsHeaders = new HashMap<>();

	/**
	 * Constructor.
	 * 
	 * @param pathToFile
	 *            Path to ODS file.
	 */
	public Ods(Path pathToFile) {

		load(pathToFile);
	}

	/**
	 * Constructor.
	 * 
	 * @param file
	 *            ODS file.
	 */
	public Ods(File file) {

		load(file.toPath());
	}

	/**
	 * Load ODS from specified path.
	 * 
	 * @param pathToFile
	 *            Path to ODS file.
	 * @throws IllegalArgumentException
	 *             If not found file or can not load.
	 */
	private void load(Path pathToFile) throws IllegalArgumentException {

		if (!Files.exists(pathToFile)) {

			throw new IllegalArgumentException("File not found.");
		}

		try {
			document = SpreadsheetDocument.loadDocument(pathToFile.toFile());
		} catch (Exception e) {

			throw new IllegalArgumentException("Can not load file.", e);
		}
	}

	/**
	 * Read all rows and return collection of specific type mapped by mapper
	 * instance.
	 * 
	 * @param sheetName
	 *            Sheet to work.
	 * @param headerIndex
	 *            The header index (zero based).
	 * @param mapper
	 *            The mapper to generate specific type.
	 * @return Collection of specific type.
	 */
	public <T> List<T> readAllRows(String sheetName, Integer headerIndex, IRowMapper<T> mapper) {

		checkHeaderIndex(headerIndex);
		
		readHeaders(sheetName, headerIndex);

		Table sheet = getSheet(sheetName);

		List<Row> rows = sheet.getRowList();

		List<T> allRows = new ArrayList<>();

		for (int i = 0; i < rows.size(); i++) {

			if (i <= headerIndex) {
				continue;
			}

			allRows.add(readRow(rows.get(i), mapper, sheetsHeaders.get(sheetName)));
		}

		return allRows;
	}

	/**
	 * Read row columns and return map with values.
	 * 
	 * @param sheetName
	 *            Sheet to work.
	 * @param headerIndex
	 *            The header index (zero based).
	 * @param rowIndex
	 *            The row index (zero based). Must be greater than header index.
	 * @return Map with columns values.
	 */
	public Map<String, Object> readRowAsMap(String sheetName, Integer headerIndex, Integer rowIndex) {

		checkIndex(headerIndex, rowIndex);

		readHeaders(sheetName, headerIndex);

		return readToMap(getSheet(sheetName).getRowByIndex(rowIndex), sheetsHeaders.get(sheetName));
	}

	/**
	 * Read row columns and return specific type mapped by mapper instance.
	 * 
	 * @param sheetName
	 *            Sheet to work.
	 * @param headerIndex
	 *            The header index (zero based).
	 * @param rowIndex
	 *            The row index (zero based). Must be greater than header index.
	 * @param mapper
	 *            The mapper to generate specific type.
	 * @return Specific type with values.
	 */
	public <T> T readRow(String sheetName, Integer headerIndex, Integer rowIndex, IRowMapper<T> mapper) {

		checkIndex(headerIndex, rowIndex);

		readHeaders(sheetName, headerIndex);

		return readRow(getSheet(sheetName).getRowByIndex(rowIndex), mapper, sheetsHeaders.get(sheetName));
	}

	/**
	 * Retrieve cell value as string.
	 * 
	 * @param sheetName
	 *            Sheet to work.
	 * @param headerIndex
	 *            The header index (zero based).
	 * @param columnName
	 *            The column name.
	 * @param rowIndex
	 *            The row index (zero based). Must be greater than header index.
	 * @return The cell value as String.
	 */
	public String getCellValueAsString(String sheetName, Integer headerIndex, String columnName, Integer rowIndex) {

		checkIndex(headerIndex, rowIndex);

		readHeaders(sheetName, headerIndex);

		return document.getSheetByName(sheetName)
				.getCellByPosition(sheetsHeaders.get(sheetName).get(columnName), rowIndex).getStringValue();
	}

	/**
	 * Retrieve cell value as string. The header is the first row.
	 * 
	 * @param sheetName
	 *            Sheet to work.
	 * @param columnName
	 *            The column name.
	 * @param rowIndex
	 *            The row index (zero based). Must be greater than zero.
	 * @return The cell value as String.
	 */
	public String getCellValueAsString(String sheetName, String columnName, Integer rowIndex) {

		return this.getCellValueAsString(sheetName, 0, columnName, rowIndex);
	}

	/**
	 * Method to validate header and row index.
	 * 
	 * @param headerIndex
	 *            The index of header.
	 * @param rowIndex
	 *            The index of row.
	 */
	private void checkIndex(Integer headerIndex, Integer rowIndex) {

		checkHeaderIndex(headerIndex);

		if (rowIndex <= headerIndex) {

			throw new IllegalArgumentException("The row index must be greater than header index.");
		}
	}

	/**
	 * Method to validate header index.
	 * 
	 * @param headerIndex
	 *            The index of header.
	 */
	private void checkHeaderIndex(Integer headerIndex) {

		if (headerIndex < 0) {

			throw new IllegalArgumentException("The header index must be greater than or equals to zero.");
		}
	}

	/**
	 * Read headers from Sheet.
	 * 
	 * @param sheetName
	 *            Sheet to read.
	 * @param headerIndex
	 *            Header index (zero based).
	 */
	private void readHeaders(String sheetName, Integer headerIndex) {

		if (sheetsHeaders.containsKey(sheetName)) {
			return;
		}

		Map<String, Integer> headers = new HashMap<>();

		Table sheet = getSheet(sheetName);

		int cellCount = sheet.getColumnCount();
		
		for (int i = 0; i < cellCount; i++) {

			Cell headerCell = sheet.getCellByPosition(i, headerIndex);
			
			String text = headerCell.getDisplayText();
			
			if (text == null || text.trim().equals("")) {
				break;
			}

			headers.put(text, headerCell.getColumnIndex());
		}

		sheetsHeaders.put(sheetName, headers);
	}

	/**
	 * Internal method to get sheet by name.
	 * 
	 * @param sheetName
	 *            The sheet name.
	 * @return Sheet.
	 */
	private Table getSheet(String sheetName) {

		return document.getSheetByName(sheetName);
	}

	/**
	 * Internal method to read row as specific type.
	 * 
	 * @param row
	 *            Row to read.
	 * @param mapper
	 *            The mapper to generate specific type.
	 * @param columns
	 *            The row columns.
	 * @return Specific type with values.
	 */
	private <T> T readRow(Row row, IRowMapper<T> mapper, Map<String, Integer> columns) {

		return mapper.map(readToMap(row, columns));
	}

	/**
	 * Internal method to read row to map object.
	 * 
	 * @param row
	 *            Row to read.
	 * @param columns
	 *            The row columns.
	 * @return Map with values.
	 */
	private Map<String, Object> readToMap(Row row, Map<String, Integer> columns) {

		Map<String, Object> fields = new HashMap<>();

		for (Entry<String, Integer> entry : columns.entrySet()) {

			Cell cell = row.getCellByIndex(entry.getValue());

			fields.put(entry.getKey(), getValue(cell));
		}

		return fields;
	}

	/**
	 * Internal method to get cell value by type.
	 * 
	 * @param cell
	 *            The cell to extract value.
	 * @return Value of cell.
	 */
	private Object getValue(Cell cell) {

		String type = cell.getValueType();

		if (type == null) {

			return cell.getStringValue();
		}

		switch (type) {
		case "boolean":
			return cell.getBooleanValue();
		case "date":
			return cell.getDateValue();
		case "time":
			return cell.getDateTimeValue();
		case "float":
			return cell.getDoubleValue();
		case "percentage":
			return cell.getPercentageValue();
		case "string":
			return cell.getStringValue();
		}

		return null;
	}
}
