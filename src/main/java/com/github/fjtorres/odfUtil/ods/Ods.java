package com.github.fjtorres.odfUtil.ods;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

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

		if (headerIndex < 0) {

			throw new IllegalArgumentException("The header index must be greater than or equals to zero.");
		}

		if (rowIndex <= headerIndex) {

			throw new IllegalArgumentException("The row index must be greater than header index.");
		}

		if (!sheetsHeaders.containsKey(sheetName)) {
			readHeaders(sheetName, headerIndex);
		}

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
	 * Read headers from Sheet.
	 * 
	 * @param sheetName
	 *            Sheet to read.
	 * @param headerIndex
	 *            Header index (zero based).
	 */
	private void readHeaders(String sheetName, Integer headerIndex) {

		Map<String, Integer> headers = new HashMap<>();

		Table sheet = document.getSheetByName(sheetName);

		Row headersRow = sheet.getRowByIndex(headerIndex);

		for (int i = 0; i < headersRow.getCellCount(); i++) {

			Cell headerCell = headersRow.getCellByIndex(i);

			headers.put(headerCell.getDisplayText(), headerCell.getColumnIndex());
		}

		sheetsHeaders.put(sheetName, headers);
	}
}
