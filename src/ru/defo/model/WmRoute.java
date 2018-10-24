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
@Table(name = "wm_route", schema = "public", catalog = "wms")
public class WmRoute {
    private Long routeId;
    private String routeCode;
    private Timestamp expectedDate;
    private Timestamp factDate;
    private Long carId;
    private Long volume;
    private Long weight;
    private Long batchId;
    private Long statusId;
    private Timestamp printDate;
    private Long commentId;


    public WmRoute(){
    	super();
    }

    public WmRoute(String routeCode){
    	super();
    	this.routeCode = routeCode;
    	this.statusId = 1L;
    }


    @Id
    @Column(name = "route_id", nullable = false)
    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    @Basic
    @Column(name = "route_code", nullable = true, length = 50)
    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
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
    @Column(name = "volume", nullable = true)
    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    @Basic
    @Column(name = "weight", nullable = true)
    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "batch_id", nullable = true)
    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
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
    @Column(name = "print_date", nullable = true)
    public Timestamp getPrintDate() {
		return printDate;
	}

	public void setPrintDate(Timestamp printDate) {
		this.printDate = printDate;
	}

	@Basic
    @Column(name = "comment_id", nullable = true)
	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmRoute wmRoute = (WmRoute) o;

        if (routeId != null ? !routeId.equals(wmRoute.routeId) : wmRoute.routeId != null) return false;
        if (routeCode != null ? !routeCode.equals(wmRoute.routeCode) : wmRoute.routeCode != null) return false;
        if (expectedDate != null ? !expectedDate.equals(wmRoute.expectedDate) : wmRoute.expectedDate != null)
            return false;
        if (factDate != null ? !factDate.equals(wmRoute.factDate) : wmRoute.factDate != null) return false;
        if (carId != null ? !carId.equals(wmRoute.carId) : wmRoute.carId != null) return false;
        if (volume != null ? !volume.equals(wmRoute.volume) : wmRoute.volume != null) return false;
        if (weight != null ? !weight.equals(wmRoute.weight) : wmRoute.weight != null) return false;
        if (batchId != null ? !batchId.equals(wmRoute.batchId) : wmRoute.batchId != null) return false;
        if (statusId != null ? !statusId.equals(wmRoute.statusId) : wmRoute.statusId != null) return false;
        if (printDate != null ? !printDate.equals(wmRoute.printDate) : wmRoute.printDate != null) return false;
        if (commentId != null ? !commentId.equals(wmRoute.commentId) : wmRoute.commentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = routeId != null ? routeId.hashCode() : 0;
        result = 31 * result + (routeCode != null ? routeCode.hashCode() : 0);
        result = 31 * result + (expectedDate != null ? expectedDate.hashCode() : 0);
        result = 31 * result + (factDate != null ? factDate.hashCode() : 0);
        result = 31 * result + (carId != null ? carId.hashCode() : 0);
        result = 31 * result + (volume != null ? volume.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (batchId != null ? batchId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (printDate != null ? printDate.hashCode() : 0);
        result = 31 * result + (commentId != null ? commentId.hashCode() : 0);
        return result;
    }
}
