package ru.defo.model.views;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by syn-wms on 29.11.2017.
 */
public class VrouteordposPK implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long orderId;
    private Long orderPosId;
    private Long preOrderId;
    private Long preOrderPosId;
    private Long routeId;

    @Column(name = "order_id", nullable = false)
    @Id
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Column(name = "order_pos_id", nullable = false)
    @Id
    public Long getOrderPosId() {
        return orderPosId;
    }

    public void setOrderPosId(Long orderPosId) {
        this.orderPosId = orderPosId;
    }

    @Column(name = "pre_order_id", nullable = false)
    @Id
    public Long getPreOrderId() {
        return preOrderId;
    }

    public void setPreOrderId(Long preOrderId) {
        this.preOrderId = preOrderId;
    }

    @Column(name = "pre_order_pos_id", nullable = false)
    @Id
    public Long getPreOrderPosId() {
        return preOrderPosId;
    }

    public void setPreOrderPosId(Long preOrderPosId) {
        this.preOrderPosId = preOrderPosId;
    }

    @Id
    @Column(name = "route_id", nullable = false)
    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VrouteordposPK that = (VrouteordposPK) o;

         if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
         if (orderPosId != null ? !orderPosId.equals(that.orderPosId) : that.orderPosId != null) return false;
        if (preOrderId != null ? !preOrderId.equals(that.preOrderId) : that.preOrderId != null) return false;
        if (preOrderPosId != null ? !preOrderPosId.equals(that.preOrderPosId) : that.preOrderPosId != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (orderPosId != null ? orderPosId.hashCode() : 0);
        result = 31 * result + (preOrderId != null ? preOrderId.hashCode() : 0);
       // int result = preOrderId != null ? preOrderId.hashCode() : 0;
        result = 31 * result + (preOrderPosId != null ? preOrderPosId.hashCode() : 0);
        return result;
    }
}
