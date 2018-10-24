package ru.defo.util;

public enum BarcodeType {

	BIN("������"),
	UNIT("������"),
	SKU("��������"),
	USER("������������"),
	UNKNOWN("�����������");
	
	private final String typeName;
	
	BarcodeType(String typeName){ this.typeName = typeName; }
	
	public String typeName(){
		return typeName;
	}
	
	
}
