package ru.defo.model.local.advice;

import java.util.ArrayList;
import java.util.List;

public class Nomenclature {
	private String code;
	private String description;
	private List<Element> elementList;
	private int quantity;

	public Nomenclature(){
		super();
		elementList = new ArrayList<Element>();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Element> getElementList() {
		return elementList;
	}

	public void setElementList(List<Element> elementList) {
		this.elementList = elementList;
	}



	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString(){
		return this.getClass().getSimpleName()+" code: ["+getCode()+"] description: ["+getDescription()+"] element.count: ["+getElementList().size()+"] qty: ["+getQuantity()+"]";
	}


}
