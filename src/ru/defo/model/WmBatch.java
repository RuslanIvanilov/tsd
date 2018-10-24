package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 07.11.2017.
 */
@Entity
@Table(name = "wm_batch", schema = "public", catalog = "wms")
public class WmBatch {
    private Long batchId;
    private Long createUser;
    private Timestamp createDate;
    private Long statusId;
    private Timestamp startDate;
    private Timestamp endDate;

    @Id
    @Column(name = "batch_id", nullable = false)
    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    @Basic
    @Column(name = "create_user", nullable = true)
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "create_date", nullable = true)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "status_id", nullable = true)
    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "start_date", nullable = true)
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = true)
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmBatch wmBatch = (WmBatch) o;

        if (batchId != null ? !batchId.equals(wmBatch.batchId) : wmBatch.batchId != null) return false;
        if (createUser != null ? !createUser.equals(wmBatch.createUser) : wmBatch.createUser != null) return false;
        if (createDate != null ? !createDate.equals(wmBatch.createDate) : wmBatch.createDate != null) return false;
        if (statusId != null ? !statusId.equals(wmBatch.statusId) : wmBatch.statusId != null) return false;
        if (startDate != null ? !startDate.equals(wmBatch.startDate) : wmBatch.startDate != null) return false;
        if (endDate != null ? !endDate.equals(wmBatch.endDate) : wmBatch.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = batchId != null ? batchId.hashCode() : 0;
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
