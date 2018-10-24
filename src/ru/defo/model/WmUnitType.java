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
@Table(name = "wm_unit_type", schema = "public", catalog = "wms")
public class WmUnitType {
    private String unitTypeCode;
    private String description;
    private Long heigh;
    private Long width;
    private Long depth;
    private Long maxWight;
    private boolean blocked;

    @Id
    @Column(name = "unit_type_code", nullable = false, length = 3)
    public String getUnitTypeCode() {
        return unitTypeCode;
    }

    public void setUnitTypeCode(String unitTypeCode) {
        this.unitTypeCode = unitTypeCode;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 250)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "max_wight", nullable = true)
    public Long getMaxWight() {
        return maxWight;
    }

    public void setMaxWight(Long maxWight) {
        this.maxWight = maxWight;
    }

    @Basic
    @Column(name = "blocked", nullable = true)
    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmUnitType that = (WmUnitType) o;

        if (unitTypeCode != null ? !unitTypeCode.equals(that.unitTypeCode) : that.unitTypeCode != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (heigh != null ? !heigh.equals(that.heigh) : that.heigh != null) return false;
        if (width != null ? !width.equals(that.width) : that.width != null) return false;
        if (depth != null ? !depth.equals(that.depth) : that.depth != null) return false;
        if (maxWight != null ? !maxWight.equals(that.maxWight) : that.maxWight != null) return false;
        if (blocked != that.blocked) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = unitTypeCode != null ? unitTypeCode.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (heigh != null ? heigh.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (depth != null ? depth.hashCode() : 0);
        result = 31 * result + (maxWight != null ? maxWight.hashCode() : 0);
        result = 31 * result + (blocked != false ? 1 : 0);
        return result;
    }
}
