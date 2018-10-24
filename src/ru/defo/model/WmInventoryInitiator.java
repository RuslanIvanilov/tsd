package ru.defo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 04.10.2017.
 */
@Entity
@Table(name = "wm_inventory_initiator", schema = "public", catalog = "wms")
public class WmInventoryInitiator {
    private Long initiatorId;
    private String initiatorTypeCode;
    private String description;

    @Id
    @Column(name = "initiator_id", nullable = false)
    public Long getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Long initiatorId) {
        this.initiatorId = initiatorId;
    }

    @Basic
    @Column(name = "initiator_type_code", nullable = true, length = 50)
    public String getInitiatorTypeCode() {
        return initiatorTypeCode;
    }

    public void setInitiatorTypeCode(String initiatorTypeCode) {
        this.initiatorTypeCode = initiatorTypeCode;
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

        WmInventoryInitiator that = (WmInventoryInitiator) o;

        if (initiatorId != null ? !initiatorId.equals(that.initiatorId) : that.initiatorId != null) return false;
        if (initiatorTypeCode != null ? !initiatorTypeCode.equals(that.initiatorTypeCode) : that.initiatorTypeCode != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = initiatorId != null ? initiatorId.hashCode() : 0;
        result = 31 * result + (initiatorTypeCode != null ? initiatorTypeCode.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
