package ru.defo.filters;

import ru.defo.util.DefaultValue;

public class VunitInfoFilter {
	private String articleId;
	private String unitFlt;
	private String binFlt;
	private String areaFlt;
	private String rackFlt;
	private String levelFlt;
	private String qtyFlt;
	private String skuFlt;
	private String qStateFlt;
	private String needChkFlt;
	private String createDate;
	private String surnameFlt;
	private String fstnameFlt;
	private String adviceFlt;
	private String advicePosFlt;
	private String expectedDate;
	private String vendorGroupName;
	private String rowCount;
	private String articleflt;
	private String artnameflt;

	public void init(){
		articleId = "";
		unitFlt = "";
		binFlt = "";
		areaFlt = "";
		rackFlt = "";
		levelFlt = "";
		qtyFlt = "";
		skuFlt = "";
		qStateFlt = "";
		needChkFlt = "";
		createDate = "";
		surnameFlt = "";
		fstnameFlt = "";
		adviceFlt = "";
		advicePosFlt = "";
		expectedDate = "";
		vendorGroupName = "";
		articleflt = "";
		artnameflt = "";
		rowCount = String.valueOf(DefaultValue.TABLE_ROW_COUNT);
	}

	public VunitInfoFilter(){
		init();
	}


	public VunitInfoFilter(String areaFlt, String createDate){
		init();
		setAreaFlt(areaFlt);
		setCreateDate(createDate);
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getUnitFlt() {
		return unitFlt;
	}

	public void setUnitFlt(String unitFlt) {
		this.unitFlt = unitFlt;
	}

	public String getBinFlt() {
		return binFlt;
	}

	public void setBinFlt(String binFlt) {
		this.binFlt = binFlt;
	}

	public String getAreaFlt() {
		return areaFlt;
	}

	public void setAreaFlt(String areaFlt) {
		this.areaFlt = areaFlt;
	}

	public String getRackFlt() {
		return rackFlt;
	}

	public void setRackFlt(String rackFlt) {
		this.rackFlt = rackFlt;
	}

	public String getLevelFlt() {
		return levelFlt;
	}

	public void setLevelFlt(String levelFlt) {
		this.levelFlt = levelFlt;
	}

	public String getQtyFlt() {
		return qtyFlt;
	}

	public void setQtyFlt(String qtyFlt) {
		this.qtyFlt = qtyFlt;
	}

	public String getSkuFlt() {
		return skuFlt;
	}

	public void setSkuFlt(String skuFlt) {
		this.skuFlt = skuFlt;
	}

	public String getqStateFlt() {
		return qStateFlt;
	}

	public void setqStateFlt(String qStateFlt) {
		this.qStateFlt = qStateFlt;
	}

	public String getNeedChkFlt() {
		return needChkFlt;
	}

	public void setNeedChkFlt(String needChkFlt) {
		this.needChkFlt = needChkFlt;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSurnameFlt() {
		return surnameFlt;
	}

	public void setSurnameFlt(String surnameFlt) {
		this.surnameFlt = surnameFlt;
	}

	public String getFstnameFlt() {
		return fstnameFlt;
	}

	public void setFstnameFlt(String fstnameFlt) {
		this.fstnameFlt = fstnameFlt;
	}

	public String getAdviceFlt() {
		return adviceFlt;
	}

	public void setAdviceFlt(String adviceFlt) {
		this.adviceFlt = adviceFlt;
	}

	public String getAdvicePosFlt() {
		return advicePosFlt;
	}

	public void setAdvicePosFlt(String advicePosFlt) {
		this.advicePosFlt = advicePosFlt;
	}

	public String getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}

	public String getVendorGroupName() {
		return vendorGroupName;
	}

	public void setVendorGroupName(String vendorGroupName) {
		this.vendorGroupName = vendorGroupName;
	}

	public String getRowCount() {
		return rowCount;
	}

	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	public String getArticleflt() {
		return articleflt;
	}

	public void setArticleflt(String articleflt) {
		this.articleflt = articleflt;
	}

	public String getArtnameflt() {
		return artnameflt;
	}

	public void setArtnameflt(String artnameflt) {
		this.artnameflt = artnameflt;
	}



}
