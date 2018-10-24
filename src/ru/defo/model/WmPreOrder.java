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

import org.hibernate.annotations.Check;

/**
 * Created by syn-wms on 21.03.2017.
 */
@Entity
@Table(name = "wm_pre_order", schema = "public", catalog = "wms")
public class WmPreOrder {
    private Long orderId;
    private Long orderTypeId;
    private String orderCode;
    private String clientDocCode;
    private Long clientId;
    private Timestamp expectedDate;
    private Timestamp factDate;
    private Long carId;
    private Long placeCount;
    private Long dockId;
    private Long routeId;
    private Long routeSequence;
    private Long batchId;
    private Long statusId;
	private Long wmOrderLink;
	private Long errorId;
    private String errorComment;
    private String clientDocDescription;

    private WmOrderType orderType;
    private WmStatus status;
    private WmRoute route;

    public WmPreOrder(){
    	super();
    }

    public WmPreOrder(Long orderId, Long orderTypeId, Long StatusId, Long ClientId){
		this.setOrderId(orderId);
		this.setOrderTypeId(orderTypeId);
		this.setStatusId(StatusId);
		this.setClientId(ClientId);
    }

    public WmPreOrder(long orderId){
    	this.setOrderId(Long.valueOf(orderId));
		this.setOrderTypeId(1L);
		this.setStatusId(1L);
		this.setClientId(1L);
    }


