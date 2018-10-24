package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Created by syn-wms on 21.03.2017.
 */
@Entity
@Table(name = "wm_advice", schema = "public", catalog = "wms")
public class WmAdvice {
    private Long adviceId;
    private Long adviceTypeId;
    private WmAdviceType adviceType;
    private String adviceCode;
    private Long clientId;
    private Timestamp expectedDate;
    private Timestamp factDate;
    private Long carId;
    private Long placeCount;
    private Long dockId;
    private Long statusId;
    private String clientDocCode;
    private Long errorId;
    private String errorComment;

    private WmStatus status;
    private WmBin bin;

    @Id
    @Column(name = "advice_id", nullable = false)
    public Long getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(Long adviceId) {
        this.adviceId = adviceId;
    }

    @Basic
    @Column(name = "advice_type_id", nullable = true)
    public Long getAdviceTypeId() {
        return adviceTypeId;
    }

    public void setAdviceTypeId(Long adviceTypeId) {
        this.adviceTypeId = adviceTypeId;
    }

    @OneToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "advice_type_id"  , referencedColumnName = "advice_type_id", insertable = false, updatable = false)
    @Fetch(FetchMode.SELECT)
	public WmAdviceType getAdviceType() {
		return this.adviceType;
	}

	public void setAdviceType(WmAdviceType adviceType) {
		this.adviceType = adviceType;
	}

    @Basic
    @Column(name = "advice_code", nullable = true, length = 50)
    public String getAdviceCode() {
        return adviceCode;
    }

    public void setAdviceCode(String adviceCode) {
        this.adviceCode = adviceCode;
    }

    @Basic
    @Column(name = "client_id", nullable = true)
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "error_id", nullable = true)
    public Long getErrorId() {
        return errorId;
    }

    public void setErrorId(Long errorId) {
        this.errorId = errorId;
    }


    @Basic
    @Column(name = "expected_date", nullable = true)
    public Timestamp getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Timestamp expectedDate) {
        this.expectedDate = expectedDate;
    }

    @Basic
    @Column(name = "fact_date", nullable = true)
    public Timestamp getFactDate() {
        return factDate;
    }

    public void setFactDate(Timestamp factDate) {
        this.factDate = factDate;
    }

    @Basic
    @Column(name = "car_id", nullable = true)
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Basic
    @Column(name = "place_count", nullable = true)
    public Long getPlaceCount() {
        return placeCount;
    }

    public void setPlaceCount(Long placeCount) {
        this.placeCount = placeCount;
    }

    @Basic
    @Column(name = "dock_id", nullable = true)
    public Long getDockId() {
        return dockId;
    }

    public void setDockId(Long dockId) {
        this.dockId = dockId;
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
    @Column(name = "client_doc_code", nullable = true, length = 50)
    public String getClientDocCode() {
        return clientDocCode;
    }

    public void setClientDocCode(String clientDocCode) {
        this.clientDocCode = clientDocCode;
    }

    @Basic
    @Column(name = "error_comment", nullable = true, length = 250)
    public String getErrorComment() {
        return errorComment;
    }

    public void setErrorComment(String errorComment) {
        this.errorComment = errorComment;
    }


    @OneToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "status_id"  , referencedColumnName = "status_id", insertable = false, updatable = false)
	public WmStatus getStatus() {
		return this.status;
	}

	public void setStatus(WmStatus status) {
		this.status = status;
	}

	@OneToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name = "dock_id", referencedColumnName = "bin_id", insertable = false, updatable = false)
	public WmBin getBin(){
		return this.bin;
	}

	public void setBin(WmBin bin){
		this.bin = bin;
	}



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmAdvice wmAdvice = (WmAdvice) o;

        if (adviceId != null ? !adviceId.equals(wmAdvice.adviceId) : wmAdvice.adviceId != null) return false;
        if (adviceTypeId != null ? !adviceTypeId.equals(wmAdvice.adviceTypeId) : wmAdvice.adviceTypeId != null)
            return false;
        if (adviceCode != null ? !adviceCode.equals(wmAdvice.adviceCode) : wmAdvice.adviceCode != null) return false;
        if (clientId != null ? !clientId.equals(wmAdvice.clientId) : wmAdvice.clientId != null) return false;
        if (expectedDate != null ? !expectedDate.equals(wmAdvice.expectedDate) : wmAdvice.expectedDate != null)
            return false;
        if (factDate != null ? !factDate.equals(wmAdvice.factDate) : wmAdvice.factDate != null) return false;
        if (carId != null ? !carId.equals(wmAdvice.carId) : wmAdvice.carId != null) return false;
        if (placeCount != null ? !placeCount.equals(wmAdvice.placeCount) : wmAdvice.placeCount != null) return false;
        if (dockId != null ? !dockId.equals(wmAdvice.dockId) : wmAdvice.dockId != null) return false;
        if (statusId != null ? !statusId.equals(wmAdvice.statusId) : wmAdvice.statusId != null) return false;
        if (clientDocCode != null ? !clientDocCode.equals(wmAdvice.clientDocCode) : wmAdvice.clientDocCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adviceId != null ? adviceId.hashCode() : 0;
        result = 31 * result + (adviceTypeId != null ? adviceTypeId.hashCode() : 0);
        result = 31 * result + (adviceCode != null ? adviceCode.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (expectedDate != null ? expectedDate.hashCode() : 0);
        result = 31 * result + (factDate != null ? factDate.hashCode() : 0);
        result = 31 * result + (carId != null ? carId.hashCode() : 0);
        result = 31 * result + (placeCount != null ? placeCount.hashCode() : 0);
        result = 31 * result + (dockId != null ? dockId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (clientDocCode != null ? clientDocCode.hashCode() : 0);
        return result;
    }
}
