package test.bin;
 

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import ru.defo.controllers.BinController;
import ru.defo.managers.BinManager;
import ru.defo.model.WmBin;
import ru.defo.util.HibernateUtil;

public class BinsMirroring3 {

	@Test
	public void test() { 

		List<WmBin> binList = new BinManager().getAll();

		for(WmBin bin : binList){
			if(!bin.getAreaCode().equals("FRN")||
			 bin.getMirrorBinId()!= null ||
			 bin.getLevelNo().intValue()==0) continue;

			System.out.println("\n");
			System.out.println("for change: "+bin.getBinCode());
			System.out.println("binRackNo: "+(bin.getBinRackNo()==null?"NULL":bin.getRackNo()));

            WmBin mirroredBin = new BinController().calcMirrorBinForBin(bin);

            System.out.println("MirroredBin: "+mirroredBin.getBinCode());

            Session session = HibernateUtil.getSession();
            Transaction tx = session.getTransaction();
            tx.begin();
            bin.setMirrorBinId(mirroredBin.getBinId());
            session.merge(bin);
            tx.commit();

		}


	}





}
