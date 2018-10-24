package ru.defo.util;

public class HistoryComment {
	String delQuantComment = new String();
	String delUnitBinComment = new String();

	public HistoryComment(EntryType entryTypeId){
		switch(entryTypeId){

		case INVENT:
			delQuantComment = DefaultValue.INVENTORY+"."+DefaultValue.DEL_QUANT;
			delUnitBinComment = DefaultValue.INVENT_DEL_UNIT_TEXT;
			break;

		case SHIPMENT:
			delQuantComment = DefaultValue.SHIPMENT+"."+DefaultValue.DEL_QUANT;
			delUnitBinComment = DefaultValue.SHIPMENT+"."+DefaultValue.DEL_UNIT_FROM_BIN;
			break;

		}
	}

	public String getDelQuantComment(){ return delQuantComment; }
	public String getDelUnitBinComment(){ return delUnitBinComment; }

}
