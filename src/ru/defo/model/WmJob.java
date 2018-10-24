package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by syn-wms on 22.03.2018.
 */
@Entity
@Table(name = "wm_job", schema = "public", catalog = "wms")
@IdClass(WmJobPK.class)
public class WmJob {
    private Long jobId;
    private Long jobPosId;
    private Long jobTypeId;
    private Long statusId;
    private Long binId;
    private Long unitId;
    private Long articleId;
    private Long skuId;
    private Long qualityStateId;
    private Long lotId;
    private Long quantity;
    private Long priority;
    private Long reservId;
    private Long documentId;
    private Long documentTypeId;
    private Timestamp deadlineDate;
    private Timestamp completeDate; 
    private Long executeUser;
    private Timestamp executeDate;
    private Long createUser;
    private Timestamp createDate;

    private WmJobType jobType;

    @Id
    @Column(name = "job_id", nullable = false)
    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Id
    @Column(name = "job_pos_id", nullable = false)
    public Long getJobPosId() {
        return jobPosId;
    }

    public void setJobPosId(Long jobPosId) {
        this.jobPosId = jobPosId;
    }

    @Basic
    @Column(name = "job_type_id", nullable = true)
    public Long getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(Long jobTypeId) {
        this.jobTypeId = jobTypeId;
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
    @Column(name = "bin_id", nullable = true)
    public Long getBinId() {
        return binId;
    }

    public void setBinId(Long binId) {
        this.binId = binId;
    }

    @Basic
    @Column(name = "unit_id", nullable = true)
    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    @Basic
    @Column(name = "article_id", nullable = true)
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Basic
    @Column(name = "sku_id", nullable = true)
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Basic
    @Column(name = "quality_state_id", nullable = true)
    public Long getQualityStateId() {
        return qualityStateId;
    }

    public void setQualityStateId(Long qualityStateId) {
        this.qualityStateId = qualityStateId;
    }

    @Basic
    @Column(name = "lot_id", nullable = true)
    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    @Basic
    @Column(name = "quantity", nullable = true)
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "priority", nullable = true)
    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "reserv_id", nullable = true)
    public Long getReservId() {
        return reservId;
    }

    public void setReservId(Long reservId) {
        this.reservId = reservId;
    }

    @Basic
    @Column(name = "document_id", nullable = true)
    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    @Basic
    @Column(name = "document_type_id", nullable = true)
    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    @Basic
    @Column(name = "deadline_date", nullable = true)
    public Timestamp getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Timestamp deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    @Basic
    @Column(name = "complete_date", nullable = true)
    public Timestamp getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Timestamp completeDate) {
        this.completeDate = completeDate;
    }

    @Basic
    @Column(name = "execute_user", nullable = true)       
    public Long getExecuteUser() {
		return executeUser;
	}

	public void setExecuteUser(Long executeUser) {
		this.executeUser = executeUser;
	}

	@Basic
    @Column(name = "execute_date", nullable = true)
	public Timestamp getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Timestamp executeDate) {
		this.executeDate = executeDate;
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

	@OneToOne(fetch=FetchType.LAZY, optional=true)
    @JoinColumn(name = "job_type_id"  , referencedColumnName = "job_type_id", insertable = false, updatable = false)
    public WmJobType getJobType() {
		return jobType;
	}

	public void setJobType(WmJobType jobType) {
		this.jobType = jobType;
	}
	
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmJob wmJob = (WmJob) o;

        if (jobId != null ? !jobId.equals(wmJob.jobId) : wmJob.jobId != null) return false;
        if (jobPosId != null ? !jobPosId.equals(wmJob.jobPosId) : wmJob.jobPosId != null) return false;
        if (jobTypeId != null ? !jobTypeId.equals(wmJob.jobTypeId) : wmJob.jobTypeId != null) return false;
        if (statusId != null ? !statusId.equals(wmJob.statusId) : wmJob.statusId != null) return false;
        if (binId != null ? !binId.equals(wmJob.binId) : wmJob.binId != null) return false;
        if (unitId != null ? !unitId.equals(wmJob.unitId) : wmJob.unitId != null) return false;
        if (articleId != null ? !articleId.equals(wmJob.articleId) : wmJob.articleId != null) return false;
        if (skuId != null ? !skuId.equals(wmJob.skuId) : wmJob.skuId != null) return false;
        if (qualityStateId != null ? !qualityStateId.equals(wmJob.qualityStateId) : wmJob.qualityStateId != null)
            return false;
        if (lotId != null ? !lotId.equals(wmJob.lotId) : wmJob.lotId != null) return false;
        if (quantity != null ? !quantity.equals(wmJob.quantity) : wmJob.quantity != null) return false;
        if (priority != null ? !priority.equals(wmJob.priority) : wmJob.priority != null) return false;
        if (reservId != null ? !reservId.equals(wmJob.reservId) : wmJob.reservId != null) return false;
        if (documentId != null ? !documentId.equals(wmJob.documentId) : wmJob.documentId != null) return false;
        if (documentTypeId != null ? !documentTypeId.equals(wmJob.documentTypeId) : wmJob.documentTypeId != null)
            return false;
        if (deadlineDate != null ? !deadlineDate.equals(wmJob.deadlineDate) : wmJob.deadlineDate != null) return false;
        if (completeDate != null ? !completeDate.equals(wmJob.completeDate) : wmJob.completeDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jobId != null ? jobId.hashCode() : 0;
        result = 31 * result + (jobPosId != null ? jobPosId.hashCode() : 0);
        result = 31 * result + (jobTypeId != null ? jobTypeId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (binId != null ? binId.hashCode() : 0);
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (qualityStateId != null ? qualityStateId.hashCode() : 0);
        result = 31 * result + (lotId != null ? lotId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (reservId != null ? reservId.hashCode() : 0);
        result = 31 * result + (documentId != null ? documentId.hashCode() : 0);
        result = 31 * result + (documentTypeId != null ? documentTypeId.hashCode() : 0);
        result = 31 * result + (deadlineDate != null ? deadlineDate.hashCode() : 0);
        result = 31 * result + (completeDate != null ? completeDate.hashCode() : 0);
        return result;
    }
}
