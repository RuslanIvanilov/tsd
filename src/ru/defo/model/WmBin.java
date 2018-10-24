package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by syn-wms on 21.03.2017.
 */
@Entity
@Table(name = "wm_bin", schema = "public", catalog = "wms")
public class WmBin implements Comparable<WmBin>{
    private Long binId;
    private Long locationId;
    private String areaCode;
    private Long rackNo;
    private Long levelNo;
    private boolean blockIn;
    private boolean blockOut;
    private String binCode;
    private Long depthCount;
    private String abcCode;
    private Long heigh;
    private Long width;
    private Long depth;
    private boolean needCheck;
    private Long binTypeId;
    private Long sectionId;
    private Long binStatusId;
    private Long reservForUnitId;
    private Timestamp reservDate;
    private Long vendorGroupId;
    private Long binRackNo;
    private Long mirrorBinId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bin_id")
    @SequenceGenerator(name = "seq_bin_id", sequenceName = "seq_bin_id", allocationSize=1)
    @Column(name = "bin_id", nullable = false)
    public Long getBinId() {
        return binId;
    }

    public void setBinId(Long binId) {
        this.binId = binId;
    }

    @Basic
    @Column(name = "location_id", nullable = true)
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Basic
    @Column(name = "area_code", nullable = true, length = 50)
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Basic
    @Column(name = "rack_no", nullable = true)
    public Long getRackNo() {
        return rackNo;
    }

    public void setRackNo(Long rackNo) {
        this.rackNo = rackNo;
    }

    @Basic
    @Column(name = "level_no", nullable = true)
    public Long getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(Long levelNo) {
        this.levelNo = levelNo;
    }

    @Basic
    @Column(name = "block_in", nullable = true)
    public boolean getBlockIn() {
        return blockIn;
    }

    public void setBlockIn(boolean blockIn) {
        this.blockIn = blockIn;
    }

    @Basic
    @Column(name = "block_out", nullable = true)
    public boolean getBlockOut() {
        return blockOut;
    }

    public void setBlockOut(boolean blockOut) {
        this.blockOut = blockOut;
    }

    @Basic
    @Column(name = "bin_code", nullable = true, length = 150)
    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    @Basic
    @Column(name = "depth_count", nullable = true)
    public Long getDepthCount() {
        return depthCount;
    }

    public void setDepthCount(Long depthCount) {
        this.depthCount = depthCount;
    }

    @Basic
    @Column(name = "abc_code", nullable = true, length = -1)
    public String getAbcCode() {
        return abcCode;
    }

    public void setAbcCode(String abcCode) {
        this.abcCode = abcCode;
    }

    @Basic
    @Column(name = "heigh", nullable = true)
    public Long getHeigh() {
        return heigh;
    }

    public void setHeigh(Long heigh) {
        this.heigh = heigh;
    }

    @Basic
    @Column(name = "width", nullable = true)
    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    @Basic
    @Column(name = "depth", nullable = true)
    public Long getDepth() {
        return depth;
    }

    public void setDepth(Long depth) {
        this.depth = depth;
    }

    @Basic
    @Column(name = "need_check", nullable = true)
    public boolean getNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(boolean needCheck) {
        this.needCheck = needCheck;
    }

    @Basic
    @Column(name = "bin_type_id", nullable = true)
    public Long getBinTypeId() {
        return binTypeId;
    }

    public void setBinTypeId(Long binTypeId) {
        this.binTypeId = binTypeId;
    }

    @Basic
    @Column(name = "section_id", nullable = true)
    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    @Basic
    @Column(name = "bin_status_id", nullable = true)
    public Long getBinStatusId() {
        return binStatusId;
    }

    public void setBinStatusId(Long binStatusId) {
        this.binStatusId = binStatusId;
    }

    @Basic
    @Column(name = "reserv_for_unit_id", nullable = true)
    public Long getReservForUnitId() {
        return reservForUnitId;
    }

    public void setReservForUnitId(Long reservForUnitId) {
        this.reservForUnitId = reservForUnitId;
    }

    @Basic
    @Column(name = "reserv_date", nullable = true)
    public Timestamp getReservDate() {
        return reservDate;
    }

    public void setReservDate(Timestamp reservDate) {
        this.reservDate = reservDate;
    }

    /* Usable for receive in pallets */
    @Transient
    public boolean isAdviceBin(){
    	switch(this.binTypeId.intValue()){
    		case 3: return true;
    		case 7: return true;
    	   default: return false;
    	}

    }

    @Basic
    @Column(name = "vendor_group_id", nullable = true)
    public Long getVendorGroupId() {
        return vendorGroupId;
    }

    public void setVendorGroupId(Long vendorGroupId) {
        this.vendorGroupId = vendorGroupId;
    }

