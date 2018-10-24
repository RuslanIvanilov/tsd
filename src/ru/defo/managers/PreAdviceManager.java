package ru.defo.managers;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.defo.model.WmAdvice;
import ru.defo.model.WmAdvicePos;
import ru.defo.model.WmAdviceType;
import ru.defo.model.WmPreAdvice;
import ru.defo.model.WmPreAdvicePos;
import ru.defo.model.local.advice.AdviceInvoice;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.EANcontrolNum;
import ru.defo.util.HibernateUtil;

public class PreAdviceManager extends ManagerTemplate {

		public PreAdviceManager() {
			super(WmPreAdvice.class);
		}

		@SuppressWarnings("unchecked")
		public List<WmPreAdvicePos> getPreAdvicePosByWmPosAdviceLink(Long wmPosAdviceLink) {
			Criteria criteria = HibernateUtil.getSession().createCriteria(WmPreAdvicePos.class);
			criteria.add(Restrictions.eq("wmAdvicePosLink", wmPosAdviceLink));
			return (List<WmPreAdvicePos>) criteria.list();
		}

		@SuppressWarnings("unchecked")
		public List<WmPreAdvice> getPreAdviceByWmAdviceLink(Long wmAdviceLink) {
			Criteria criteria = HibernateUtil.getSession().createCriteria(WmPreAdvice.class);
			criteria.add(Restrictions.eq("wmAdviceLink", wmAdviceLink));
			return (List<WmPreAdvice>) criteria.list();
		}

		@SuppressWarnings("unchecked")
		public List<WmAdviceType> getAdviceTypeList() {
			Criteria criteria = HibernateUtil.getSession().createCriteria(WmAdviceType.class);
			criteria.addOrder(Order.asc("adviceTypeId"));
			return (List<WmAdviceType>) criteria.list();
		}

		public boolean toPersist(WmPreAdvice preAdvice)
		{
			WmPreAdvice preAdv = getPreAdviceById(preAdvice.getAdviceId());
			return(preAdv == null || preAdv.getWmAdviceLink()==null || !preAdv.getExpectedDate().equals(preAdvice.getExpectedDate()) || preAdv.getErrorId().intValue() != preAdvice.getErrorId().intValue());

		}

		public WmPreAdvice initByAdviceInvoice(AdviceInvoice advInv)
		{
			WmPreAdvice preAdv = new WmPreAdvice();
			preAdv.setAdviceId(getNextPreAdviceId());
			preAdv.setAdviceCode(advInv.getDocNumber());
			preAdv.setAdviceTypeId(1L);
			preAdv.setExpectedDate(Timestamp.valueOf(advInv.getDocDate().replace('T', ' ')));
			if(advInv.getErrorId()>0)preAdv.setErrorId(Long.valueOf(advInv.getErrorId()));
			preAdv.setStatusId(DefaultValue.STATUS_CREATED);
			preAdv.setClientDocCode(advInv.getClient().getCode());
			preAdv.setClientDocDescription(advInv.getClient().getDescription());
			return preAdv;
		}

		public WmPreAdvice getPreAdviceById(Long adviceId) {
			Criteria criteria = HibernateUtil.getSession().createCriteria(WmPreAdvice.class);
			criteria.add(Restrictions.eq("adviceId", adviceId));
			criteria.setMaxResults(1);
			return (WmPreAdvice) criteria.uniqueResult();
		}

		public long getNextPreAdviceId() {
			return ((BigInteger) HibernateUtil.getSession().createSQLQuery("select nextval('seq_pre_advice_id')").uniqueResult()).longValue();
		}

		public WmAdvice createAdvice(WmPreAdvice preAdvice, Timestamp expDate) {

			WmAdvice advice = new WmAdvice();
			advice.setAdviceId(preAdvice.getWmAdviceLink());
			advice.setClientId(preAdvice.getClientId());
			advice.setAdviceTypeId(preAdvice.getAdviceTypeId());
			advice.setExpectedDate(expDate);

			String bc = String.valueOf((233000000000L + preAdvice.getWmAdviceLink().longValue()));
			StringBuilder strBldr = new StringBuilder(bc);
			strBldr.append(EANcontrolNum.getControlNum(bc));

			advice.setAdviceCode(strBldr.toString());
			advice.setStatusId(1L);

			return advice;
		}

