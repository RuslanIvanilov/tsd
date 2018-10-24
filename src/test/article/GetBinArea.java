package test.article;
 

import java.util.List;
 
import org.junit.Test;

import ru.defo.filters.CriterionFilter; 
import ru.defo.model.WmBin; 
import ru.defo.util.Bc;
import ru.defo.util.HibernateUtil;

public class GetBinArea {

	@Test
	public void test() {

		CriterionFilter flt = new CriterionFilter();
		List<WmBin> binList = HibernateUtil.getObjectList(WmBin.class, flt.getFilterList(), 0, true, "binCode");

		for(WmBin bin : binList){
			String result = Bc.symbols("B"+bin.getBinCode());
			System.out.println(bin.getBinCode()+" --> "+result+" --> "+bin.getAreaCode()+" --> ["+bin.getBinCode().substring(0, 3)+"]");
		}
	}

}