    @Basic
    @Column(name = "bin_rack_no", nullable = true)
    public Long getBinRackNo() {
        return binRackNo;
    }

    public void setBinRackNo(Long binRackNo) {
        this.binRackNo = binRackNo;
    }

    @Basic
    @Column(name = "mirror_bin_id", nullable = true)
    public Long getMirrorBinId() {
        return mirrorBinId;
    }

    public void setMirrorBinId(Long mirrorBinId) {
        this.mirrorBinId = mirrorBinId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmBin wmBin = (WmBin) o;

        if (binId != null ? !binId.equals(wmBin.binId) : wmBin.binId != null) return false;
        if (locationId != null ? !locationId.equals(wmBin.locationId) : wmBin.locationId != null) return false;
        if (areaCode != null ? !areaCode.equals(wmBin.areaCode) : wmBin.areaCode != null) return false;
        if (rackNo != null ? !rackNo.equals(wmBin.rackNo) : wmBin.rackNo != null) return false;
        if (levelNo != null ? !levelNo.equals(wmBin.levelNo) : wmBin.levelNo != null) return false;
        if (blockIn != wmBin.blockIn) return false;
        if (blockOut != wmBin.blockOut) return false;
        if (binCode != null ? !binCode.equals(wmBin.binCode) : wmBin.binCode != null) return false;
        if (depthCount != null ? !depthCount.equals(wmBin.depthCount) : wmBin.depthCount != null) return false;
        if (abcCode != null ? !abcCode.equals(wmBin.abcCode) : wmBin.abcCode != null) return false;
        if (heigh != null ? !heigh.equals(wmBin.heigh) : wmBin.heigh != null) return false;
        if (width != null ? !width.equals(wmBin.width) : wmBin.width != null) return false;
        if (depth != null ? !depth.equals(wmBin.depth) : wmBin.depth != null) return false;
        if (needCheck != wmBin.needCheck) return false;
        if (binTypeId != null ? !binTypeId.equals(wmBin.binTypeId) : wmBin.binTypeId != null) return false;
        if (sectionId != null ? !sectionId.equals(wmBin.sectionId) : wmBin.sectionId != null) return false;
        if (binStatusId != null ? !binStatusId.equals(wmBin.binStatusId) : wmBin.binStatusId != null) return false;
        if (reservForUnitId != null ? !reservForUnitId.equals(wmBin.reservForUnitId) : wmBin.reservForUnitId != null)
            return false;
        if (reservDate != null ? !reservDate.equals(wmBin.reservDate) : wmBin.reservDate != null) return false;
        if (vendorGroupId != null ? !vendorGroupId.equals(wmBin.vendorGroupId) : wmBin.vendorGroupId != null) return false;
        if (binRackNo != null ? !binRackNo.equals(wmBin.binRackNo) : wmBin.binRackNo != null) return false;
        if (mirrorBinId != null ? !mirrorBinId.equals(wmBin.mirrorBinId) : wmBin.mirrorBinId != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = binId != null ? binId.hashCode() : 0;
        result = 31 * result + (locationId != null ? locationId.hashCode() : 0);
        result = 31 * result + (areaCode != null ? areaCode.hashCode() : 0);
        result = 31 * result + (rackNo != null ? rackNo.hashCode() : 0);
        result = 31 * result + (levelNo != null ? levelNo.hashCode() : 0);
        result = 31 * result + (blockIn != false ? 1 : 0);
        result = 31 * result + (blockOut != false ? 1 : 0);
        result = 31 * result + (binCode != null ? binCode.hashCode() : 0);
        result = 31 * result + (depthCount != null ? depthCount.hashCode() : 0);
        result = 31 * result + (abcCode != null ? abcCode.hashCode() : 0);
        result = 31 * result + (heigh != null ? heigh.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (depth != null ? depth.hashCode() : 0);
        result = 31 * result + (needCheck != false ? 1 : 0);
        result = 31 * result + (binTypeId != null ? binTypeId.hashCode() : 0);
        result = 31 * result + (sectionId != null ? sectionId.hashCode() : 0);
        result = 31 * result + (binStatusId != null ? binStatusId.hashCode() : 0);
        result = 31 * result + (reservForUnitId != null ? reservForUnitId.hashCode() : 0);
        result = 31 * result + (reservDate != null ? reservDate.hashCode() : 0);
        result = 31 * result + (vendorGroupId != null ? vendorGroupId.hashCode() : 0);
        result = 31 * result + (binRackNo != null ? binRackNo.hashCode() : 0);
        result = 31 * result + (mirrorBinId != null ? mirrorBinId.hashCode() : 0);
        return result;
    }

	@Override
	public int compareTo(WmBin p) {
		return (int) ((this.getRackNo() - p.getRackNo())+(this.getLevelNo()-p.getLevelNo()));
	}
}
