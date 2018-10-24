package ru.defo.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ru.defo.filters.CriterionFilter;
import ru.defo.managers.BinManager;
import ru.defo.managers.InventoryManager;
import ru.defo.managers.QuantManager;
import ru.defo.managers.SkuManager;
import ru.defo.managers.UnitManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmBin;
import ru.defo.model.WmInventory;
import ru.defo.model.WmInventoryInitiator;
import ru.defo.model.WmInventoryPos;
import ru.defo.model.WmQualityState;
import ru.defo.model.WmQuant;
import ru.defo.model.WmSku;
import ru.defo.model.WmUnit;
import ru.defo.model.WmUser;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;
import ru.defo.util.data.RestFilter;

public class InventoryController {

	public void closeInventByBin(Object binCodeObj){
		WmBin bin = new BinController().getBin(binCodeObj);
		if(!(bin instanceof WmBin)) return;

		List<WmInventoryPos> posList = getOpenPosListByBin(bin);

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		try{
			tx.begin();

			for(WmInventoryPos pos : posList){
				pos.setQuantityAfter(0L);
				pos.setStatusId(DefaultValue.COMPLETE_STATUS);
				session.merge(pos);
			}

			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			session.close();
		}


	}

	public List<WmInventoryPos> getOpenPosListByBin(WmBin bin){
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("binId", bin.getBinId().toString(), "eq");
		flt.addFilter("statusId", "<4", "<>");
		return HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 0, false, "inventoryId,inventoryPosId");
	}


	public boolean isInventOrdBin(WmBin bin, Object inventoryIdObj, Object userIdObj)
	{
		WmInventory invent = new InventoryController().getInventById(inventoryIdObj);
		WmUser user = new UserController().getUserById(userIdObj);
		List<WmInventoryPos> posList = getPosListByBin(bin, invent, user);
		return posList.size()>0;
	}

	public boolean hasOpenedInventByUnit(Object unitCodeObject)
	{
		WmUnit unit = new UnitManager().getUnitByCode(unitCodeObject.toString().trim());
		return new InventoryManager().getOpenPosListByUnit(unit).size()>0;
	}


	public boolean hasOpenedInventByBin(WmBin bin)
	{
		return getOpenPosListByBin(bin).size()>0;
	}

	public List<WmInventoryPos> getPosList(WmUser user, WmUnit unit, WmInventory invent)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("inventoryId", invent.getInventoryId().toString(), "eq");
		flt.addFilter("executorId", user.getUserId().toString(), "eq");
		flt.addFilter("unitId", unit.getUnitId().toString(), "eq");
		return HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 0, false, "inventoryId,inventoryPosId");
	}

	public List<WmInventoryPos> getPosListQtyNull(WmUser user, WmUnit unit, WmInventory invent)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("inventoryId", invent.getInventoryId().toString(), "eq");
		flt.addFilter("executorId", user.getUserId().toString(), "eq");
		flt.addFilter("unitId", unit.getUnitId().toString(), "eq");
		flt.addFilter("quantityAfter", "null", "isnull");
		return HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 0, false, "inventoryId,inventoryPosId");
	}

	public List<WmInventoryPos> getPosListByBin(WmBin bin, WmInventory invent, WmUser user)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("inventoryId", invent.getInventoryId().toString(), "eq");
		flt.addFilter("executorId", user.getUserId().toString(), "eq");
		flt.addFilter("binId", bin.getBinId().toString(), "eq");
		return HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 0, false, "inventoryId,inventoryPosId");

	}

	void closePos(Session session, WmInventoryPos pos){
		pos.setQuantityAfter(0L);
		pos.setStatusId(DefaultValue.COMPLETE_STATUS);
		session.update(pos);
	}

	public String closeUnitInventPosList(HttpSession sessionH)
	{
		WmUser user = new UserController().getUserById(sessionH.getAttribute("userid"));
		WmUnit unit = new UnitController().getUnitByUnitCode(sessionH.getAttribute("unitcode"));
		WmInventory invent = new InventoryController().getInventById(sessionH.getAttribute("inventoryid"));

		List<WmInventoryPos> posList = new InventoryManager().getUnconfirmedPosList(invent, user, null, unit, 0);

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		try{
			tx.begin();
			closePostList(session, posList);
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			session.close();
			return "Не удалось завершить строки по поддону отсутствующему в ячейке: ["+sessionH.getAttribute("unitcode").toString()+"]\n"+e.getMessage();
		}

		return "";
	}

	private int getRowCount(String rowCountTxt) throws NumberFormatException{ return Integer.decode(rowCountTxt); }

	private int getPosCount(WmInventory invent){
		List<WmInventoryPos> posList = new InventoryManager().getInventoryPosListByWmInventory(invent);
		if(posList.size()==0) return 0;

		return posList.get(0).getInventoryPosId().intValue();
	}

	public void canCloseInvent(Session session, WmInventory invent)
	{
		/* Если больше нет открытых строк, завершаем работу по всему заданию инвентаризации. */
		if(new InventoryManager().getOpenPosList(invent).size() == 0)
		{
			invent.setStatusId(DefaultValue.COMPLETE_STATUS);
			invent.setEndDate(new Timestamp(System.currentTimeMillis()));
			session.merge(invent);
		}

	}

	public String saveInvPos(HttpSession hSession)
	{
		InventoryController invCtrl = new InventoryController();

		WmUser user = new UserController().getUserById(hSession.getAttribute("userid"));
		WmInventory invent = invCtrl.getInventById(hSession.getAttribute("inventoryid"));
		WmBin bin = new BinController().getBin(hSession.getAttribute("bincode").toString());
		WmUnit unit = new UnitController().getUnitByUnitCode(hSession.getAttribute("unitcode"));
		WmQualityState state = new QualityStateController().getQStateById(Long.valueOf(hSession.getAttribute("qtStateSelected").toString()));

		@SuppressWarnings("unchecked")
		HashMap<Integer, String> map = (HashMap<Integer, String>) hSession.getAttribute("articlelist");

		for(Entry<Integer, String> entry : map.entrySet()){

			WmArticle article = new ArticleController().getArticle(entry.getKey());
			WmSku sku = new SkuManager().getBaseSkyByArticle(article.getArticleId());
			Long qty = Long.valueOf( AppUtil.getValueDataList(entry.getValue(), ":") );

			WmInventoryPos pos = invCtrl.getInventoryPos(user, invent, unit, article);
			if(pos == null)
				pos = invCtrl.getInventoryPos(hSession.getAttribute("inventoryid"), hSession.getAttribute("inventoryposid"));

			Session session = HibernateUtil.getSession();
			Transaction tx = session.getTransaction();
			try{
					tx.begin();

					WmQuant quantToDel = new QuantManager().getQuantByUnitArticle(unit, article);
					if(quantToDel instanceof WmQuant) session.delete(quantToDel);

					WmQuant quantToCreate = new QuantManager().initQuant(article, sku, state, qty, unit, pos.getInventoryId(), pos.getInventoryPosId(), user);
					session.persist(quantToCreate);

					unit.setBinId(bin.getBinId());
					session.merge(unit);

					if (pos.getArticleId()==null || !pos.getArticleId().equals(article.getArticleId())  || !pos.getUnitId().equals(unit.getUnitId()) || !pos.getBinId().equals(bin.getBinId()) )
					{
						WmInventoryPos posNew = new InventoryManager().createInventoryPos(invent, getPosCount(invent)+1, bin, unit, sku, 0L);
						posNew.setQuantityAfter(qty);
						posNew.setCreateDate(new Timestamp(System.currentTimeMillis()));
						posNew.setCreateUser(user.getUserId());
						posNew.setExecutorId(user.getUserId());
						posNew.setStatusId(DefaultValue.COMPLETE_STATUS);
						session.persist(posNew);
					} else {
						pos.setQuantityAfter(qty);
						pos.setStatusId(DefaultValue.COMPLETE_STATUS);
						session.merge(pos);
					}

					closeUnconfirmedPosList(session, invent, user, bin, unit);
					canCloseInvent(session, invent);

					tx.commit();
				}catch(Exception e){
					tx.rollback();
					if(session.isOpen())session.close();
					return "Не удается выполнить сохранение! Требуется повторно выполнить инвентаризацию: "+bin.getBinCode()+" / "+unit.getUnitCode()+" / "+article.getArticleCode();
				}finally{
					hSession.setAttribute("articlelist", null);
				}


		}

		return "";
	}

	public void closeUnconfirmedPosList(Session session, WmInventory invent, WmUser user, WmBin bin, WmUnit unit)
	{
		List<WmInventoryPos> posList = new InventoryManager().getUnconfirmedPosList(invent, user, bin, unit, 1);
		closePostList(session, posList);

	}

	private void closePostList(Session session, List<WmInventoryPos> posList){
		for(WmInventoryPos pos : posList){
			pos.setQuantityAfter(0L);
			pos.setStatusId(DefaultValue.COMPLETE_STATUS);
			session.merge(pos);
		}
	}


	/**@deprecated
	 * @see saveInvPos
	 * */
	public String saveInventoryPos(HttpSession hSession)
	{
		InventoryController invCtrl = new InventoryController();

		//WmInventoryPos pos = invCtrl.getInventoryPos(hSession.getAttribute("inventoryid"), hSession.getAttribute("inventoryposid"));

		WmInventory invent = invCtrl.getInventById(hSession.getAttribute("inventoryid"));
		WmBin bin = new BinController().getBin(hSession.getAttribute("bincode"));
		WmUnit unit = new UnitController().getUnitByUnitCode(hSession.getAttribute("unitcode"));

		WmArticle article = new ArticleController().getArticle(hSession.getAttribute("articleid"));
		WmSku sku = new SkuManager().getBaseSkyByArticle(article.getArticleId());
		WmQualityState state = new QualityStateController().getQStateById(Long.valueOf(hSession.getAttribute("qtStateSelected").toString()));
		Long qty = Long.valueOf(hSession.getAttribute("quantity").toString());
		WmUser user = new UserController().getUserById(hSession.getAttribute("userid"));

		WmInventoryPos pos = invCtrl.getInventoryPos(user, invent, unit, article);
		if(pos == null)
			pos = invCtrl.getInventoryPos(hSession.getAttribute("inventoryid"), hSession.getAttribute("inventoryposid"));

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		try{
				tx.begin();

				WmQuant quantToDel = new QuantManager().getQuantByUnitArticle(unit, article);
				if(quantToDel instanceof WmQuant) session.delete(quantToDel);

				WmQuant quantToCreate = new QuantManager().initQuant(article, sku, state, qty, unit, pos.getInventoryId(), pos.getInventoryPosId(), user);
				session.persist(quantToCreate);

				if (!pos.getArticleId().equals(article.getArticleId())  || !pos.getUnitId().equals(unit.getUnitId()))
				{
					WmInventoryPos posNew = new InventoryManager().createInventoryPos(invent, getPosCount(invent)+1, bin, unit, sku, 0L);
					posNew.setQuantityAfter(qty);
					posNew.setCreateDate(new Timestamp(System.currentTimeMillis()));
					posNew.setCreateUser(user.getUserId());
					posNew.setExecutorId(user.getUserId());
					posNew.setStatusId(DefaultValue.COMPLETE_STATUS);
					session.persist(posNew);

					unit.setBinId(bin.getBinId());
					session.merge(unit);
				} else {
					pos.setQuantityAfter(qty);
					pos.setStatusId(DefaultValue.COMPLETE_STATUS);
					session.merge(pos);
				}

				tx.commit();
			}catch(Exception e){
				tx.rollback();
				if(session.isOpen())session.close();
				return "Не удается выполнить сохранение! Требуется повторно выполнить инвентаризацию: "+bin.getBinCode()+" / "+unit.getUnitCode()+" / "+article.getArticleCode();
			}

		return "";
	}

	public String setBinClear(WmUser user, WmInventory invent, WmBin bin, WmUnit unit)
	{
		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		try{
				tx.begin();
				new QuantController().delQuantByUnitCode(session, user, unit);

				List<WmInventoryPos> posList = new InventoryManager().getPosList(invent, bin, unit);
				for(WmInventoryPos pos : posList)
				{
					/*if(pos.getUnitId()!= null)
					{
						//WmUnit unit = new UnitManager().getUnitById(pos.getUnitId());
						//if(unit instanceof WmUnit)
							new QuantController().delQuantByUnitCode(session, user, unit);
					}*/

					if(pos.getQuantityBefore().intValue()==0){
						session.delete(pos);
						continue;
					}
					pos.setQuantityAfter(null);
					pos.setStatusId(DefaultValue.STATUS_CREATED);
					session.merge(pos);
				}
				tx.commit();
				return "";
			}catch(Exception e){
				tx.rollback();
				if(session.isOpen())session.close();
				return "Не удалось выполнить операцию очистки ячейки ["+bin.getBinCode()+"]\n \n"+e.getStackTrace();
			}
	}

	public String setBinClear(HttpSession httpSession)
	{
		WmInventory invent = getInventById(httpSession.getAttribute("inventoryid"));
		if(!(invent instanceof WmInventory)) return this.getClass().getSimpleName()+".setBinEmpty : inventoryid is wrong!";

		WmUser user = new UserController().getUserById(httpSession.getAttribute("userid"));
		if(!(user instanceof WmUser)) return this.getClass().getSimpleName()+".setBinEmpty : userid is wrong!";

		WmBin bin = new BinController().getBin(httpSession.getAttribute("bincode").toString());
		if(!(bin instanceof WmBin)) return this.getClass().getSimpleName()+".setBinEmpty : bincode is wrong!";

		WmUnit unit = new UnitController().getUnitByUnitCode(httpSession.getAttribute("unitcode"));
		if(!(unit instanceof WmUnit)) return this.getClass().getSimpleName()+".setBinEmpty : unitcode is wrong!";


		return setBinClear(user, invent, bin, unit);

	}

	public String setBinEmpty(HttpSession httpSession){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		try{
			tx.begin();
			WmInventoryPos pos = getInventoryPos(httpSession.getAttribute("inventoryid"), httpSession.getAttribute("inventoryposid"));
			WmInventory invent = new InventoryManager().getInventById(pos.getInventoryId());
			WmUser user = new UserController().getUserById(httpSession.getAttribute("userid"));
			WmBin bin = new BinManager().getBinById(pos.getBinId());

			if(httpSession.getAttribute("unitcode")==null){
				List<WmUnit> unitList = new UnitManager().getUnitListByBin(bin);

				for(WmUnit unit : unitList){
					new QuantController().delQuantByUnitCode(session, user, unit, invent);
				}
			} else {
				WmUnit unit = new UnitController().getUnitByUnitCode(httpSession.getAttribute("unitcode"));
				if(unit instanceof WmUnit)
					new QuantController().delQuantByUnitCode(session, user, unit);
			}

			WmUnit unit = null;
			if(httpSession.getAttribute("unitcode")!=null)
				unit = new UnitController().getUnitByUnitCode(httpSession.getAttribute("unitcode"));

			WmArticle article = null;
			if(httpSession.getAttribute("articleid")!=null)
				article = new ArticleController().getArticle(httpSession.getAttribute("articleid"));

			List<WmInventoryPos> posList = getPosList(invent, bin, unit, article);
			for(WmInventoryPos pos0 : posList){
				if(pos.getQuantityAfter()==null)
					closePos(session, pos0);
			}
			canCloseInvent(session, invent);

			//pos.setQuantityAfter(0L);
			//pos.setStatusId(DefaultValue.COMPLETE_STATUS);
			//session.update(pos);

			tx.commit();
			//httpSession.setAttribute("bincode", null);
			return "";
		}catch(Exception e){
			tx.rollback();
			return "Не удалось выполнить операцию очистки ячейки ["+httpSession.getAttribute("binocode").toString()+"]\n \n"+e.getStackTrace();
		}finally{
			if(session.isOpen())session.close();
		}
	}


	public  WmInventoryPos getInventoryPos(WmUser user, WmInventory invent, WmUnit unit, WmArticle article)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("inventoryId", invent.getInventoryId()));
		restList.add(Restrictions.eq("unitId", unit.getUnitId()));
		restList.add(Restrictions.eq("articleId", article.getArticleId()));
		restList.add(Restrictions.eq("executorId", user.getUserId()));

		WmInventoryPos pos = (WmInventoryPos)HibernateUtil.getUniqObject(WmInventoryPos.class, restList, false);

		return pos;
	}

	public WmInventoryPos getInventoryPos(Object inventoryIdObj, Object inventoryPosIdObj)
	{
		Long inventId = AppUtil.strToLong(inventoryIdObj.toString());
		Long inventPosId = 	AppUtil.strToLong(inventoryPosIdObj.toString());

		return new InventoryManager().getInventoryPos(inventId, inventPosId);
	}


	public WmInventoryPos getUserInventPos(WmUser user)
	{
		return new InventoryManager().getCurrentActivePos(user);
	}

	public  WmInventory getInventById(Object inventIdObj)
	{
		Long inventId;
		try{
			inventId = Long.valueOf(inventIdObj.toString());
		}catch(NumberFormatException e){
			return null;
		}

		return getInventById(inventId);
	}

	public void delInventory(String inventoryIdTxt){
		InventoryManager invMgr = new InventoryManager();
		Long inventId = AppUtil.strToLong(inventoryIdTxt);
		WmInventory invent = invMgr.getInventById(inventId);

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		try{
			tx.begin();

			List<WmInventoryPos> posList = invMgr.getInventoryPosListByWmInventory(invent);
			for(WmInventoryPos pos : posList){
				session.delete(pos);
			}
			session.delete(invent);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			session.close();
		}

	}

	public void setExecutor(HttpSession session){
		//if(session.getAttribute("marked") == null) return;
		Session sessionH = HibernateUtil.getSession();

		WmUser user = new WmUser();
		if(!session.getAttribute("executorselect").toString().equals(DefaultValue.EMPTY_OPTION)){
			//List<WmUser> userList = new UserController().getUserListByUserFlt(session.getAttribute("executorselect").toString());
			//user = userList.get(0);

			user = new UserController().getUserByUserFlt(session.getAttribute("executorselect").toString());
		}

		List<String> list = new ArrayList<String>(Arrays.asList( session.getAttribute("marked").toString().split(",")));

		Transaction tx = sessionH.getTransaction();
		try{
			tx.begin();
			for(String posid : list){
				WmInventoryPos pos = getPos(session.getAttribute("inventid").toString(), posid);
				pos.setExecutorId(user.getUserId());
				sessionH.persist(pos);
			}
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}finally{
			sessionH.close();
		}



	}

	public WmInventory getInventById(Long inventId)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("inventoryId", inventId));
		WmInventory invent = (WmInventory) HibernateUtil.getUniqObject(WmInventory.class, restList, true);

		return invent;
	}

	public String getActualHostBalanceDate(){
		WmInventory invent = new InventoryManager().getActualInventory(DefaultValue.INITIATOR_HOST);
		if(!(invent instanceof WmInventory)) return null;
		return AppUtil.getLocalDateTime(invent.getCreateDate());
	}

	public List<WmInventory> getInventList(HttpSession session){

		int rowCnt = getRowCount(session.getAttribute("rowcount").toString());
        String inventFlt = session.getAttribute("inventflt").toString();
        String initerFlt = session.getAttribute("initerflt").toString();
        String dateFlt = session.getAttribute("dateflt").toString();
        String creatorFlt = session.getAttribute("creatorflt").toString();
        String statusFlt = session.getAttribute("statusflt").toString();
        String hidehost = session.getAttribute("hidehost").toString();

        String areaCodeFlt = session.getAttribute("areaflt").toString();
        String rackFromFlt = session.getAttribute("rackfromflt").toString();
        String rackToFlt = session.getAttribute("racktoflt").toString();
        String levelFromFlt = session.getAttribute("levelfromflt").toString();
        String levelToFlt = session.getAttribute("leveltoflt").toString();
        String binFromFlt = session.getAttribute("binfromflt").toString();
        String binToFlt = session.getAttribute("bintoflt").toString();


		//return new InventoryManager().getInventoryListByType(DefaultValue.INITIATOR_WMS);
        return new InventoryManager().getInventoryListByFilter(inventFlt, initerFlt, dateFlt, creatorFlt, statusFlt, hidehost, rowCnt,
        		areaCodeFlt, rackFromFlt, rackToFlt, levelFromFlt, levelToFlt, binFromFlt, binToFlt);

	}

	public WmInventoryInitiator getInitiator(Long initiatorId)
	{
		List<SimpleExpression> restList = new ArrayList<SimpleExpression>();
		restList.add(Restrictions.eq("initiatorId", initiatorId));
		WmInventoryInitiator initiator = (WmInventoryInitiator)HibernateUtil.getUniqObject(WmInventoryInitiator.class, restList, true);

		return initiator;
	}

	public WmInventoryInitiator getInitiator(String description)
	{

		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("description", description, "like");
		List<WmInventoryInitiator> initiatorList =  HibernateUtil.getObjectList(WmInventoryInitiator.class, flt.getFilterList(), 1, true, "description");


		if(!(initiatorList.get(0) instanceof WmInventoryInitiator)) return null;

		return initiatorList.get(0);
	}

	private String getBetweenFlt(Object startValue, Object endValue){

		int startInt = 0, endInt = 999;

		if(!startValue.toString().isEmpty()){
			startInt = Integer.decode(startValue.toString());
		}

		if(!endValue.toString().isEmpty()){
			endInt = Integer.decode(endValue.toString());
		}

		return startInt+","+endInt;
	}

	public boolean createInventory(HttpSession session) throws NumberFormatException
	{

		if(session.getAttribute("userid").toString().isEmpty()) return false;

		if(
		   session.getAttribute("areatxt").toString().isEmpty() &&
		   session.getAttribute("rackstart").toString().isEmpty() &&
		   session.getAttribute("rackend").toString().isEmpty() &&
		   session.getAttribute("levelstart").toString().isEmpty() &&
		   session.getAttribute("levelend").toString().isEmpty() &&
		   session.getAttribute("binstart").toString().isEmpty() &&
		   session.getAttribute("binend").toString().isEmpty() &&
		   session.getAttribute("articletxt").toString().isEmpty()     //<-- из Quant
		   ) return false;

		CriterionFilter flt = new CriterionFilter();
		if(!session.getAttribute("areatxt").toString().isEmpty()) flt.addFilter("areaCode", session.getAttribute("areatxt").toString(), "like");

		flt.addFilter("rackNo", getBetweenFlt(session.getAttribute("rackstart"), session.getAttribute("rackend")) , "between");
		flt.addFilter("levelNo", getBetweenFlt(session.getAttribute("levelstart"), session.getAttribute("levelend")) , "between");
		flt.addFilter("binRackNo", getBetweenFlt(session.getAttribute("binstart"), session.getAttribute("binend")) , "between");

		List<WmBin> binList = HibernateUtil.getObjectList(WmBin.class, flt.getFilterList(), 0, false, "binId");

		WmUser user = new UserController().getUserById(session.getAttribute("userid"));

		return createInventoryByBinList(binList, user, getTmpInventBySession(session));

	}

	private WmInventory getTmpInventBySession(HttpSession session)
	{
		WmInventory inv = new WmInventory();
		if(session.getAttribute("areatxt")!= null) inv.setAreaCodeFilter(session.getAttribute("areatxt").toString());
		if( session.getAttribute("rackstart") !=null)	inv.setRackFromFilter(Long.valueOf(session.getAttribute("rackstart").toString()) );
		if( session.getAttribute("rackend") !=null) 	inv.setRackToFilter(Long.valueOf(session.getAttribute("rackend").toString()) );
		if( session.getAttribute("levelstart") !=null)	inv.setLevelFromFilter(Long.valueOf(session.getAttribute("levelstart").toString()) );
		if( session.getAttribute("levelend") !=null) 	inv.setLevelToFilter(Long.valueOf(session.getAttribute("levelend").toString()) );
		if( session.getAttribute("binstart") !=null)	inv.setBinFromFilter(Long.valueOf(session.getAttribute("binstart").toString()) );
		if( session.getAttribute("binend") !=null) 		inv.setBinToFilter(Long.valueOf(session.getAttribute("binend").toString()) );

		return inv;
	}

	private WmInventory setInventFilterFields(WmInventory source, WmInventory destination)
	{
		destination.setAreaCodeFilter(source.getAreaCodeFilter());
		destination.setRackFromFilter(source.getRackFromFilter());
		destination.setRackToFilter(source.getRackToFilter());
		destination.setLevelFromFilter(source.getLevelFromFilter());
		destination.setLevelToFilter(source.getLevelToFilter());
		destination.setBinFromFilter(source.getBinFromFilter());
		destination.setBinToFilter(source.getBinToFilter());

		return destination;
	}

	public boolean createInventoryByBinList(List<WmBin> binList, WmUser user, WmInventory inventTmp){
		WmInventory invent = new InventoryManager().createInventory(DefaultValue.INITIATOR_WMS, user);
		invent = setInventFilterFields(inventTmp, invent);

		List<WmInventoryPos> posList = initInventPosByBinList(invent, binList);

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		try{
			tx.begin();

			session.persist(invent);

			for(WmInventoryPos pos : posList){
				//System.out.println(pos.getInventoryId()+" / "+pos.getInventoryPosId());
				session.persist(pos);
			}

			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			return false;
		}finally{
			session.close();
		}
		return true;
	}

	public WmInventoryPos getFirstPos(WmUser user, WmInventory invent,  WmBin bin)
	{
		CriterionFilter flt = new CriterionFilter();
		flt.addFilter("inventoryId",  invent.getInventoryId().toString() , "eq");
		flt.addFilter("executorId",  user.getUserId().toString() , "eq");
		flt.addFilter("binId", bin.getBinId().toString() , "eq");

		List<WmInventoryPos> inventList = HibernateUtil.getObjectList(WmInventoryPos.class, flt.getFilterList(), 0, false, "quantityAfter");

		return inventList.get(0);
	}

	public WmInventoryPos getPos(String inventoryIdTxt, String inventoryPosTxt){

		Long inventId = Long.valueOf(inventoryIdTxt.toString());
		Long inventPosId = Long.valueOf(inventoryPosTxt.toString());

		WmInventoryPos pos = new InventoryManager().getInventoryPos(inventId, inventPosId);
		if(pos==null) return null;

		return pos;
	}


	private void createInventoryPos(List<WmInventoryPos> invPos, WmInventory invent,  WmBin bin, WmUnit unit, WmSku sku, Long quantityBefore){
		WmInventoryPos pos = new InventoryManager().createInventoryPos(invent, (invPos.size()+1), bin, unit, sku, quantityBefore);
		invPos.add(pos);
	}

	private List<WmInventoryPos>  initInventPosByBinList(WmInventory invent, List<WmBin> binList)
	{
		UnitManager unitMgr = new UnitManager();
		QuantManager quantMgr = new QuantManager();
		int lastPosId = 1;

		List<WmInventoryPos> invPos = new ArrayList<WmInventoryPos>();

		for(WmBin bin : binList)
		{
			List<WmUnit> unitList = unitMgr.getUnitListByBinId(bin.getBinId());
			if(unitList.size()==0){
				createInventoryPos(invPos, invent, bin, null, null, null);
			} else {
				for(WmUnit unit : unitList){
					List<WmQuant> quantList = quantMgr.getQuantListByUnit(unit);
					if(quantList.size()==0){
						createInventoryPos(invPos, invent, bin, unit, null, null);
					} else {
						for(WmQuant quant : quantList){
							WmSku sku = new SkuManager().getSkuById(quant.getSkuId());
							createInventoryPos(invPos, invent, bin, unit, sku, quant.getQuantity());
						}
					}
				}
			}



		}
	return invPos;
	}


	public List<WmInventoryPos> getPosList(WmInventory invent, WmBin bin, WmUnit unit, WmArticle article)
	{
		return new InventoryManager().getPosList(invent, bin, unit, article);
	}

}
