package ru.defo.util;

public class Bc {

	/**
	 * @see converter ScanWedge Code Id = Symbol  to Value of BarCode.
	 * */
	public static String symbols(String barCode){
		if(barCode!= null && barCode!= ""){
			if(barCode.charAt(0)=='A' || (barCode.charAt(0)=='D' && !(barCode.length()==14)) || (barCode.charAt(0)=='B' && !(barCode.length()==14)))
				barCode = barCode.substring(1, barCode.length());
		}
		return barCode;
	}
}
