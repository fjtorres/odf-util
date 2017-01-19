package com.github.fjtorres.odfUtil.ods;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to work with specific Sheet.
 * 
 * @author fjtorres
 *
 */
public class Sheet {

	/**
	 * Class logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Sheet.class);
	
	/**
	 * Document reference.
	 */
	private SpreadsheetDocument document;

	/**
	 * Sheet reference.
	 */
	private Table sheet;

	private Map<String, Integer> sheetsHeaders = new HashMap<>();

	/**
	 * Constructor.
	 * 
	 * @param pathToFile
	 *            Path to ODS file.
	 * @param sheetName
	 *            Sheet name.
	 */
	public Sheet(Path pathToFile, String sheetName) {

		if (sheetName == null || sheetName.trim().length() == 0) {
			throw new IllegalArgumentException("The sheet name is required.");
		}

		load(pathToFile);

		LOGGER.debug(String.format("Searching sheet: %s", sheetName));
		sheet = document.getSheetByName(sheetName);
		LOGGER.debug("Sheet found and loaded.");
	}
	
	/**
	 * Read all rows and return collection of specific type mapped by mapper
	 * instance.
	 * 
	 * @param headerIndex
	 *            The header index (zero based).
	 * @param mapper
	 *            The mapper to generate specific type.
	 * @return Collection of specific type.
	 */
	public <T> List<T> readAllRows(Integer headerIndex, IRowMapper<T> mapper) {

		checkHeaderIndex(headerIndex);
		
		readHeaders(headerIndex);
		
		LOGGER.debug("Loading sheet rows");
		List<Row> rows = sheet.getRowList();
		
		int count = rows.size();
		
		LOGGER.debug(String.format("Sheet rows loaded. Count: %d", count));

		List<T> allRows = new ArrayList<>();

		for (int i = 0; i < count; i++) {

			if (i <= headerIndex) {
				continue;
			}

			LOGGER.debug(String.format("Reading row: %d", i));
			allRows.add(readRow(headerIndex, i, mapper));
		}

		return allRows;
	}

	/**
	 * Read row columns and return specific type mapped by mapper instance.
	 * 
	 * @param headerIndex
	 *            The header index (zero based).
	 * @param rowIndex
	 *            The row index (zero based). Must be greater than header index.
	 * @param mapper
	 *            The mapper to generate specific type.
	 * @return Specific type with values.
	 */
	public <T> T readRow(Integer headerIndex, Integer rowIndex, IRowMapper<T> mapper) {
		
		return mapper.map(readRowAsMap(headerIndex, rowIndex));
	}

	/**
	 * Read row columns and return map with values.
	 * 
	 * @param headerIndex
	 *            The header index (zero based).
	 * @param rowIndex
	 *            The row index (zero based). Must be greater than header index.
	 * @return Map with columns values.
	 */
	public Map<String, Object> readRowAsMap(Integer headerIndex, Integer rowIndex) {

		checkIndex(headerIndex, rowIndex);
		
		readHeaders(headerIndex);
		
		Row row = sheet.getRowByIndex(rowIndex);
		
		Map<String, Object> fields = new HashMap<>();

		for (Entry<String, Integer> entry : sheetsHeaders.entrySet()) {

			Cell cell = row.getCellByIndex(entry.getValue());

			fields.put(entry.getKey(), getValue(cell));
		}

		return fields;
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
	 * @param headerIndex
	 *            Header index (zero based).
	 */
	private void readHeaders(Integer headerIndex) {

		if (!sheetsHeaders.isEmpty()) {
			return;
		}
		
		LOGGER.debug("Loading sheet columns.");

		Row headersRow = sheet.getRowByIndex(headerIndex);
		
		LOGGER.debug("Header row loaded");

		int cellCount = sheet.getColumnCount();
		
		LOGGER.debug(String.format("Number of cells: %d", cellCount));

		for (int i = 0; i < cellCount; i++) {

			Cell headerCell = headersRow.getCellByIndex(i);

			String text = headerCell.getDisplayText();

			if (text == null || text.trim().length() == 0) {
				break;
			}

			LOGGER.debug(String.format("Column detected: %s", text));
			sheetsHeaders.put(text, headerCell.getColumnIndex());
		}
		
		LOGGER.debug("Sheet columns loaded.");
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
			LOGGER.debug(String.format("Loading ODS file: %s", pathToFile.toString()));
			document = SpreadsheetDocument.loadDocument(pathToFile.toFile());
			LOGGER.debug("ODS file loaded.");
		} catch (Exception e) {

			throw new IllegalArgumentException("Can not load file.", e);
		}
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
