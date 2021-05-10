/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Juan
 */
public class SortBy {
	
	/** The map of sorts. */
	private Map<String, SortOrder> mapOfSorts;
	
	/**
	 * Instantiates a new sort by.
	 */
	public SortBy() {
		if(null == mapOfSorts) {
			mapOfSorts = new HashMap<String, SortOrder>();
		}
	}

	/**
	 * Gets the sort bys.
	 *
	 * @return the sortBys
	 */
	public Map<String, SortOrder>  getSortBys() {
		return mapOfSorts;
	}
	
	/**
	 * Adds the sort.
	 *
	 * @param sortBy the sort by
	 */
	public void addSort(String sortBy) {
		mapOfSorts.put(sortBy, SortOrder.ASC);
	}
	
	/**
	 * Adds the sort.
	 *
	 * @param sortBy the sort by
	 * @param sortOrder the sort order
	 */
	public void addSort(String sortBy, SortOrder sortOrder) {
		mapOfSorts.put(sortBy, sortOrder);
	}

}