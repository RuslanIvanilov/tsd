package ru.defo.model.local.advice;

public class Element {
	private String code;
	private String description;
	private int quantity;

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" code: [" + getCode() + "] description: [" + getDescription() + "] quantity: [" + getQuantity()
				+ "]";
	}

}
