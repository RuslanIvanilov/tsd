package ru.defo.util;

public enum BarcodeType {

	BIN("€чейка"),
	UNIT("поддон"),
	SKU("упаковка"),
	USER("пользователь"),
	UNKNOWN("нераспознан");
	
	private final String typeName;
	
	BarcodeType(String typeName){ this.typeName = typeName; }
	
	public String typeName(){
		return typeName;
	}
	
	
}
