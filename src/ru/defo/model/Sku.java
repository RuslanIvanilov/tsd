package ru.defo.model;

import java.util.HashSet;

public class Sku {

	String masterCode;
	String masterDescription;
	String skuCode;
	String skuDescription;
	HashSet<String> barcodeSet;

	public Sku(){
		this.barcodeSet = new HashSet<String>();
	}

	public Sku(String masterCode, String masterDescription, String skuCode, String skuDescription, HashSet<String> barcodeSet){
		this.masterCode = masterCode;
		this.masterDescription = masterDescription;
		this.skuCode = skuCode;
		this.skuDescription = skuDescription;
		this.barcodeSet = barcodeSet;
	}

	public String getMasterCode() {
		return masterCode;
	}
	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}
	public String getMasterDescription() {
		return masterDescription;
	}
	public void setMasterDescription(String masterDescription) {
		this.masterDescription = masterDescription;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSkuDescription() {
		return skuDescription;
	}
	public void setSkuDescription(String skuDescription) {
		this.skuDescription = skuDescription;
	}

	public HashSet<String> getBarcodeSet() {
		return barcodeSet;
	}
	public void setBarcodeSet(HashSet<String> barcodeSet) {
		this.barcodeSet = barcodeSet;
	}

    public void addBarcodeToSet(String barcode){
    	this.barcodeSet.add(barcode);
    }


}
