package ru.defo.filters;

public class VorderPosFilter {
	private Long orderId;
	private String posflt;
	private String articleidflt;
	private String artdescrflt;
	private String skuflt;
	private String expqtyflt;
	private String factqtyflt;
	private String ctrlqtyflt;
	private String statusflt;
	private String vendoridflt;

	public VorderPosFilter(){
		super();
	}

	public VorderPosFilter(Long orderId, String posflt, String articleidflt, String artdescrflt, String skuflt,
			String expqtyflt, String factqtyflt, String ctrlqtyflt, String statusflt, String vendoridflt) {
		super();
		this.orderId = orderId;
		this.posflt = posflt;
		this.articleidflt = articleidflt;
		this.artdescrflt = artdescrflt;
		this.skuflt = skuflt;
		this.expqtyflt = expqtyflt;
		this.factqtyflt = factqtyflt;
		this.ctrlqtyflt = ctrlqtyflt;
		this.statusflt = statusflt;
		this.vendoridflt = vendoridflt;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getPosflt() {
		return posflt;
	}

	public void setPosflt(String posflt) {
		this.posflt = posflt;
	}

	public String getArticleidflt() {
		return articleidflt;
	}

	public void setArticleidflt(String articleidflt) {
		this.articleidflt = articleidflt;
	}

	public String getArtdescrflt() {
		return artdescrflt;
	}

	public void setArtdescrflt(String artdescrflt) {
		this.artdescrflt = artdescrflt;
	}

	public String getSkuflt() {
		return skuflt;
	}

	public void setSkuflt(String skuflt) {
		this.skuflt = skuflt;
	}

	public String getExpqtyflt() {
		return expqtyflt;
	}

	public void setExpqtyflt(String expqtyflt) {
		this.expqtyflt = expqtyflt;
	}

	public String getFactqtyflt() {
		return factqtyflt;
	}

	public void setFactqtyflt(String factqtyflt) {
		this.factqtyflt = factqtyflt;
	}

	public String getCtrlqtyflt() {
		return ctrlqtyflt;
	}

	public void setCtrlqtyflt(String ctrlqtyflt) {
		this.ctrlqtyflt = ctrlqtyflt;
	}

	public String getStatusflt() {
		return statusflt;
	}

	public void setStatusflt(String statusflt) {
		this.statusflt = statusflt;
	}

	public String getVendoridflt() {
		return vendoridflt;
	}

	public void setVendoridflt(String vendoridflt) {
		this.vendoridflt = vendoridflt;
	}



}