	@Id
    @Column(name = "order_id", nullable = false)
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "order_type_id", nullable = true)
    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }




    @Basic
    @Column(name = "order_code", nullable = true, length = 50)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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
    @Column(name = "client_doc_description", nullable = true, length = 250)
    public String getClientDocDescription() {
        return clientDocDescription;
    }

    public void setClientDocDescription(String clientDocDescription) {
        this.clientDocDescription = clientDocDescription;
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
    @Column(name = "route_id", nullable = true)
    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    @Basic
    @Column(name = "route_sequence", nullable = true)
    public Long getRouteSequence() {
        return routeSequence;
    }

    public void setRouteSequence(Long routeSequence) {
        this.routeSequence = routeSequence;
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
    @Column(name = "wm_order_link", nullable = true)
    public Long getWmOrderLink() {
        return wmOrderLink;
    }

    public void setWmOrderLink(Long wmOrderLink) {
        this.wmOrderLink = wmOrderLink;
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
    @Column(name = "error_comment", nullable = true, length = 250)
    public String getErrorComment() {
        return errorComment;
    }

    public void setErrorComment(String errorComment) {
        this.errorComment = errorComment;
    }


    @OneToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name = "order_type_id"  , referencedColumnName = "order_type_id", insertable = false, updatable = false)
	public WmOrderType getOrderType() {
		return this.orderType;
	}

	public void setOrderType(WmOrderType orderType) {
		this.orderType = orderType;
	}

	@OneToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "status_id"  , referencedColumnName = "status_id", insertable = false, updatable = false)
	public WmStatus getStatus() {
		return this.status;
	}

	public void setStatus(WmStatus status) {
		this.status = status;
	}

	@OneToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name = "route_id"  , referencedColumnName = "route_id", insertable = false, updatable = false)
	public WmRoute getRoute() {
		return this.route;
	}


	public void setRoute(WmRoute route) {
		this.route = route;
	}

	/**
	 * @see Check equals without orderId <- PK field
	 * */
	public boolean equals(WmPreOrder preOrderNew){
		if(this.getOrderCode() != null ? !this.getOrderCode().equals(preOrderNew.getOrderCode()) : preOrderNew.getOrderCode() != null) return false;
//		if(this.getOrderTypeId() != null ? !this.getOrderTypeId().equals(preOrderNew.getOrderTypeId()) : preOrderNew.getOrderTypeId() != null) return false;
		if(this.getClientDocCode() != null ? !this.getClientDocCode().equals(preOrderNew.getClientDocCode()) : preOrderNew.getClientDocCode() != null) return false;
//		if(this.getClientId() != null ? !this.getClientId().equals(preOrderNew.getClientId()) : preOrderNew.getClientId() != null) return false;
		if(this.getClientDocDescription() != null ? !this.getClientDocDescription().equals(preOrderNew.getClientDocDescription()) : preOrderNew.getClientDocDescription() != null) return false;
		if(this.getExpectedDate() != null ? !this.getExpectedDate().equals(preOrderNew.getExpectedDate()) : preOrderNew.getExpectedDate() != null) return false;
		if(this.getCarId() != null ? !this.getCarId().equals(preOrderNew.getCarId()) : preOrderNew.getCarId() != null) return false;
		if(this.getFactDate() != null ? !this.getFactDate().equals(preOrderNew.getFactDate()) : preOrderNew.getFactDate() != null) return false;
		if(this.getPlaceCount() != null ? !this.getPlaceCount().equals(preOrderNew.getPlaceCount()) : preOrderNew.getPlaceCount() != null) return false;
		if(this.getDockId() != null ? !this.getDockId().equals(preOrderNew.getDockId()) : preOrderNew.getDockId() != null) return false;
		if(this.getRouteId() != null ? !this.getRouteId().equals(preOrderNew.getRouteId()) : preOrderNew.getRouteId() != null) return false;
		if(this.getRouteSequence() != null ? !this.getRouteSequence().equals(preOrderNew.getRouteSequence()) : preOrderNew.getRouteSequence() != null) return false;
		if(this.getBatchId() != null ? !this.getBatchId().equals(preOrderNew.getBatchId()) : preOrderNew.getBatchId() != null) return false;
		if(this.getStatusId() != null ? !this.getStatusId().equals(preOrderNew.getStatusId()) : preOrderNew.getStatusId() != null) return false;

		return true;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmPreOrder wmOrder = (WmPreOrder) o;

        if (orderId != null ? !orderId.equals(wmOrder.orderId) : wmOrder.orderId != null) return false;
        if (orderTypeId != null ? !orderTypeId.equals(wmOrder.orderTypeId) : wmOrder.orderTypeId != null) return false;
        if (orderCode != null ? !orderCode.equals(wmOrder.orderCode) : wmOrder.orderCode != null) return false;
        if (clientDocCode != null ? !clientDocCode.equals(wmOrder.clientDocCode) : wmOrder.clientDocCode != null)
            return false;
        if (clientDocDescription != null ? !clientDocDescription.equals(wmOrder.clientDocDescription) : wmOrder.clientDocDescription != null)
            return false;
        if (clientId != null ? !clientId.equals(wmOrder.clientId) : wmOrder.clientId != null) return false;
        if (expectedDate != null ? !expectedDate.equals(wmOrder.expectedDate) : wmOrder.expectedDate != null)
            return false;
        if (factDate != null ? !factDate.equals(wmOrder.factDate) : wmOrder.factDate != null) return false;
        if (carId != null ? !carId.equals(wmOrder.carId) : wmOrder.carId != null) return false;
        if (placeCount != null ? !placeCount.equals(wmOrder.placeCount) : wmOrder.placeCount != null) return false;
        if (dockId != null ? !dockId.equals(wmOrder.dockId) : wmOrder.dockId != null) return false;
        if (routeId != null ? !routeId.equals(wmOrder.routeId) : wmOrder.routeId != null) return false;
        if (routeSequence != null ? !routeSequence.equals(wmOrder.routeSequence) : wmOrder.routeSequence != null)
            return false;
        if (batchId != null ? !batchId.equals(wmOrder.batchId) : wmOrder.batchId != null) return false;
        if (statusId != null ? !statusId.equals(wmOrder.statusId) : wmOrder.statusId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (orderTypeId != null ? orderTypeId.hashCode() : 0);
        result = 31 * result + (orderCode != null ? orderCode.hashCode() : 0);
        result = 31 * result + (clientDocCode != null ? clientDocCode.hashCode() : 0);
        result = 31 * result + (clientDocDescription != null ? clientDocDescription.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (expectedDate != null ? expectedDate.hashCode() : 0);
        result = 31 * result + (factDate != null ? factDate.hashCode() : 0);
        result = 31 * result + (carId != null ? carId.hashCode() : 0);
        result = 31 * result + (placeCount != null ? placeCount.hashCode() : 0);
        result = 31 * result + (dockId != null ? dockId.hashCode() : 0);
        result = 31 * result + (routeId != null ? routeId.hashCode() : 0);
        result = 31 * result + (routeSequence != null ? routeSequence.hashCode() : 0);
        result = 31 * result + (batchId != null ? batchId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        return result;
    }
}
