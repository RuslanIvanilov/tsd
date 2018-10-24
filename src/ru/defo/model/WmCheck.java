package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 26.02.2018.
 */
@Entity
@Table(name = "wm_check", schema = "public", catalog = "wms")
public class WmCheck {
    private Long checkId;
    private Long pickId;
    private Long unitId;
    private String scanBarcode;
    private Long articleId;
    private Long skuId;
    private Long createUser;
    private Timestamp createDate;
    private Long modUser;
    private Timestamp modDate;
    private Long scanQuantity;
    private Long pickQuantity;
    private Long orderId;
    private Long orderPosId;

    @Id
    @Column(name = "check_id", nullable = false)
    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    @Basic
    @Column(name = "pick_id", nullable = true)
    public Long getPickId() {
        return pickId;
    }

    public void setPickId(Long pickId) {
        this.pickId = pickId;
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
    @Column(name = "scan_barcode", nullable = true, length = 50)
    public String getScanBarcode() {
        return scanBarcode;
    }

    public void setScanBarcode(String scanBarcode) {
        this.scanBarcode = scanBarcode;
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
    @Column(name = "mod_user", nullable = true)
    public Long getModUser() {
        return modUser;
    }

    public void setModUser(Long modUser) {
        this.modUser = modUser;
    }

    @Basic
    @Column(name = "mod_date", nullable = true)
    public Timestamp getModDate() {
        return modDate;
    }

    public void setModDate(Timestamp modDate) {
        this.modDate = modDate;
    }

    @Basic
    @Column(name = "scan_quantity", nullable = true)
    public Long getScanQuantity() {
        return scanQuantity;
    }

    public void setScanQuantity(Long scanQuantity) {
        this.scanQuantity = scanQuantity;
    }

    @Basic
    @Column(name = "pick_quantity", nullable = true)
    public Long getPickQuantity() {
        return pickQuantity;
    }

    public void setPickQuantity(Long pickQuantity) {
        this.pickQuantity = pickQuantity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmCheck wmCheck = (WmCheck) o;

        if (checkId != null ? !checkId.equals(wmCheck.checkId) : wmCheck.checkId != null) return false;
        if (pickId != null ? !pickId.equals(wmCheck.pickId) : wmCheck.pickId != null) return false;
        if (unitId != null ? !unitId.equals(wmCheck.unitId) : wmCheck.unitId != null) return false;
        if (scanBarcode != null ? !scanBarcode.equals(wmCheck.scanBarcode) : wmCheck.scanBarcode != null) return false;
        if (articleId != null ? !articleId.equals(wmCheck.articleId) : wmCheck.articleId != null) return false;
        if (skuId != null ? !skuId.equals(wmCheck.skuId) : wmCheck.skuId != null) return false;
        if (createUser != null ? !createUser.equals(wmCheck.createUser) : wmCheck.createUser != null) return false;
        if (createDate != null ? !createDate.equals(wmCheck.createDate) : wmCheck.createDate != null) return false;
        if (modUser != null ? !modUser.equals(wmCheck.modUser) : wmCheck.modUser != null) return false;
        if (modDate != null ? !modDate.equals(wmCheck.modDate) : wmCheck.modDate != null) return false;
        if (scanQuantity != null ? !scanQuantity.equals(wmCheck.scanQuantity) : wmCheck.scanQuantity != null)
            return false;
        if (pickQuantity != null ? !pickQuantity.equals(wmCheck.pickQuantity) : wmCheck.pickQuantity != null)
            return false;
        if (orderId != null ? !orderId.equals(wmCheck.orderId) : wmCheck.orderId != null) return false;
        if (orderPosId != null ? !orderPosId.equals(wmCheck.orderPosId) : wmCheck.orderPosId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = checkId != null ? checkId.hashCode() : 0;
        result = 31 * result + (pickId != null ? pickId.hashCode() : 0);
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        result = 31 * result + (scanBarcode != null ? scanBarcode.hashCode() : 0);
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (modUser != null ? modUser.hashCode() : 0);
        result = 31 * result + (modDate != null ? modDate.hashCode() : 0);
        result = 31 * result + (scanQuantity != null ? scanQuantity.hashCode() : 0);
        result = 31 * result + (pickQuantity != null ? pickQuantity.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (orderPosId != null ? orderPosId.hashCode() : 0);
        return result;
    }
}
