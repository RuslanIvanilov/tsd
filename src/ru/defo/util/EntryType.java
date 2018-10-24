package ru.defo.util;

public enum EntryType {
	INVENT(1),
	SHIPMENT(2);

	private final int typeId;

	EntryType(int typeId){ this.typeId = typeId; }

	public int typeId(){
		return typeId;
	}
}
