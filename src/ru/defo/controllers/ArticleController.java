package ru.defo.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import ru.defo.filters.VunitInfoFilter;
import ru.defo.managers.ArticleManager;
import ru.defo.managers.BarcodeManager;
import ru.defo.managers.BinManager;
import ru.defo.managers.QualityStateManager;
import ru.defo.managers.QuantManager;
import ru.defo.managers.SkuManager;
import ru.defo.managers.UnitManager;
import ru.defo.managers.VArticleQtyManager;
import ru.defo.managers.VUnitInfoManager;
import ru.defo.model.Sku;
import ru.defo.model.WmArticle;
import ru.defo.model.WmArticleType;
import ru.defo.model.WmBarcode;
import ru.defo.model.WmBin;
import ru.defo.model.WmQualityState;
import ru.defo.model.WmQuant;
import ru.defo.model.WmSku;
import ru.defo.model.WmUnit;
import ru.defo.model.WmUser;
import ru.defo.model.WmVendorGroup;
import ru.defo.model.local.ArticleQty;
import ru.defo.model.views.Varticleqty;
import ru.defo.model.views.Varticleqtyr;
import ru.defo.model.views.Vunitinfo;
import ru.defo.util.AppUtil;
import ru.defo.util.DefaultValue;
import ru.defo.util.EANcontrolNum;
import ru.defo.util.ErrorMessage;
import ru.defo.util.HibernateUtil;

public class ArticleController {

	BarcodeManager bcMgr;

	public ArticleController() {
		bcMgr = new BarcodeManager();
	}

	public WmArticleType getArticleTypeByArticle(WmArticle article)
	{
		return new ArticleManager().getArticlrTypeByArticle(article);
	}

	public void update(HttpSession session){
		boolean blocked = false;
		String description;
		WmArticle article  = getArticle(session.getAttribute("articleid"));
		WmUser user = new UserController().getUserById(session.getAttribute("userid"));
		if(user==null) return;

		if(article != null){
			blocked = session.getAttribute("blockedtxt").toString().equals("true");
			description = session.getAttribute("descriptiontxt").toString();

			article.setBlocked(blocked);
			article.setDescription(description);

			Session hSession = HibernateUtil.getSession();
			try{
				hSession.getTransaction().begin();
				hSession.update(article);
				new HistoryController().addArticleChangeEntry(hSession, article, user);
				hSession.getTransaction().commit();
			} catch(Exception e){
				e.printStackTrace();
				hSession.getTransaction().rollback();
				hSession.close();
			}
		}



	}

