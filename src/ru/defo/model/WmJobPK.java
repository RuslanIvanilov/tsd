package ru.defo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by syn-wms on 22.03.2018.
 */
public class WmJobPK implements Serializable {
    private Long jobId;
    private Long jobPosId;

    @Column(name = "job_id", nullable = false)
    @Id
    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Column(name = "job_pos_id", nullable = false)
    @Id
    public Long getJobPosId() {
        return jobPosId;
    }

    public void setJobPosId(Long jobPosId) {
        this.jobPosId = jobPosId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmJobPK wmJobPK = (WmJobPK) o;

        if (jobId != null ? !jobId.equals(wmJobPK.jobId) : wmJobPK.jobId != null) return false;
        if (jobPosId != null ? !jobPosId.equals(wmJobPK.jobPosId) : wmJobPK.jobPosId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jobId != null ? jobId.hashCode() : 0;
        result = 31 * result + (jobPosId != null ? jobPosId.hashCode() : 0);
        return result;
    }
}
