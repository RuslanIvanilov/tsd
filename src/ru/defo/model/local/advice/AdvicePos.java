package ru.defo.model.local.advice;

import java.util.ArrayList;
import java.util.List;

public class AdvicePos {
	private int posNumber;
	private List<Nomenclature> nomenclatureList;
	private int quantity;

	public int getPosNumber() {
		return posNumber;
	}



	public void setPosNumber(int posNumber) {
		this.posNumber = posNumber;
	}



	public List<Nomenclature> getNomenclatureList() {
		return nomenclatureList;
	}



	public void setNomenclatureList(List<Nomenclature> nomenclatureList) {
		this.nomenclatureList = nomenclatureList;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public AdvicePos(){
		super();
		nomenclatureList = new ArrayList<Nomenclature>();
	}

	@Override
	public String toString(){
		return this.getClass().getSimpleName()+" pos: ["+getPosNumber()+"] quantity: ["+getQuantity()+"] nomenclatureList.count: ["+getNomenclatureList().size()+"]";
	}

}