		public void addPreAdviceLinesToAdviceLines(Session session, WmPreAdvice preAdvice, WmAdvice advice) {
			List<WmPreAdvicePos> preAdvPosList = new PreAdvicePosManager().getAdvicePosListById(preAdvice.getAdviceId());
			AdvicePosManager advPosMgr = new AdvicePosManager();

			try{
			for (WmPreAdvicePos preAdvPos : preAdvPosList) {

				WmAdvicePos advicePos0 = advPosMgr.getAdvicePosByArticleQState(preAdvice.getWmAdviceLink(),
						preAdvPos.getArticleId(), preAdvPos.getQualityStateId());
				if (advicePos0 != null) {
					preAdvPos.setWmAdvicePosLink(advicePos0.getAdvicePosId());
					advicePos0.setExpectQuantity((advicePos0.getExpectQuantity() + preAdvPos.getExpectQuantity()));
					session.persist(preAdvPos);
					session.persist(advicePos0);
				} else {
					WmAdvicePos advPos = new WmAdvicePos();
					advPos.setAdviceId(preAdvice.getWmAdviceLink());
					advPos.setAdvicePosId(new PreAdvicePosManager().getNextAdvicePosId());

					advPos.setArticleId(preAdvPos.getArticleId());
					advPos.setSkuId(preAdvPos.getSkuId());
					advPos.setQualityStateId(preAdvPos.getQualityStateId());
					advPos.setLotId(preAdvPos.getLotId());
					advPos.setStatusId(1L);
					advPos.setCreateDate(AppUtil.stringToTimestamp(AppUtil.getToday()));
					advPos.setErrorComment(preAdvPos.getErrorComment());
					advPos.setExpectQuantity(preAdvPos.getExpectQuantity());

					preAdvPos.setWmAdvicePosLink(advPos.getAdvicePosId());

					session.persist(preAdvPos);
					session.persist(advPos);
				}

				System.out.println("Pre Advice Pos : " + preAdvPos.getAdviceId() + " / " + preAdvPos.getAdvicePosId());
			}
			} catch(Exception e){
				preAdvice.setErrorId(5L);
				preAdvice.setErrorComment(new ErrorManager().getErrorById(5L).getDescription());
				session = HibernateUtil.getSession();
				session.getTransaction().begin();
				session.persist(preAdvice);
				session.getTransaction().commit();

				e.printStackTrace();
			}

		}

		public void addPreAdviceToAdvice(Long preAdviceId, Long nextAdviceId, Timestamp expDate) {
			// Проверка что нет связи с объединяющей складской приемкой
			WmPreAdvice preAdvice = getPreAdviceById(preAdviceId);
			if (preAdvice != null && preAdvice.getWmAdviceLink() == null) {
				preAdvice.setWmAdviceLink(nextAdviceId);

				WmAdvice advice = new AdviceManager().getAdviceById(nextAdviceId);
				if (advice == null) {
					// Если объединяющая приемка еще не создана, то создать
					advice = createAdvice(preAdvice, expDate);
				}
				Session session = HibernateUtil.getSession();
				session.getTransaction().begin();
				/* add qty to AdvicePos */
				addPreAdviceLinesToAdviceLines(session, preAdvice, advice);

				session.persist(advice);
				session.persist(preAdvice);
				session.getTransaction().commit();
				session.refresh(advice);

			}

		}


		@SuppressWarnings("unchecked")
		public List<WmPreAdvice> getPreAdviceListByFilter(HttpSession sessionHttp, int rowCount) {

			List<WmPreAdvice> wmPreAdvList = new ArrayList<WmPreAdvice>();

			Session session = HibernateUtil.getSession();
			Criteria criteria = session.createCriteria((WmPreAdvice.class).getName());

			if (sessionHttp.getAttribute("adviceflt") != null)
				criteria.add(Restrictions.ilike("adviceCode", sessionHttp.getAttribute("adviceflt").toString(), MatchMode.ANYWHERE));

			if (sessionHttp.getAttribute("clientdocflt") != null)
				criteria.add(Restrictions.ilike("clientDocCode", sessionHttp.getAttribute("clientdocflt").toString(), MatchMode.ANYWHERE));

			if (sessionHttp.getAttribute("clientdescrflt") != null)
				criteria.add(Restrictions.ilike("clientDocDescription", sessionHttp.getAttribute("clientdescrflt").toString(), MatchMode.ANYWHERE));

			if (!sessionHttp.getAttribute("dateflt").toString().isEmpty()) {
				criteria.add(Restrictions.ge("expectedDate", AppUtil.stringToTimestamp(sessionHttp.getAttribute("dateflt").toString())));
				criteria.add(Restrictions.lt("expectedDate", AppUtil.getNextDay(sessionHttp.getAttribute("dateflt").toString())));
			}

			if (!sessionHttp.getAttribute("typeflt").toString().isEmpty()) {
				criteria.add(Restrictions.eq("adviceTypeId", Long.valueOf(sessionHttp.getAttribute("typeflt").toString())));
			}

			if (!sessionHttp.getAttribute("statusflt").toString().isEmpty()) {
				criteria.add(Restrictions.eq("statusId", Long.valueOf(sessionHttp.getAttribute("statusflt").toString())));
			}


			try {
				if(sessionHttp.getAttribute("adviceLinkFlt") != null)
					setFilter(criteria, "eq", "wmAdviceLink", sessionHttp.getAttribute("adviceLinkFlt").toString());
			} catch (Exception e) {

				e.printStackTrace();
			}

			criteria.addOrder(Order.asc("adviceCode"));

			if (rowCount > 0)
				criteria.setMaxResults(rowCount);

			try{
				wmPreAdvList = (List<WmPreAdvice>) criteria.list();
			} catch(Exception e){
				e.printStackTrace();
			}

			return wmPreAdvList;
		}


		public String getClientDocDescription(Long wmAdviceLink) {
			Criteria criteria = HibernateUtil.getSession().createCriteria(WmPreAdvice.class);
			criteria.add(Restrictions.eq("wmAdviceLink", wmAdviceLink));
			criteria.addOrder(Order.asc("adviceId"));
			criteria.setMaxResults(1);
			WmPreAdvice preAdvice = (WmPreAdvice) criteria.uniqueResult();
			String clientDocDescription = preAdvice.getClientDocDescription();
			return clientDocDescription == null ? "" : clientDocDescription;
		}

		public void saveOrUpdatePreAdvice(WmPreAdvice advice){
			Session session =  HibernateUtil.getSession();
			session.getTransaction().begin();
			session.saveOrUpdate(advice);
			session.getTransaction().commit();
		}

}
