package test.unit;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ru.defo.controllers.UnitController;

public class IsUnitBarCode {

	@Test
	public void test() {
		List<String> unitBarCodeList = Arrays.asList("EU000216900", "EU000210", "EU00021690", "IN00000001", "IS01250784", "SS00000001", null, "");

		for(String barCode : unitBarCodeList){
			System.out.println(barCode+" : "+new UnitController().isUnitBarCode(barCode));
		}
	}

}
