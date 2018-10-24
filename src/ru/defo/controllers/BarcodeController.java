package ru.defo.controllers;

import ru.defo.managers.BarcodeManager;
import ru.defo.managers.BinManager;
import ru.defo.managers.UnitManager;
import ru.defo.managers.UserManager;
import ru.defo.model.WmBarcode;
import ru.defo.model.WmBin;
import ru.defo.model.WmUnit;
import ru.defo.model.WmUser;
import ru.defo.util.BarcodeType;
import ru.defo.util.DefaultValue;

public class BarcodeController {

	BarcodeManager ArtBcMgr = new BarcodeManager();

	public BarcodeType getBarcodeType(String barCode){

		/* Ячейка */
		if(isBinBarcode(barCode)){ return BarcodeType.BIN; }

		/* Артикул */
		BarcodeManager bcMgr = new BarcodeManager();
		WmBarcode bc = bcMgr.gerBarCode(barCode);
		if(bc != null) {
			//bcMgr.closeSession();
			return BarcodeType.SKU;
			}

		/* Поддон */
		WmUnit unit = null;
		UnitManager unitMgr = new UnitManager();
		try{
		  unit = unitMgr.getUnitByCode(barCode);
		} catch(Exception e){
			e.printStackTrace();
		}

		if(unit instanceof WmUnit || (barCode.length() == DefaultValue.UNIT_NUMBER_SIZE && (unitMgr.getUnitType(barCode) != null))){
			//unitMgr.closeSession();
			return BarcodeType.UNIT;
			}

		/* Сотрудник */
		UserManager userMgr = new UserManager();
		WmUser user = userMgr.getBarCodeUser(barCode);
		if(user != null){
			return BarcodeType.USER;
		}



		return BarcodeType.UNKNOWN;
	}

	public BarcodeType getBarcodeType(Object barCode){
		return getBarcodeType(String.valueOf(barCode));
	}

	public boolean isBinBarcode(String barCode){
		BinManager binMgr = new BinManager();
		WmBin bin = binMgr.getBinByBarcode(barCode);
		//binMgr.closeSession();
		return (bin instanceof WmBin);
	}

	public boolean isBinBarcode(Object barCode){
		return isBinBarcode(String.valueOf(barCode));
	}

	public void addBarcode(String barcode, String articleId, String skuId, String userId){
		BarcodeManager bcMgr = new BarcodeManager();

		if(new ArticleController().getBarCode(barcode) == null)
		{
			WmBarcode bc = new WmBarcode();
			bc.setBarCode(barcode);
			bc.setArticleId(Long.valueOf(articleId));
			bc.setSkuId(Long.valueOf(skuId));
			bc.setBlocked(false);
			bcMgr.createBarcode(bc);
			//bcMgr.closeSession();

			String comment = DefaultValue.ASSIGN_BARCODE_SKU+" ["+barcode+"]";

			new HistoryController().addAssignEntry(Long.valueOf(userId), Long.valueOf(articleId), Long.valueOf(skuId), comment);
		}
	}

	public void delBarcode(String barcode, String userId)
	{
		BarcodeManager bcMgr = new BarcodeManager();
		WmBarcode bc = bcMgr.gerBarCode(barcode);
		bcMgr.deleteBarCode(bc);

		String comment = DefaultValue.ASSIGN_DEL_BARCODE+" ["+barcode+"]";
		new HistoryController().addAssignEntry(Long.valueOf(userId), bc.getArticleId(), bc.getSkuId(), comment);
	}

}
