package com.github.fjtorres.odfUtil.ods;

import java.util.Map;

/**
 * Row mapper definition.
 * 
 * @author fjtorres
 *
 */
public interface IRowMapper<T> {

	/**
	 * Map row columns to specific type.
	 * 
	 * @param columns
	 *            The row columns.
	 * @return The row object.
	 */
	T map(Map<String, Object> columns);
}
