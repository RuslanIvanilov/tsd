package ru.defo.model.local.advice;

import java.util.ArrayList;
import java.util.List;

public class AdviceInvoice {
	private String docGUID;
	private String docNumber;
	private String docDate;
	private Client client;
	private List<Nomenclature> nomenclatureList;
	private int errorId;

	public AdviceInvoice(){
		super();
		client = new Client();
		nomenclatureList = new ArrayList<Nomenclature>();
	}

	@Override
	public String toString()
	{
		return this.getClass().getSimpleName()+" ERROR: ["+getErrorId()+"]"+" GUID: ["+getDocGUID()+"] Number: ["+getDocNumber()+"] Date: ["+getDocDate()+"] Client: ["+client.toString()+"] nomenclatureList.count: ["+getNomenclatureList().size()+"]";
	}

	public String getDocGUID() {
		return docGUID;
	}

	public void setDocGUID(String docGUID) {
		this.docGUID = docGUID;
	}

	public String getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public String getDocDate() {
		return docDate;
	}

	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}

	public Client getClient() { return client; }
	public void setClient(Client client) { this.client = client; }

	public List<Nomenclature> getNomenclatureList() {
		return nomenclatureList;
	}

	public void setNomenclatureList(List<Nomenclature> nomenclatureList) {
		this.nomenclatureList = nomenclatureList;
	}

	public int getErrorId() {
		return errorId;
	}

	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}



}
