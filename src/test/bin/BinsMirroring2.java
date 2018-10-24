package test.bin;
 

import java.util.List;

import org.junit.Test;

import ru.defo.controllers.BinController;
import ru.defo.managers.BinManager;
import ru.defo.model.WmBin;

public class BinsMirroring2 {

	@Test
	public void test() {
		List<WmBin> binList = new BinManager().getAll();

		for(WmBin bin : binList){
			if(!bin.getAreaCode().equals("FRN")) continue;
			if(bin.getRackNo().intValue()%2 ==0){
				String sLevel = String.format("%02d", bin.getLevelNo().intValue());
				String sBin = String.format("%03d", bin.getBinRackNo().intValue());
				String sRack = String.format("%03d", (bin.getRackNo().intValue()-1));
				System.out.println("ch bin: "+bin.getBinCode()+" ... a:"+bin.getAreaCode()+"."+sRack+"."+sBin+"."+ sLevel+" real Level: "+bin.getLevelNo().intValue());

				WmBin mBin = new BinController().getBin(bin.getAreaCode()+"."+sRack+"."+sBin+"."+ sLevel);

				System.out.println("--> "+bin.getAreaCode()+"."+sRack+"."+sBin+"."+ sLevel+" :: "+mBin.getBinCode());

			}


		}
	}

}
