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
@Table(name = "wm_advice_type", schema = "public", catalog = "wms")
public class WmAdviceType {
    private Long adviceTypeId;
    private String description;

    @Id
    @Column(name = "advice_type_id", nullable = false)
    public Long getAdviceTypeId() {
        return adviceTypeId;
    }

    public void setAdviceTypeId(Long adviceTypeId) {
        this.adviceTypeId = adviceTypeId;
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

        WmAdviceType that = (WmAdviceType) o;

        if (adviceTypeId != null ? !adviceTypeId.equals(that.adviceTypeId) : that.adviceTypeId != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adviceTypeId != null ? adviceTypeId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
