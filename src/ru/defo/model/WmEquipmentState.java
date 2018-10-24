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
@Table(name = "wm_equipment_state", schema = "public", catalog = "wms")
public class WmEquipmentState {
    private Long equipmentStateId;
    private String description;

    @Id
    @Column(name = "equipment_state_id", nullable = false)
    public Long getEquipmentStateId() {
        return equipmentStateId;
    }

    public void setEquipmentStateId(Long equipmentStateId) {
        this.equipmentStateId = equipmentStateId;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 250)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmEquipmentState that = (WmEquipmentState) o;

        if (equipmentStateId != null ? !equipmentStateId.equals(that.equipmentStateId) : that.equipmentStateId != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = equipmentStateId != null ? equipmentStateId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
