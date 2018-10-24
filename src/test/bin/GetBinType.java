package test.bin;

import org.junit.Test;

import ru.defo.managers.BinManager;
import ru.defo.model.WmBinType;

public class GetBinType {

	@Test
	public void test() {
		WmBinType binType = new WmBinType();
		for(int i=1; i<9; i++){
			binType = new BinManager().getBinTypeById(Long.valueOf(Integer.valueOf(i).longValue()));
			System.out.println("."+binType==null?"<null>":binType.getDescription());
		}
	}

}
