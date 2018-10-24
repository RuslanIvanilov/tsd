package ru.defo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by syn-wms on 04.10.2017.
 */
public class WmInventoryPosPK implements Serializable {
    private Long inventoryId;
    private Long inventoryPosId;

    @Column(name = "inventory_id", nullable = false)
    @Id
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Column(name = "inventory_pos_id", nullable = false)
    @Id
    public Long getInventoryPosId() {
        return inventoryPosId;
    }

    public void setInventoryPosId(Long inventoryPosId) {
        this.inventoryPosId = inventoryPosId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmInventoryPosPK that = (WmInventoryPosPK) o;

        if (inventoryId != null ? !inventoryId.equals(that.inventoryId) : that.inventoryId != null) return false;
        if (inventoryPosId != null ? !inventoryPosId.equals(that.inventoryPosId) : that.inventoryPosId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = inventoryId != null ? inventoryId.hashCode() : 0;
        result = 31 * result + (inventoryPosId != null ? inventoryPosId.hashCode() : 0);
        return result;
    }
}
