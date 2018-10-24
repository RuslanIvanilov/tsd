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
public class Vpick {
    private String unitCode;
    private Long unitId;
    private String binCode;
    private Long binId;
    private String qstateName;
    private Long qualityStateId;
    private Long quantity;
    private Timestamp createDate;
    private String surname;
    private String firstName;
    private Long orderId;
    private Long orderPosId;
    private Long pickId;

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
    @Column(name = "bin_code", nullable = true, length = 150)
    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
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
    @Column(name = "qstate_name", nullable = true, length = 250)
    public String getQstateName() {
        return qstateName;
    }

    public void setQstateName(String qstateName) {
        this.qstateName = qstateName;
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
    @Column(name = "pick_id", nullable = true)
    public Long getPickId() {
        return pickId;
    }

    public void setPickId(Long pickId) {
        this.pickId = pickId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vpick vpick = (Vpick) o;

        if (unitCode != null ? !unitCode.equals(vpick.unitCode) : vpick.unitCode != null) return false;
        if (unitId != null ? !unitId.equals(vpick.unitId) : vpick.unitId != null) return false;
        if (binCode != null ? !binCode.equals(vpick.binCode) : vpick.binCode != null) return false;
        if (binId != null ? !binId.equals(vpick.binId) : vpick.binId != null) return false;
        if (qstateName != null ? !qstateName.equals(vpick.qstateName) : vpick.qstateName != null) return false;
        if (qualityStateId != null ? !qualityStateId.equals(vpick.qualityStateId) : vpick.qualityStateId != null)
            return false;
        if (quantity != null ? !quantity.equals(vpick.quantity) : vpick.quantity != null) return false;
        if (createDate != null ? !createDate.equals(vpick.createDate) : vpick.createDate != null) return false;
        if (surname != null ? !surname.equals(vpick.surname) : vpick.surname != null) return false;
        if (firstName != null ? !firstName.equals(vpick.firstName) : vpick.firstName != null) return false;
        if (orderId != null ? !orderId.equals(vpick.orderId) : vpick.orderId != null) return false;
        if (orderPosId != null ? !orderPosId.equals(vpick.orderPosId) : vpick.orderPosId != null) return false;
        if (pickId != null ? !pickId.equals(vpick.pickId) : vpick.pickId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = unitCode != null ? unitCode.hashCode() : 0;
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        result = 31 * result + (binCode != null ? binCode.hashCode() : 0);
        result = 31 * result + (binId != null ? binId.hashCode() : 0);
        result = 31 * result + (qstateName != null ? qstateName.hashCode() : 0);
        result = 31 * result + (qualityStateId != null ? qualityStateId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (orderPosId != null ? orderPosId.hashCode() : 0);
        result = 31 * result + (pickId != null ? pickId.hashCode() : 0);
        return result;
    }
}
