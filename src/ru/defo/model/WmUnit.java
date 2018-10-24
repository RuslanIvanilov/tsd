package ru.defo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.Id; 
import javax.persistence.Table;

/**
 * Created by syn-wms on 21.03.2017.
 */
@Entity
@Table(name = "wm_unit", schema = "public", catalog = "wms")
public class WmUnit {
    private Long unitId;
    private String unitCode;
    private boolean waste;
    private boolean blocked;
    private Long binId;
    private boolean needCheck;
    private String unitTypeCode;

    @Id
    @Column(name = "unit_id", nullable = false)
    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    @Basic
    @Column(name = "unit_code", nullable = true, length = 50)
    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    @Basic
    @Column(name = "waste", nullable = true)
    public boolean getWaste() {
        return waste;
    }

    public void setWaste(boolean waste) {
        this.waste = waste;
    }

    @Basic
    @Column(name = "blocked", nullable = true)
    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Basic
    @Column(name = "bin_id", nullable = true)
    public Long getBinId() {
        return binId;
    }

    public void setBinId(Long binId) {
        this.binId = binId;
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
    @Column(name = "unit_type_code", nullable = true, length = 3)
    public String getUnitTypeCode() {
        return unitTypeCode;
    }

    public void setUnitTypeCode(String unitTypeCode) {
        this.unitTypeCode = unitTypeCode;
    }

    public WmUnit initUnitByCode(String unitCode)
    {
    	WmUnit unit = new WmUnit();
    	unit.setUnitCode(unitCode);
    	unit.setUnitTypeCode(unitCode.substring(0, 2));
    	unit.setUnitId((long)Integer.valueOf(unitCode.substring(2)));
    	return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmUnit wmUnit = (WmUnit) o;

        if (unitId != null ? !unitId.equals(wmUnit.unitId) : wmUnit.unitId != null) return false;
        if (unitCode != null ? !unitCode.equals(wmUnit.unitCode) : wmUnit.unitCode != null) return false;
        if (waste != wmUnit.waste) return false;
        if (blocked != wmUnit.blocked) return false;
        if (binId != null ? !binId.equals(wmUnit.binId) : wmUnit.binId != null) return false;
        if (needCheck != wmUnit.needCheck) return false;
        if (unitTypeCode != null ? !unitTypeCode.equals(wmUnit.unitTypeCode) : wmUnit.unitTypeCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = unitId != null ? unitId.hashCode() : 0;
        result = 31 * result + (unitCode != null ? unitCode.hashCode() : 0);
        result = 31 * result + (waste != false ? 1 : 0);
        result = 31 * result + (blocked != false ? 1 : 0);
        result = 31 * result + (binId != null ? binId.hashCode() : 0);
        result = 31 * result + (needCheck != false ? 1 : 0);
        result = 31 * result + (unitTypeCode != null ? unitTypeCode.hashCode() : 0);
        return result;
    }
}
