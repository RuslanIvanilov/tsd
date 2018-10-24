 
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
 
import ru.defo.managers.BinManager;  
import ru.defo.model.WmBin; 
import ru.defo.util.HibernateUtil;

/**
 * Created by syn-wms on 21.03.2017.
 */
public class Main {
	/*
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            ourSessionFactory = new Configuration().
                    configure("hibernate.cfg.xml").
                    buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }
	*/

	public static void printPermissionForUser(Session session, int user){
		/*
		Long userId = Long.valueOf(String.valueOf(user));
		List<WmUserPermission> userPermList = new UserManager().getUserPermissionList(session, userId);

		for(WmUserPermission userPerm : userPermList){
			System.out.println("user "+user+" : permissionId : "+userPerm.getPermissionId()+" group : "+new UserManager().getPermission(session, userPerm.getPermissionId()).getGroupCode() );
		}
		*/
	}

    public static void main(String[] args) throws Exception {
        //final Session session = getSession();
        //System.out.println("parse = "+Integer.parseInt("010"));
        //System.out.println("decode = "+Integer.decode("010"));

    	//System.out.println(new Time(System.currentTimeMillis()).toString());

    	//System.out.println("START :: "+(Main.class).getSimpleName());

        try {

        	List<WmBin> binList = new BinManager().getAll();

        	for(WmBin bin : binList){
        		if(!bin.getAreaCode().equals("FRN")) continue;

        		if(bin.getMirrorBinId()==null){
        			System.out.println(bin.getBinCode()+" - не имеет ЗАРКАЛЬНОЙ ячейки!");
        			continue;
        		}

        		if((bin.getRackNo().floatValue()%2)> 0) continue;

        		//if(bin.getRackNo().intValue()>4) continue;

        		System.out.println("ДО: "+bin.getBinCode()+" --> "+new BinManager().getBinById(bin.getMirrorBinId()).getBinCode());

        		WmBin binForMirr = new BinManager().getBin(bin.getAreaCode(), bin.getRackNo().longValue()-1, bin.getLevelNo(), bin.getBinRackNo());
        		//System.out.println("Заменить на: "+binForMirr.getBinCode());

        		bin.setMirrorBinId(binForMirr.getBinId());

        		Session session = HibernateUtil.getSession();
        		Transaction tx = session.getTransaction();
        		tx.begin();
        		session.merge(bin);
        		tx.commit();

        		WmBin binN = new BinManager().getBinById(bin.getBinId());

        		System.out.println("ПОСЛЕ: "+binN.getBinCode()+" --> "+new BinManager().getBinById(binN.getMirrorBinId()).getBinCode());

        	}


        	//System.out.println("defaultCharset : "+Charset.defaultCharset().name());

        	//System.out.println(new String("Проверка кодовой страницы".getBytes("UTF-8"), Charset.forName("cp1251"))); //cp1251

        	//WmArticle art = new ArticleController().getArticleByCode("7#17528+01");
        	//System.out.println(art.getArticleId()+ " / "+art.getArticleCode()+" / "+art.getDescription());

        	/* String s = "7#88147+00 : 157";
        	 System.out.println(s.substring(s.indexOf(":")+1, s.length()).trim());
        	 */
        	/*
        	for(int i=0; i<10; i++){
        		long nextHistoryId = new HistoryManager().getNextHistoryId();
        		System.out.println(i+". nextHistoryId : "+nextHistoryId);
        	}*/

        	/*
        	@SuppressWarnings("unused")
			WmBinType binType = new WmBinType();
        	binType.setDescription("test");
        	Session session = HibernateUtil.getSession();
        	Transaction tx = session.getTransaction();
        	tx.begin();

        	session.persist(binType);
        	tx.commit();

        	List<WmBinType> binTypeList = new BinManager().getBinTypeList();
        	for(WmBinType binType0 : binTypeList){
        		System.out.println(binType0.getBinTypeId()+" : "+binType0.getDescription());
        	}

        	session.close();
        	*/


        	/*
        	StringBuilder sb = new StringBuilder("");
        	List<WmBin> binList = new BinManager().getAll();

        	System.out.println("binList.size() : "+binList.size());

        	for(WmBin bin : binList){
        		sb.append(","+bin.getBinId().toString());
        	}

        	System.out.println("sb size: "+sb.length());

        	System.out.println("List -> str : "+sb.toString().substring(1, sb.length()));
        	*/

        	/*
        	String s = "12S";
        	Long l = Long.valueOf(s);
        	*/

/*
        	List<WmUnit> unitList = new UnitController().getUnitListByBinId(1L); //34412L
        	System.out.println("unitList size: "+unitList.size());

        	for(WmUnit unit : unitList){
        		System.out.println(unit.getUnitCode());
        	}
*/

        	/*
        	Client client = new Client();
        	client.setCode("ЦФ-1784");
        	client.setDescription("Какой-то клиент");

        	System.out.println(client.toString());
        	*/

        	/*
        	System.out.println("before error gen test --->");

        	String str = null;

        	if(!(str instanceof String)) { System.out.println("not String"); } else {System.out.println("Is string!");}
        	*/

        	//throw new NullPointerException("generated test!");

        	//System.out.println("after error gen test <---");

        	/*
        	Session session = HibernateUtil.getSession();

        	WmAdviceType advTd = session.get(WmAdviceType.class, 3L);
        	try{
        		session.getTransaction().begin();
        		session.delete(advTd);
        		session.getTransaction().commit();
        	}catch(IllegalArgumentException e){
        		e.printStackTrace();
        		session.getTransaction().rollback();
        	}

        	WmAdviceType advT = new WmAdviceType();
        	WmAdviceType advT0 = session.get(WmAdviceType.class, Long.valueOf("1"));
        	advT.setAdviceTypeId(3L);
        	advT.setDescription(advT0.getDescription()+"\tTest type");

        	try{
        		session.getTransaction().begin();
        		session.persist(advT);
        		session.getTransaction().commit();
        	}catch(Exception e){
        		e.printStackTrace();
        		session.getTransaction().rollback();
        	}



        	@SuppressWarnings("unchecked")
			List<WmAdviceType> advTypeList =  session.createCriteria(WmAdviceType.class).list();

        	for(WmAdviceType advType : advTypeList){
        		System.out.println(advType.getAdviceTypeId()+"."+advType.getDescription());
        	}
        	*/


        	/*
        	List<WmUnit> unitList = new UnitManager().getUnitListByBinIdArticleId(29831L, 250424L);

        	for(WmUnit unit : unitList){
        		System.out.println(unit.getUnitCode());
        	}
        	*/

        	//System.out.println(new BinController().equalBins("FRN.001.023.03", "FRN.001.023.00"));

        	//System.out.println(new BinController().getBinNoByBinCode("FRN.001.023.03"));

        	//System.out.println(Long.valueOf(("FRN.001.012.093.01".substring(12, 15))));

/*
        	List<WmBinType> binTypeList = new BinManager().getBinTypeList();
        	for(WmBinType binType : binTypeList){
        		System.out.println(binType.getBinTypeId()+" : "+ binType.getDescription());
        	}

        	System.out.println("-------------------------------");

        	for(int i=1; i<=9; i++){
        		WmBinType binType =  new BinManager().getBinTypeById(Long.valueOf(Integer.valueOf(i).longValue()));
        		System.out.println(i+"."+binType.getBinTypeId()+"/"+binType.getDescription());
        	}
*/
        	/*
        	Map<WmVendorGroup, Long> groupMap =  new OrderController().getOpenPickGroupListByOrderId(17178L);
        	for(Map.Entry<WmVendorGroup, Long> entry : groupMap.entrySet())
        	{
        		System.out.println(entry.getKey().getDescription()+" : "+entry.getValue());
        	}
        	 */


        	/*
        	Vorderpos op = new Vorderpos();

        	FieldFilter filter = new FieldFilter();
        	filter.add("orderId", "=");
        	filter.add("statusId", "<");

        	System.out.println(filter.get().get("statusId").toString() );
			*/



        	//System.out.println(op.getClass().getField("orderId").getName());

        	/*
        	String unitCode0 = "EU00012345";
        	int Id;

        	System.out.println(Integer.valueOf(unitCode0.substring(2)));


        	Session session = HibernateUtil.getSession();


        	Long unitId = 50000L;
        	String unitCode = "EU"+String.valueOf((100000000+unitId)).substring(1);

        	System.out.println("unitCode : "+unitCode);
        	 */

        	/*
        	for(int i=1; i <= 77; i++){

        		printPermissionForUser(session, i);
        		printPermissionForUser(session, 78-i);

        	}

        	System.out.println("----------------------------");

        	List<Long> modules = new ArrayList<Long>();
        	for(int i=1; i<=77; i++){
        		Long userId = Long.valueOf(String.valueOf(i));
        		modules = new UserManager().getListModules(session, userId);

        		if(modules==null) continue;

        		for(Long moduleId : modules){
        			System.out.println(i+". perm : "+moduleId);
        		}
        		System.out.println("Total: "+modules.size()+"\n");

        	}
        	*/

        	/*
        	int i=0;
        	List<WmArticle> articleList  = new ArticleController().getArticleListByArtCodeFilter("");
        	WmInventoryPos pos = null;
        	for(WmArticle article : articleList){
        		if(article.getArticleKitId() != null){
        			pos = new InventoryManager().getActualInventPos(article.getArticleId());
        		}

        		Long expQty = 0L;
        		if(pos!=null) expQty = pos.getQuantityBefore();

        		if(expQty.intValue()>0){
        			i++;
        			System.out.println(i+". "+article.getArticleId()+" | "+article.getArticleCode()+" | "+article.getDescription()+" ::: "+expQty);
        		}
        	}
        	*/


        	/*
        	String[] units = {
        			"EU00003017",
        			"EU00008395"
        	};

        	for(int i = 0; i<units.length; i++){
        		System.out.println(i+". "+units[i]);
        		new PickController().removeShptData(1, null, units[i], null);
        	}
        	 */
/*
        	PreOrderManager mgr = new PreOrderManager();
        	WmPreOrder preOrder = mgr.getPreOrderById(700567L);

        	WmPreOrder preOrder0 = new WmPreOrder();
        	preOrder0.setOrderCode(preOrder.getOrderCode());
        	preOrder0.setStatusId(preOrder.getStatusId());
        	preOrder0.setOrderTypeId(preOrder.getOrderTypeId());
*/
        	//preOrder0.setRouteId(1L);


/*
        	System.out.println("equal : "+preOrder0.getRouteId().equals(preOrder.getRouteId()==null?0L:preOrder.getRouteId())
        			+"\n  :"+preOrder.getRouteId()+"\n0 :"+preOrder0.getRouteId());
*/
 //       	System.out.println("::::::::>>> "+preOrder0.equals(preOrder));

        	/*
        	PreOrderManager mgr = new PreOrderManager();
        	List<WmPreOrder> preOrderList =  mgr.getPreOrderListByRoute(null);
        	for(WmPreOrder preOrder : preOrderList){
        		System.out.println(preOrder.getOrderId()+" : "+preOrder.getOrderCode()+" : "+preOrder.getRouteId()+" / "+preOrder.getRouteCode());
        	}*/


/*
        	Vroute vroute = Vroute.getByRouteId(2275L);
        	System.out.println(vroute.getRouteCode()+" : "+vroute.getOrderLinkCount()+" date : "+vroute.getExpectedDate());

        	List<Vroute> vrouteList =  Vroute.getVrouteList("");
        	for(Vroute route : vrouteList){
        		System.out.println(route.getRouteCode()+" : pre = "+route.getPreOrderCount()+" word = "+route.getOrderLinkCount());
        	}
*/
        	//List<Vunitinfo> unitInfoList = (List<Vunitinfo>) new VUnitInfoManager().getVunitinfoListByArticleId(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 250);

        	//for(Vunitinfo unitinfo : unitInfoList){
        	//	System.out.println(unitinfo.getArticleCode()+" == "+unitinfo.getVendorGroupName());
        	//}

//        	System.out.println("cnt :"+new PickManager().getUnitsBalanceByOrderStatus(7590L, DefaultValue.SHIPPED_UNIT_STATUS, false));
//        	System.out.println("cnt n :"+new PickManager().getUnitsBalanceByOrderStatus(7590L, 3L, true));

///////////////////////////////////////////

        	/*--if(new XmlDocController(new SAD_WMSObmenPortTypeProxy().getSkladskoySostav("WMS", false, null)).loadArticleData())
    			--System.out.println("WebServiceClient: Article Data success loaded.");*/

        	//if(new XmlDocController(new SAD_WMSObmenPortTypeProxy().getAdvices("WMS")).processAdviceData())
    		//	System.out.println("WebServiceClient: Advices Data success loaded.");




/*
        	if(new XmlDocController(new SAD_WMSObmenPortTypeProxy().getMovements("WMS")).processMovementData())
    			System.out.println("WebServiceClient: Movement Data success loaded.");
*/

        	//WmInventory inv = new InventoryManager().getInventoryByDate(AppUtil.stringToTimestamp(AppUtil.getToday()));
        	//System.out.println("inv : "+inv.getInventoryId()+" | "+inv.getCreateDate());

/*

//BALANCE_LOADER -->
        	if(new XmlDocController(new SAD_WMSObmenPortTypeProxy().getBalance("WMS", true)).processBalanceData(DefaultValue.INITIATOR_HOST, true)){
    			System.out.println("WebServiceClient: Balance Data success loaded.");
        	} else {
        		System.out.println("WebServiceClient: Balance Data NOT loaded!");
        	}

    		System.out.println("WebClient END.");
    		HibernateUtil.close();
*/

////////////////////////////////////////////


/*
        	int i = 0;
        	List<WmArticle> articles = new ArticleManager().getArticleList();
        	for(WmArticle article : articles){
        		i++;
        		System.out.println(i+" : "+article.getArticleId()+" | "+article.getArticleCode()+" | "+article.getDescription());
        	}
*/

        	//new ArticleServiceImpl().getArticlesXML();

        	//String carHeap = "123 1";
        	//WmCar car  = null;
/*
        	for(int i=0; i<carHeap.length()-1; i++){
        		if(Character.isWhitespace(carHeap.charAt(i)) && (!(car instanceof WmCar))){
        			car =  new WmCar(carHeap.substring(0, i), carHeap.substring(i, carHeap.length()).trim());
        			break;
        		}
        	}
*/
        	//car = new WmCar(carHeap);

        	//System.out.println("car : ["+car.getCarMark() + "] \n no : ["+car.getCarCode()+"]");

        	//String dateStr = "03.11.2017 0:00:00";

        	//System.out.println("ts....: "+AppUtil.convert1Cdate(dateStr));
        	/*
        	 * DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        	Date date = format.parse("2017-01-01");
        	System.out.println(new Timestamp(date.getTime()));
        	 * */

/*
        	System.out.println(new Date().getTime());

        	Thread.sleep(3000);
        	System.out.println(new Date().getTime());
*/


/*
        	Long longValue = null;

        	if (!(longValue instanceof Long)){
        		System.out.println("Test Ok");
        	} else {
        		System.out.println("lose test!");
        	}
*/


/*
        	ArticleManager artMgr = new ArticleManager();


        	OrderManager ordMgr = new OrderManager();



        	System.out.println("не очищены сессии");
*/
/*
        	WmError err = new ErrorManager().getErrorById(4L);
        	System.out.println(err.getDescription());
*/
/*
        	BarcodeManager bcMgr = new BarcodeManager();

        	WmArticle article = new ArticleController().getArticleByCode("7#75212+00");

        	System.out.println("BC is : "+bcMgr.BcByArticleId(article.getArticleId()));
*/
/*
        	WmPick pick = new PickManager().fillControlPick(30377L, 38L, 266081L, 266076L, 1L, 18L, 99L, 6808L, 1L);

        	WmOrderPos orderPos  = new OrderPosManager().getOrderPosByArticleQState(99L, 266081L, 1L);
        	if(orderPos == null){
        		System.out.println("ERROR: Нет такой строки!");
        	} else {
        		new PickManager().addOrUpdatePick(pick);
        		orderPos.setFactQuantity((orderPos.getFactQuantity()==null?0:orderPos.getFactQuantity())+18L);
        		new OrderPosManager().saveOrderPos(orderPos);
        	}


        	System.out.println("pick : "+ pick.getPickId());
*/

        	/*
        	Timestamp date = AppUtil.stringToTimestamp(AppUtil.getToday());
        	System.out.println("date : "+date);
			*/

        	/*
        	BinManager binMgr = new BinManager();
        	WmBin bin = binMgr.getDokByNumber(7);
        	System.out.println("bin : "+bin.getBinCode());
			*/

        	/*
        	System.out.println(AppUtil.getToday()+" : "+AppUtil.getNextDay(AppUtil.getToday()));
        	System.out.println("tomorrow :"+ new Timestamp(Long.valueOf(1*24*60*60*1000)));
			*/
        	/*
        	String areaFlt = "DOK";
        	String create_date = "2017-08-13";

        	VunitInfoFilter filter = new VunitInfoFilter(areaFlt, create_date);
        	List<Vunitinfo> unitInfoList = new ArticleController().getVunitinfoListByArticleId(filter);

        	for(Vunitinfo unitInfo : unitInfoList){
        		System.out.println("unitInfo : "+unitInfo.getUnitCode()+" | "+unitInfo.getBinCode()+" | "+unitInfo.getCreateDate()+" | "+unitInfo.getCreateDate());
        	}
        	 */
        	/*
        	List<Vunitinfo> unitinfoList = new ArticleController().getVunitinfoListByArticleId(251379L, "", "", "", "", "", "", "", "", "");

        	for(Vunitinfo uinfo : unitinfoList){
        		System.out.println(uinfo.getAdviceCode()+" : "+uinfo.getAdvicePosId()+" : "+uinfo.getNeedCheck()+" : "+uinfo.getUnitCode());
        	}
			*/

        	/*
        	List<WmStatus> statusList = new StatusManager().getStatusList();

        	for(WmStatus status : statusList){
        		System.out.println(status.getStatusId() + " : "+status.getDescription());
        	}
			*/
        	/*
        	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	Date currDateWoTime = sdf.parse(sdf.format(new Date()));

        	System.out.println("::::: "+ sdf.format(currDateWoTime));
			*/
/*
        	System.out.println("--> "+AppUtil.getToday());


        	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        	Date date = format.parse("2017-01-01");
        	System.out.println(new Timestamp(date.getTime()));
*/
        	//System.out.println(new Timestamp(currDateWoTime.getTime()));
        	/*
        	List<Vadvice> vAdviceList = new VAdviceManager().getAdviceList(true, new Timestamp(date.getTime()), "");

        	for(Vadvice vAdv : vAdviceList){
        		System.out.println(vAdv.getAdviceCode()+" "+vAdv.getStatus()+" "+vAdv.getErrorComment());
        	}

        	System.out.println(vAdviceList.size());
 			*/
        	/*
        	Map<String, String> map = new LinkedHashMap();

        	for(int i=0; i<5; i++){
        		map.put(String.valueOf(i), String.valueOf((i+100)));
        	}

        	System.out.println(map.get("3"));
			*/
/*
        	 List<WmUser> userList = new AuthorizationController().getUserForAdvice();
				for (WmUser user : userList) {
					System.out.println(user.getUserId()+" : "+user.getSurname()+" : "+user.getFirstName().charAt(0));
				}
*/
/*
        	List<WmBin> binList = new BinController().getDokList();

        	for(WmBin bin : binList){
        		System.out.println(bin.getBinId()+" : "+bin.getBinCode());
        	}
*/

        	//Class<WmAdvice> cl = WmAdvice.class;

        	//Criteria crt = session.createCriteria(cl.getName());
        	/*
        	System.out.println("--> "+cl.getName());


        	List<WmAdvice> adviceList = new AdviceManager().getAdviceListByClientDocFilter("CLIENT");
        	for(WmAdvice advice : adviceList){
        		System.out.println(advice.getClientDocCode());
        	}

        	System.out.println("END.....");
        	*/

        	/*
            ArticleController ctrl = new ArticleController();
        	String str = ctrl.getLastBC("183864");

        	System.out.println("bc : "+str);
        	*/
        	/*
        	String bctext = "Курбонов1992";

        	System.out.println("bctext hash : "+bctext.hashCode()+" ::: "+"ИгамкуловНумос".hashCode());
        	//System.out.println("bctext2 hash : "+bctext2.hashCode());

        	for(int i=0; i<bctext.length(); i++){
            	System.out.println(i+". "+bctext.charAt(i)+" : "+(int)bctext.charAt(i));
        	}
        	*/
        	/*
            WmEquipment equipment = new EquipmentManager().getEquipmentByIp("192.168.22.97");

            System.out.println("equipment : "+equipment.getEquipmentId()+" : "+equipment.getIpAddressCode());
        	*/
        	/*
        	String bc = new BarcodeManager().getLastBarcodeArticle(256371L);

        	System.out.println("barcode : "+bc);

        	System.out.println("ctrl bc : "+ new ArticleController().getLastBC("7#67374+00"));
        	*/

        	/*
        	QuantController ctrl = new QuantController();
        	ctrl.delQuantByUnitCode(1, "EU00007214");

        	new UnitController().delUnitFromBin("EU00007214", 1);
        	*/

        	/*
        	String str1 = "123"; // -962678352
        	String str2 = "123";

            int hCode = str1.hashCode();

        	System.out.println("str1 to h : "+str1.hashCode()+"\nstr2 to h : "+str2.hashCode()+"\n equals str2==hCode : "+(str2.hashCode() == hCode));
        	*/

        	//BarcodeController ctrl = new BarcodeController();
        	        	   //"4814917077293"
        	//String barcode = "481398511224";

        	//System.out.println(barcode+String.valueOf(EANcontrolNum.getControlNum(barcode)));
        	/*
        	BarcodeManager bcMgr = new BarcodeManager();
        	List<WmBarcode> barcodeList = bcMgr.getBarcodeListByFilter("268");
        	Iterator iterator = barcodeList.iterator();
        	for(WmBarcode bc: barcodeList){
        		System.out.println(bc.getBarCode()+" : "+bc.getBarCode()+String.valueOf(EANcontrolNum.getControlNum(bc.getBarCode())));
        		bc.setBarCode(bc.getBarCode()+String.valueOf(EANcontrolNum.getControlNum(bc.getBarCode())));
        		bcMgr.deleteBarCode(bc);
        		bcMgr.createBarcode(bc);
        	}
            */

        	/*
        	WmSku sku = new SkuManager().getBaseSkyByArticle(268L);
        	System.out.println("SKU : "+sku.getSkuId()+" description : "+sku.getDescription());
        	*/
        	/*
        	List<WmArticle> articleList = new ArticleController().getArticleListByArtCodeFilter("9005");
        	Iterator<WmArticle> iterator = articleList.iterator();

        	while(iterator.hasNext()){

        		WmArticle article = (WmArticle) iterator.next();
        		System.out.println(article.getArticleId()+" : "+article.getArticleCode()+" : "+article.getDescription()+" : "+article.getArticleKitId());
        	}
        	*/
        	/*
        	BarcodeType bcType = BarcodeType.USER;
        	System.out.println(bcType.typeName());
        	*/

        	/*
        	UnitManager mgr = new UnitManager();
        	System.out.println("unit id : "+mgr.getUnitBinArticle(16774L, 2L));
        	*/

        	/*
        	BarcodeType bcType = BarcodeType.SKU;

        	System.out.println("barcode type : "+bcType);
        	*/
        	/*
        	ArrayList<String> args1 = new ArrayList<String>();
        	args1.add("EU00000024");
        	args1.add("IN00000088");
        	System.out.println(ErrorMessage.BC_NOT_UNIT.message(args1));
        	*/
        	//System.out.println(new BinController().getFreeSpaceBin(new BinController().getBin("FRN.001.016.02").getBinId()));

        	//new QuantManager().delQuantByUnitId(1L);
        	/*
        	//List<WmQualityState> states = new QualityStateManager().getListQualityState();
        	List<WmQualityState> states = new QualityStateController().getModulesList();

        	Iterator itr = states.iterator();

        	while(itr.hasNext()){

        		WmQualityState state = (WmQualityState) itr.next();
        		System.out.println(state.getDescription()+" : "+state.getQualityStateId());
        	}



        	BinManager mgr = new BinManager();
        	System.out.println("max Level for section: "+mgr.getMaxSectionLevel(350L));

        	ArticleController artCtrl = new ArticleController();
        	WmBarcode bc = artCtrl.getBarCode("1234567890123");

        	System.out.println("Bar Code: "+bc.getBarCode()+" article: "+bc.getArticleId());
        	*/
        	/*
        	List users = new ArrayList();
			users =  (List) session.createQuery("from WmUser where userBarcode = :barcode").setParameter("barcode", "999999").list();
			WmUser wmUser = (WmUser)users.iterator().next();

        	System.out.println("User is : "+wmUser.getFirstName());
        	*/
        	/*
        	UnitController ctrl = new UnitController();

        	String barCode = "EH00000001";
        	System.out.println("It's a unit : "+ctrl.isUnitBarCode(barCode));

        	List modules = new ArrayList();
        	modules =  (List) session.getNamedQuery("getUserModules").setInteger("userId", 1).list();
			Iterator iterator = modules.iterator();
			while(iterator.hasNext()){
        	  System.out.println("Module : "+ iterator.next());
			}
			*/


        } finally {
        	System.out.println("END :: "+(Main.class).getSimpleName());
        	HibernateUtil.closeAll();
           // session.close();
           //ourSessionFactory.close();

        }
    }
}
