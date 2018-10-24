package ru.defo.model.local;

public class HostSku {

	String articleCode;
	String description;
	double hostQuantity;

	public HostSku(){
		this.articleCode = "";
		this.description = "";
		this.hostQuantity = 0;
	}

	public String getArticleCode() {
		return articleCode;
	}
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getHostQuantity() {
		return hostQuantity;
	}
	public void setHostQuantity(double hostQuantity) {
		this.hostQuantity = hostQuantity;
	}



}
