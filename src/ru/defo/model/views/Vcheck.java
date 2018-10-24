package ru.defo.model.views;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by syn-wms on 05.09.2017.
 */
@Entity
public class Vcheck {
    private String unitCode;
    private Long unitId;
    private Long quantity;
    private Timestamp createDate;
    private String surname;
    private String firstName;
    private Long orderId;
    private Long orderPosId;
    private Long checkId;

    @Basic
    @Column(name = "unit_code", nullable = true, length = 50)
    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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
    @Column(name = "quantity", nullable = true)
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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
    @Column(name = "surname", nullable = true, length = 250)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = 250)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "order_id", nullable = true)
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "order_pos_id", nullable = true)
    public Long getOrderPosId() {
        return orderPosId;
    }

    public void setOrderPosId(Long orderPosId) {
        this.orderPosId = orderPosId;
    }

    @Id
    @Column(name = "check_id", nullable = true)
    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vcheck c = (Vcheck) o;

        if (unitCode != null ? !unitCode.equals(c.unitCode) : c.unitCode != null) return false;
        if (unitId != null ? !unitId.equals(c.unitId) : c.unitId != null) return false;

        if (quantity != null ? !quantity.equals(c.quantity) : c.quantity != null) return false;
        if (createDate != null ? !createDate.equals(c.createDate) : c.createDate != null) return false;
        if (surname != null ? !surname.equals(c.surname) : c.surname != null) return false;
        if (firstName != null ? !firstName.equals(c.firstName) : c.firstName != null) return false;
        if (orderId != null ? !orderId.equals(c.orderId) : c.orderId != null) return false;
        if (orderPosId != null ? !orderPosId.equals(c.orderPosId) : c.orderPosId != null) return false;
        if (checkId != null ? !checkId.equals(c.checkId) : c.checkId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = unitCode != null ? unitCode.hashCode() : 0;
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (orderPosId != null ? orderPosId.hashCode() : 0);
        result = 31 * result + (checkId != null ? checkId.hashCode() : 0);
        return result;
    }
}
