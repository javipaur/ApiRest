/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;

/**
 *
 * @author Juan
 */
public enum SortOrder {

	/** The asc. */
	ASC("ASC"), 
	/** The desc. */
	DESC("DESC");

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new sort order.
	 * 
	 * @param v
	 *            the v
	 */
	SortOrder(String v) {
		value = v;
	}

	/**
	 * From value.
	 * 
	 * @param v
	 *            the v
	 * @return the sort order
	 */
	public static SortOrder fromValue(String v) {
		for (SortOrder c : SortOrder.values()) {
			if (c.name().equalsIgnoreCase(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

	/**
	 * Value.
	 * 
	 * @return the string
	 */
	public String value() {
		return value;
	}

}