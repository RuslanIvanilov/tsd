package ru.defo.model.local;

import ru.defo.model.views.Varticleqty;

public class ArticleQty {

	private int factQty;
	private int hostQty;
	private int dWmsFact;
	private int dWmsHost;
	private int dFactHost;
	private Varticleqty vArticleQty;

	public ArticleQty(){
		super();
	}

	public ArticleQty(Varticleqty vArticleQty, int factQty, int hostQty){
		 this.setvArticleQty(vArticleQty);
		 this.setFactQty(factQty);
		 this.setHostQty(hostQty);

		 this.setdWmsFact(vArticleQty.getQtySum().intValue()-factQty);
		 this.setdWmsHost(vArticleQty.getQtySum().intValue()-hostQty);
		 this.setdFactHost(factQty-hostQty);
	}

	public Varticleqty getvArticleQty() {
		return vArticleQty;
	}

	public void setvArticleQty(Varticleqty vArticleQty) {
		this.vArticleQty = vArticleQty;
	}

	public int getFactQty() {
		return factQty;
	}

	public void setFactQty(int factQty) {
		this.factQty = factQty;
	}

	public int getHostQty() {
		return hostQty;
	}

	public void setHostQty(int hostQty) {
		this.hostQty = hostQty;
	}

	public int getdWmsFact() {
		return dWmsFact;
	}

	public void setdWmsFact(int dWmsFact) {
		this.dWmsFact = dWmsFact;
	}

	public int getdWmsHost() {
		return dWmsHost;
	}

	public void setdWmsHost(int dWmsHost) {
		this.dWmsHost = dWmsHost;
	}

	public int getdFactHost() {
		return dFactHost;
	}

	public void setdFactHost(int dFactHost) {
		this.dFactHost = dFactHost;
	}



}
