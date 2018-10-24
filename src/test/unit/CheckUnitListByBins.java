package test.unit;
 

import java.util.List;

import org.junit.Test;

import ru.defo.managers.BinManager;
import ru.defo.managers.UnitManager;
import ru.defo.model.WmBin;
import ru.defo.model.WmUnit;

public class CheckUnitListByBins {

	@Test
	public void test() {
		List<WmBin> binList = new BinManager().getAll();

		for(WmBin bin : binList){
			System.out.println("----> "+bin.getBinCode());
			List<WmUnit> unitList = new UnitManager().getUnitListByBin(bin);
			for(WmUnit unit : unitList){
				System.out.println(unit.getUnitCode());
			}

		}

	}

}
