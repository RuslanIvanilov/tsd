package test.unit;


import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ru.defo.managers.UnitTypeManager;
import ru.defo.model.WmUnitType;

public class GetUnitTypeByCode {

	@Test
	public void test() {
		//fail("Not yet implemented");
		WmUnitType wmUnitType = null;
		List<String> typeList = Arrays.asList("EU", "IN", "IS", "SS");

		for(String type : typeList){
			wmUnitType = new UnitTypeManager().getUnitTypeByCode(type);
			if(wmUnitType != null) System.out.println("unit type : "+wmUnitType.getUnitTypeCode()+" / "+wmUnitType.getDescription());
		}

	}

}
