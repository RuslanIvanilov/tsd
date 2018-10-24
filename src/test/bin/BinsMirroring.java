package test.bin;
 

import java.util.List;
 
import org.junit.Test;

import ru.defo.managers.BinManager;
import ru.defo.model.WmBin; 

public class BinsMirroring {

	@Test
	public void test() {
		List<WmBin> binList = new BinManager().getAll();

		for(WmBin bin : binList){
			if(!(bin.getAreaCode().equals("FRN")) || (bin.getRackNo().intValue()>2 ) || ( bin.getBinRackNo().intValue()>10)) continue;

			WmBin foundBin = new BinManager().getBin(bin.getAreaCode(), bin.getRackNo(), bin.getLevelNo(), bin.getBinRackNo());
			if(foundBin instanceof WmBin)
				System.out.println("Found Bin: "+foundBin.getBinCode());

			WmBin mirrorBin = new BinManager().getBin(bin.getAreaCode(), bin.getRackNo().longValue()+1, bin.getLevelNo(), bin.getBinRackNo());
			if(mirrorBin instanceof WmBin){
				System.out.println("Mirrored Bin: "+mirrorBin.getBinCode());
				bin.setMirrorBinId(mirrorBin.getBinId());
				mirrorBin.setMirrorBinId(bin.getBinId());
/*
				Session session = HibernateUtil.getSession();
				Transaction tx = session.getTransaction();
				try{
					tx.begin();

					session.merge(bin);
					session.merge(mirrorBin);

					tx.commit();
				}catch(Exception e){
					e.printStackTrace();
					tx.rollback();
				}finally{
					session.close();
				}
*/

			}


			System.out.println(bin.getBinCode()+" : "+(bin.getMirrorBinId()==null?"null":bin.getMirrorBinId())+" < -- "+(mirrorBin==null?"NULL":mirrorBin.getMirrorBinId()==null?"null":mirrorBin.getMirrorBinId()));
		}

	}

}