	public WmVendorGroup getVendorGroupByArticle(WmArticle article)
	{
		List<WmUnit> unitList = new UnitManager().getUnitListByBinArticle(null, article);
		Map<Long, Long> groupMap = new HashMap<Long, Long>();
		Map<Long, Long> sortedGroupMap = new HashMap<Long, Long>();
		WmVendorGroup resultGroup = null;

		for(WmUnit unit : unitList){
			if(unit.getBinId()!= null){
				WmBin bin = new BinManager().getBinById(unit.getBinId());
				//System.out.println(unit.getUnitCode()+ " : "+bin.getBinCode()+" : "+bin.getVendorGroupId());
				if(bin.getVendorGroupId()!=null){
					if(groupMap.get(bin.getVendorGroupId())!= null)
					{
						groupMap.replace(bin.getVendorGroupId(), groupMap.get(bin.getVendorGroupId()).longValue()+1);
					} else {
						groupMap.put(bin.getVendorGroupId(), 1L);
					}
				}
			}
		}

		if(groupMap.size()>0)
			sortedGroupMap = groupMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue)->oldValue, LinkedHashMap::new));

		for(Map.Entry<Long, Long> entry : sortedGroupMap.entrySet()){
			if(resultGroup != null) break;
			resultGroup = getVendorGroupById(entry.getKey());
		}

		return resultGroup;
	}

	public List<WmVendorGroup> getVendorGroupList(){
		return new ArticleManager().getVendorGroupList();
	}

	public WmVendorGroup getVendorGroupById(Long vendorGroupId){
		return new ArticleManager().getVendorGroupById(vendorGroupId);
	}

	public boolean isBlockedBC(Object barCode){
		WmBarcode bc = getBarCode(barCode.toString());
		if(!(bc instanceof WmBarcode)) return false;
		return bc.getBlocked();
	}

	public String getErrorTextBlockedBC(Object barCode){
		if(!isBlockedBC(barCode)) return null;
		return ErrorMessage.BC_BLOCKED.message(new ArrayList<String>(Arrays.asList(barCode==null?"":barCode.toString())));
	}

	public WmBarcode getBarCode(Object barCode) {
		String barCodeTxt = String.valueOf(barCode);
		return getBarCode(barCodeTxt);
	}

	public WmBarcode getBarCode(String barCode) {
		return bcMgr.gerBarCode(ru.defo.util.Bc.symbols(barCode));
	}

	public WmArticle getArticleByBc(WmBarcode bc){
		WmArticle article = new ArticleManager().getArticleById(bc.getArticleId());
		return article;
	}

	public String getBcByArticleId(Object oArticleId){

		Long articleId =  Long.decode(oArticleId.toString());

		return bcMgr.BcByArticleId(articleId);
	}

	public String getBcValueByArticleId(Object oArticleId){
		Long articleId =  Long.decode(oArticleId.toString());
		return bcMgr.BcByArticleId(articleId).replace("#","%2523").replace("@","%2540").replace("+","%252B").replace("*","%252A");
	}

	public WmArticle getArticleByBarcode(String barcode) {
		WmBarcode bc = getBarCode(barcode);
		if (bc == null)
			return null;
		return getArticle(bc.getArticleId());
	}

	public WmArticle getArticle(Long articleId) {
		return new ArticleManager().getArticleById(articleId);
	}

	public WmArticle getArticle(HttpSession httpSession){
		return getArticle(httpSession.getAttribute("articleid"));
	}

	public WmArticle getArticle(Object articleIdObj){
		Long articleId;
		try{
			articleId = Long.valueOf(articleIdObj.toString());
		}catch(NumberFormatException nce){
			nce.printStackTrace();
			return null;
		}

		return new ArticleManager().getArticleById(articleId);
	}


	public boolean checkArticleBc(Object articleBc, String backPageJsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		WmBarcode bc = getBarCode(articleBc);
		if(!(bc instanceof WmBarcode))
		{
			request.getSession().setAttribute("message", getErrorText(articleBc.toString()));
			request.getSession().setAttribute("action", backPageJsp);
			request.getRequestDispatcher("/errorn.jsp").forward(request, response);
			return false;
		}

		String errMsgBcBlocked = new ArticleController().getErrorTextBlockedBC(articleBc);
		if(errMsgBcBlocked != null){
			request.getSession().setAttribute("message", errMsgBcBlocked);
			request.getSession().setAttribute("action", request.getContextPath()+"/"+AppUtil.getPackageName(this)+"/"+ this.getClass().getSimpleName());
			request.getRequestDispatcher("/"+"errorn.jsp").forward(request, response);
			return false;
		}

		return true;
	}


	public String getErrorText(String skuBarcode) {
		return ErrorMessage.BC_NOT_SKU.message(new ArrayList<String>(Arrays.asList(skuBarcode)));
	}

	public String getError(String skuBarCode) {
		WmBarcode bc = new ArticleController().getBarCode(skuBarCode);
		if (bc == null)
			return getErrorText(skuBarCode);

		return null;
	}

	public boolean isBarcodeArticle(String barcodeArticle, Object articleIdObj) {
		if(articleIdObj==null) return false;
		Long articleId = Long.valueOf(String.valueOf(articleIdObj));
		WmBarcode barcode = getBarCode(barcodeArticle);

		return (barcode != null) && (barcode.getArticleId().longValue() == articleId.longValue());
	}



	public String getLastBC(String articleCode) {

		WmArticle article = new ArticleManager().getArticleByCode(articleCode);

		String bc = bcMgr.getLastBarcodeArticle(article.getArticleId());

		if (bc == null) {
			bc = article.getArticleCode();
		}

		return bc;

	}

	public WmArticle getArticleByCode(String articleCode) {
		WmArticle article = null;
		ArticleManager artMgr = new ArticleManager();
		String artCode = ru.defo.util.Bc.symbols(articleCode);
		if(artCode != null) article = artMgr.getArticleByCode(artCode.trim());

		return article;
	}

	public Long createArticleSku(String articleCode, String description, String articleClientCode, Long clientId,
			Long articleTypeId, Long articleKitId, String abcCode, boolean isBaseSku, String articleBarcode) {
		Long articleId = null;
		WmArticle article = new WmArticle();
		WmArticle dbArticle = getArticleByCode(articleCode);
		String barcodeEAN;

		if (dbArticle == null) {
			ArticleManager artMgr = new ArticleManager();
			SkuManager skuMgr = new SkuManager();
			BarcodeManager bcMgr = new BarcodeManager();
			WmBarcode barcode = new WmBarcode();

			articleId = artMgr.getNextArticleId();

			article.setArticleId(articleId);
			article.setArticleCode(articleCode);
			article.setDescription(description);
			article.setArticleClientCode(articleClientCode);
			// article.setDescriptionClient("");
			article.setClientId(clientId);
			article.setBlocked(false);
			article.setAbcCode(abcCode);
			article.setArticleTypeId(articleTypeId);
			article.setLotNeed(false);
			article.setArticleKitId(articleKitId);
			artMgr.saveArticle(article);
			// artMgr.closeSession();

			Long skuId = skuMgr.getNextSkuId();
			WmSku sku = new WmSku();
			sku.setSkuId(skuId);
			sku.setDescription(DefaultValue.BASE_SKU_NAME);
			sku.setWeight(0L);
			sku.setHeigh(0L);
			sku.setWidth(0L);
			sku.setLength(0L);
			sku.setIsBase(isBaseSku);
			sku.setSkuCrushId(1L);
			sku.setArticleId(articleId);
			skuMgr.createSku(sku);
			// skuMgr.closeSession();

			if (articleBarcode != null && articleBarcode != "") {

				barcodeEAN = articleBarcode;

				System.out.println("barcodeEAN before : " + barcodeEAN);

				try {
					if (Integer.decode(articleBarcode.substring(0, 3)) == DefaultValue.EAN_SIGN_1C
							&& articleBarcode.length() == 15) {
						barcodeEAN = articleBarcode.substring(3)
								+ EANcontrolNum.getControlNum(articleBarcode.substring(3));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("barcodeEAN after : " + barcodeEAN);

				//if(bcMgr.gerBarCode(barcodeEAN.trim()) == null)
				//{
					barcode.setBarCode(barcodeEAN.trim());
					barcode.setArticleId(articleId);
					barcode.setSkuId(skuId);
					barcode.setBlocked(false);
					bcMgr.createBarcode(barcode);
					// bcMgr.closeSession();
				//}
			}

		}
		return articleId;
	}

	public Long createArticleSku(String articleCode, String description, String articleClientCode, Long clientId,
			Long articleTypeId, Long articleKitId, String abcCode, boolean isBaseSku, HashSet<String> articleBarcodeSet) {
		Long articleId = null;
		WmArticle article = new WmArticle();
		WmArticle dbArticle = getArticleByCode(articleCode);
		String barcodeEAN;

		if (dbArticle == null) {
			ArticleManager artMgr = new ArticleManager();
			SkuManager skuMgr = new SkuManager();
			BarcodeManager bcMgr = new BarcodeManager();
			WmBarcode barcode = new WmBarcode();

			articleId = artMgr.getNextArticleId();

			article.setArticleId(articleId);
			article.setArticleCode(articleCode);
			article.setDescription(description);
			article.setArticleClientCode(articleClientCode);
			// article.setDescriptionClient("");
			article.setClientId(clientId);
			article.setBlocked(false);
			article.setAbcCode(abcCode);
			article.setArticleTypeId(articleTypeId);
			article.setLotNeed(false);
			article.setArticleKitId(articleKitId);
			artMgr.saveArticle(article);
			// artMgr.closeSession();

			Long skuId = skuMgr.getNextSkuId();
			WmSku sku = new WmSku();
			sku.setSkuId(skuId);
			sku.setDescription(DefaultValue.BASE_SKU_NAME);
			sku.setWeight(0L);
			sku.setHeigh(0L);
			sku.setWidth(0L);
			sku.setLength(0L);
			sku.setIsBase(isBaseSku);
			sku.setSkuCrushId(1L);
			sku.setArticleId(articleId);
			skuMgr.createSku(sku);
			// skuMgr.closeSession();

			if (!articleBarcodeSet.isEmpty()) {

				Iterator<String> iterator = articleBarcodeSet.iterator();
				while(iterator.hasNext()){

					barcodeEAN = iterator.next();


					try {
						if (barcodeEAN.length() == 15 && Integer.decode(barcodeEAN.substring(0, 3)) == DefaultValue.EAN_SIGN_1C)
						{
							barcodeEAN = barcodeEAN.substring(3)
									+ EANcontrolNum.getControlNum(barcodeEAN.substring(3));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					    WmBarcode bc = new ArticleController().getBarCode(barcodeEAN);
					    if(!(bc instanceof WmBarcode)){
							barcode.setBarCode(barcodeEAN.trim());
							barcode.setArticleId(articleId);
							barcode.setSkuId(skuId);
							barcode.setBlocked(false);
							bcMgr.createBarcode(barcode);
					    }
				}
			}

		}
		return articleId;
	}


	public List<WmArticle> getArticleListByArtCodeFilter(String articleCodeFilter) {

		ArticleManager articleMgr = new ArticleManager();
		List<WmArticle> articleList = articleMgr.getArticleListByArtCodeFilter(articleCodeFilter);
		// articleMgr.closeSession();

		return articleList;
	}

	public List<Vunitinfo> getVunitinfoListByArticleId(VunitInfoFilter filter){

		return getVunitinfoListByArticleId(filter.getArticleId(), filter.getUnitFlt(), filter.getBinFlt(), filter.getAreaFlt(),
				filter.getRackFlt(), filter.getLevelFlt(), filter.getQtyFlt(), filter.getSkuFlt(), filter.getqStateFlt(), filter.getNeedChkFlt(),
				filter.getCreateDate(), filter.getSurnameFlt(), filter.getFstnameFlt(), filter.getAdviceFlt(), filter.getAdvicePosFlt(),
				filter.getExpectedDate(), filter.getVendorGroupName(), filter.getArticleflt(), filter.getArtnameflt(), filter.getRowCount());
	}



	public List<Vunitinfo> getVunitinfoListByArticleId(String articleId, String unitFlt, String binFlt, String areaFlt,
			String rackFlt, String levelFlt, String qtyFlt, String skuFlt, String qStateFlt, String needChkFlt,
			String createDate, String surnameFlt, String fstnameFlt, String adviceFlt, String advicePosFlt,
			String expectedDate, String groupFlt, String articleflt, String artnameflt, String rowCount) {

		int rowCnt;
		Long artId;
		Timestamp tsCreateDate = null;
		Timestamp tsExpectedDate = null;

		try {
			rowCnt = Integer.decode(rowCount);
		} catch (Exception e) {
			e.printStackTrace();
			rowCnt = 0;
		}

		if(!articleId.isEmpty()){
			artId = Long.decode(articleId);
		} else {
			artId = null;
		}

		if(!createDate.isEmpty())
			tsCreateDate = AppUtil.stringToTimestamp(createDate);

		if(!expectedDate.isEmpty())
			tsExpectedDate = AppUtil.stringToTimestamp(expectedDate);

		try {
			return new VUnitInfoManager().getVunitinfoListByArticleId(artId, unitFlt, binFlt, areaFlt,
					rackFlt, levelFlt, qtyFlt, skuFlt, qStateFlt, needChkFlt,
					tsCreateDate,
				    surnameFlt, fstnameFlt, adviceFlt, advicePosFlt,
				    tsExpectedDate, groupFlt, articleflt, artnameflt,
				    rowCnt);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private int getRowCount(String rowCountTxt) throws NumberFormatException
	{
	  return Integer.decode(rowCountTxt);
	}

	public List<Varticleqtyr> getVarticleQtyrList(HttpSession session)
	{
		int rowCnt = getRowCount(session.getAttribute("rowcount").toString());
        String articleFilter = session.getAttribute("articlefilter").toString();
        String articleDescFilter = session.getAttribute("articledescfilter").toString();
        String qtyFilter = session.getAttribute("qtyfilter").toString();
        String qtyFactFilter = session.getAttribute("qtyfctflt").toString();
        String qtyHostFilter = session.getAttribute("qtyhostflt").toString();
        String dWmsFactFilter = session.getAttribute("dwmsfctflt").toString();
        String dWmsHostFilter = session.getAttribute("dwmshostflt").toString();
        String dFactHostFilter = session.getAttribute("dfcthostflt").toString();


		return new VArticleQtyManager().getArticleQtyrListByFilter(articleFilter, articleDescFilter,
				qtyFilter, qtyFactFilter, qtyHostFilter, dWmsFactFilter, dWmsHostFilter, dFactHostFilter, rowCnt);
	}

	public List<Varticleqty> getVarticleQtyList(HttpSession session)
	{
		int rowCnt = getRowCount(session.getAttribute("rowcount").toString());
        String articleFilter = session.getAttribute("articlefilter").toString();
        String articleDescFilter = session.getAttribute("articledescfilter").toString();
        String qtyFilter = session.getAttribute("qtyfilter").toString();
        String qtyFactFilter = session.getAttribute("qtyfctflt").toString();
        String qtyHostFilter = session.getAttribute("qtyhostflt").toString();
        String dWmsFactFilter = session.getAttribute("dwmsfctflt").toString();
        String dWmsHostFilter = session.getAttribute("dwmshostflt").toString();
        String dFactHostFilter = session.getAttribute("dfcthostflt").toString();


		return new VArticleQtyManager().getArticleQtyListByFilter(articleFilter, articleDescFilter,
				qtyFilter, qtyFactFilter, qtyHostFilter, dWmsFactFilter, dWmsHostFilter, dFactHostFilter, rowCnt);
	}


	public List<Varticleqty> getVarticleQtyList(String articleFilter, String articleDescFilter, String qtyFilter,
			String rowCount) {

		int rowCnt;

		try {
			rowCnt = Integer.decode(rowCount);
		} catch (Exception e) {
			e.printStackTrace();
			rowCnt = 0;
		}
		return new VArticleQtyManager().getVarticleQtyListByFilter(articleFilter, articleDescFilter, qtyFilter, rowCnt);
	}

	public WmSku getBaseSkuByArticleId(Long articleId) {

		SkuManager skuMgr = new SkuManager();
		WmSku sku = skuMgr.getBaseSkyByArticle(articleId);
		// skuMgr.closeSession();

		return sku;
	}

	/**@deprecated
	 * */
	public void fillArticleInfoByBarcode(HttpSession session, String barcode, String unitcode) {
		ArticleController articleCtrl = new ArticleController();
		WmArticle article = articleCtrl.getArticleByBarcode(barcode);

		if (article != null) {
			WmBarcode bc = articleCtrl.getBarCode(barcode);
			WmSku sku = new SkuController().getSku(bc.getSkuId());
			session.setAttribute("barcode", barcode);
			session.setAttribute("articleid", article.getArticleId());
			session.setAttribute("articlecode", article.getArticleCode());
			session.setAttribute("articlename", article.getDescription());
			session.setAttribute("skuname", sku.getDescription());
			session.setAttribute("skuid", sku.getSkuId());
		}

		WmUnit unit = null;
		try{
			unit = new UnitManager().getUnitByCode(unitcode);
		} catch(Exception e){
			System.out.println("ERR : UnitManager().getUnitByCode !!!"+session.getAttribute("userid"));
		}

		if (unit instanceof WmUnit) {
			WmQuant quant = new QuantManager().getQuantByUnitArticle(unit.getUnitId(), article.getArticleId());
			if (article != null) {
				if (quant != null) {
					session.setAttribute("quantity", quant.getQuantId());
					WmQualityState qState = new QualityStateManager().getQualityStateById(quant.getQualityStateId());
					if (qState != null)
						session.setAttribute("qualityname", qState.getDescription());
					session.setAttribute("qualitystateid", qState.getQualityStateId());
				}
			}

		}

	}


	public void createSku(Sku sku)
	{
		ArticleManager artMgr = new ArticleManager();
		Long parentArticleId;
		boolean needUpdate = false;

		/* Create parent Article */
		WmArticle article0 = artMgr.getArticleByCode(sku.getMasterCode().trim());
		if(article0 instanceof WmArticle)
		{
			parentArticleId = article0.getArticleId();
			if(article0.getDescription().equals(sku.getMasterDescription())==false){
				article0.setDescription(sku.getMasterDescription());
				artMgr.saveArticle(article0);
			}
		} else {
			parentArticleId = createArticleSku(sku.getMasterCode().trim(), sku.getMasterDescription(), ("ws "+AppUtil.getToday()), 1L, 1L, null, "A", true, "");
		}

		/* Create Storage Structure */
		WmArticle article = artMgr.getArticleByCode(sku.getSkuCode().trim());
		if(article instanceof WmArticle){
			if(sku.getSkuDescription() instanceof String){
				if(!((article.getDescription()==null?"":article.getDescription()).equals(sku.getSkuDescription()))){
					article.setDescription(sku.getSkuDescription());
					needUpdate = true;
				}

				if((article.getArticleKitId()==null?0L:article.getArticleKitId().longValue()) != parentArticleId.longValue()){
				    article.setArticleKitId(parentArticleId);
				    needUpdate = true;
				}

				if(needUpdate){
					artMgr.saveArticle(article);
					System.out.println("CHANGE "+new Date()+" : "+article.getArticleId()+" for "+article.getArticleCode()+" | "+article.getDescription());
				}
			}
		} else {
			Long artId = createArticleSku(sku.getSkuCode().trim(), sku.getSkuDescription(), ("ws "+AppUtil.getToday()), 1L, 1L, parentArticleId, "A", true, sku.getBarcodeSet());
			System.out.println(new Date()+" : "+artId+" for "+sku.getSkuCode()+" | "+sku.getSkuDescription());
		}


	}



}
