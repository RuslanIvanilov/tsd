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
@Table(name = "wm_quality_state", schema = "public", catalog = "wms")
public class WmQualityState {
    private Long qualityStateId;
    private String description;

    @Id
    @Column(name = "quality_state_id", nullable = false)
    public Long getQualityStateId() {
        return qualityStateId;
    }

    public void setQualityStateId(Long qualityStateId) {
        this.qualityStateId = qualityStateId;
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

        WmQualityState that = (WmQualityState) o;

        if (qualityStateId != null ? !qualityStateId.equals(that.qualityStateId) : that.qualityStateId != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = qualityStateId != null ? qualityStateId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
