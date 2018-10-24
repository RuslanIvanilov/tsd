package ru.defo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 22.03.2018.
 */
@Entity
@Table(name = "wm_job_type", schema = "public", catalog = "wms")
public class WmJobType {
    private Long jobTypeId;
    private String description;

    @Id
    @Column(name = "job_type_id", nullable = false)
    public Long getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(Long jobTypeId) {
        this.jobTypeId = jobTypeId;
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

        WmJobType wmJobType = (WmJobType) o;

        if (jobTypeId != null ? !jobTypeId.equals(wmJobType.jobTypeId) : wmJobType.jobTypeId != null) return false;
        if (description != null ? !description.equals(wmJobType.description) : wmJobType.description != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jobTypeId != null ? jobTypeId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
