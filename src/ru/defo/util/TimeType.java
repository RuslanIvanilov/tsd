package ru.defo.util;

public enum TimeType {

	START("�"),
	FINISH("��");

	private final String typeName;

	TimeType(String typeName){ this.typeName = typeName; }

	public String typeName(){
		return typeName;
	}

}
