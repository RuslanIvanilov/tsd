package ru.defo.model.views;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 14.03.2018.
 */
@Entity
@Table(name = "vemptybin", schema = "public", catalog = "wms")
public class Vemptybin {
    private Long binId;
    private String areaCode;
    private Long rackNo;
    private Long levelNo;
    private String binCode;

    @Id
    @Column(name = "bin_id", nullable = true)
    public Long getBinId() {
        return binId;
    }

    public void setBinId(Long binId) {
        this.binId = binId;
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
    @Column(name = "bin_code", nullable = true, length = 150)
    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vemptybin vemptybin = (Vemptybin) o;

        if (binId != null ? !binId.equals(vemptybin.binId) : vemptybin.binId != null) return false;
        if (areaCode != null ? !areaCode.equals(vemptybin.areaCode) : vemptybin.areaCode != null) return false;
        if (rackNo != null ? !rackNo.equals(vemptybin.rackNo) : vemptybin.rackNo != null) return false;
        if (levelNo != null ? !levelNo.equals(vemptybin.levelNo) : vemptybin.levelNo != null) return false;
        if (binCode != null ? !binCode.equals(vemptybin.binCode) : vemptybin.binCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = binId != null ? binId.hashCode() : 0;
        result = 31 * result + (areaCode != null ? areaCode.hashCode() : 0);
        result = 31 * result + (rackNo != null ? rackNo.hashCode() : 0);
        result = 31 * result + (levelNo != null ? levelNo.hashCode() : 0);
        result = 31 * result + (binCode != null ? binCode.hashCode() : 0);
        return result;
    }
}
