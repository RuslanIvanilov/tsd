package test.bin;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import ru.defo.filters.CriterionFilter;
import ru.defo.managers.BinManager;
import ru.defo.model.WmBin;
import ru.defo.util.HibernateUtil;

public class BinBarCode {
 

	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		Date sdate = new Date();
		System.out.println("start time : "+sdate.toGMTString());
		//bin = new BinManager().getBinByBarcode("BLK.022.023.01 ");
		//if(bin != null) System.out.println("bin --> "+bin.getBinId()+" "+bin.getBinCode());


		CriterionFilter flt = new CriterionFilter();
		//flt.addFilter("areaCode", "BLK", "like");
		List<WmBin> binList = HibernateUtil.getObjectList(WmBin.class, flt.getFilterList(), 0, true, "binCode");

		for(WmBin bin : binList)
		{
			bin = new BinManager().getBinByBarcode(bin.getBinCode()+" ");
			System.out.println("bin --> "+bin.getBinId()+" "+bin.getBinCode());
		}

		System.out.println("\n\nend time : "+new Date().toGMTString()+"\nelapsed time : "+ ((new Date().getTime()-sdate.getTime())/1000.000 ) +"sec.");

	}

}
