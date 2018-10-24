package ru.defo.filters;

import java.util.HashMap;
import java.util.Map;

public class FieldFilter {
	Map<String, String> fieldFilterList;

	public Map<String, String> get(){ return fieldFilterList; }

	public FieldFilter(){ fieldFilterList = new HashMap<String, String>(); }

	public void add(String fieldName, String value){	fieldFilterList.put(fieldName, value); }

	public void del(String fieldName){ fieldFilterList.remove(fieldName); }

}
