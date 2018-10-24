package ru.defo.util;

public class EANcontrolNum {

	public static int getControlNum(String barcode) {
		int oddNum = 0;
		int evenNum = 0;
		int pos = 0;

		for (int i = barcode.length(); i > 0; --i) {
			pos++;
			if (pos % 2 == 0) {
				evenNum += Integer.valueOf((barcode.substring(i - 1, i)));
			} else {
				oddNum += Integer.valueOf((barcode.substring(i - 1, i)));
			}
		}

		String control = String.valueOf((oddNum * 3 + evenNum));
		
		if (Integer.decode(control.substring(control.length() - 1)).intValue() == 0) {
			return 0;
		} else {
			return 10 - Integer.decode(control.substring(control.length() - 1)).intValue();
		}
	}
}
