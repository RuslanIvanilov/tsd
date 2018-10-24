package ru.defo.model.views;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by syn-wms on 21.03.2017.
 */
public class VpreorderposPK implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long orderId;
    private Long orderPosId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VpreorderposPK that = (VpreorderposPK) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (orderPosId != null ? !orderPosId.equals(that.orderPosId) : that.orderPosId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (orderPosId != null ? orderPosId.hashCode() : 0);
        return result;
    }
}
