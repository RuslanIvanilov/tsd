package ru.defo.util;

public enum TimeType {

	START("с"),
	FINISH("по");

	private final String typeName;

	TimeType(String typeName){ this.typeName = typeName; }

	public String typeName(){
		return typeName;
	}

}
