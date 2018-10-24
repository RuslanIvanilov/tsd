package ru.defo.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import ru.defo.managers.CarManager;
import ru.defo.util.ErrorMessage;

public class CarController {

	public String getErrorText(String carCode) {
		if (new CarManager().getCarByCode(carCode) == null) {
			return ErrorMessage.WRONG_CAR_CODE.message(new ArrayList<String>(Arrays.asList(carCode)));
		}
		return null;
	}



}
