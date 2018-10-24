package test.order;
 

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import ru.defo.managers.OrderManager;
import ru.defo.managers.PreOrderManager;
import ru.defo.model.WmOrder;
import ru.defo.util.HibernateUtil;

public class CheckOrderQty {

	@Test
	public void test() {

		WmOrder order = new OrderManager().getOrderByCode("2220000275118", false);

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		tx.begin();

		new PreOrderManager().checkOrderQty(session, order);
		session.merge(order);
		tx.commit();
		session.close();

	}

}
