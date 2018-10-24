package ru.defo.util;

public class WrongParamData extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = -4779340046775451062L;

	private int number;

	public int getNumber() {
		return number;
	}

	public WrongParamData(String errorMessage, int errorNumber) {
		super(errorMessage);
		number = errorNumber;
	}
}
