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
@Table(name = "wm_sku_crush", schema = "public", catalog = "wms")
public class WmSkuCrush {
    private Long skuCrushId;
    private String description;
    private Long crushClass;

    @Id
    @Column(name = "sku_crush_id", nullable = false)
    public Long getSkuCrushId() {
        return skuCrushId;
    }

    public void setSkuCrushId(Long skuCrushId) {
        this.skuCrushId = skuCrushId;
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
    @Column(name = "crush_class", nullable = true)
    public Long getCrushClass() {
        return crushClass;
    }

    public void setCrushClass(Long crushClass) {
        this.crushClass = crushClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmSkuCrush that = (WmSkuCrush) o;

        if (skuCrushId != null ? !skuCrushId.equals(that.skuCrushId) : that.skuCrushId != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (crushClass != null ? !crushClass.equals(that.crushClass) : that.crushClass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = skuCrushId != null ? skuCrushId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (crushClass != null ? crushClass.hashCode() : 0);
        return result;
    }
}
