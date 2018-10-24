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
@Table(name = "wm_equipment_type", schema = "public", catalog = "wms")
public class WmEquipmentType {
    private Long equipmentTypeId;
    private String description;
    private Long maxHeigh;
    private Long maxWeight;

    @Id
    @Column(name = "equipment_type_id", nullable = false)
    public Long getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(Long equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
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
    @Column(name = "max_heigh", nullable = true)
    public Long getMaxHeigh() {
        return maxHeigh;
    }

    public void setMaxHeigh(Long maxHeigh) {
        this.maxHeigh = maxHeigh;
    }

    @Basic
    @Column(name = "max_weight", nullable = true)
    public Long getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Long maxWeight) {
        this.maxWeight = maxWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmEquipmentType that = (WmEquipmentType) o;

        if (equipmentTypeId != null ? !equipmentTypeId.equals(that.equipmentTypeId) : that.equipmentTypeId != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (maxHeigh != null ? !maxHeigh.equals(that.maxHeigh) : that.maxHeigh != null) return false;
        if (maxWeight != null ? !maxWeight.equals(that.maxWeight) : that.maxWeight != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = equipmentTypeId != null ? equipmentTypeId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (maxHeigh != null ? maxHeigh.hashCode() : 0);
        result = 31 * result + (maxWeight != null ? maxWeight.hashCode() : 0);
        return result;
    }
}
