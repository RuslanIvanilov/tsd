package ru.defo.filters;

public class VemptyBinFilter {
	private Long binId;
    private String areaCode;
    private String rackNo;
    private String levelNo;
    private String binCode;
    private int rowCount;

    public VemptyBinFilter(){
    	super();
    	this.rowCount = 0;
    }

	public VemptyBinFilter(Long binId, String areaCode, String rackNo, String levelNo, String binCode) {
		super();
		this.binId = binId;
		this.areaCode = areaCode;
		this.rackNo = rackNo;
		this.levelNo = levelNo;
		this.binCode = binCode;
	}

	public VemptyBinFilter(String areaCode, String rackNo, String levelNo, String binCode) {
		super();
		this.areaCode = areaCode;
		this.rackNo = rackNo;
		this.levelNo = levelNo;
		this.binCode = binCode;
	}

	public Long getBinId() {
		return binId;
	}

	public void setBinId(Long binId) {
		this.binId = binId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getRackNo() {
		return rackNo;
	}

	public void setRackNo(String rackNo) {
		this.rackNo = rackNo;
	}

	public String getLevelNo() {
		return levelNo;
	}

	public void setLevelNo(String levelNo) {
		this.levelNo = levelNo;
	}

	public String getBinCode() {
		return binCode;
	}

	public void setBinCode(String binCode) {
		this.binCode = binCode;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}




}
