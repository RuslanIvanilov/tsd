package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 21.03.2017.
 */
@Entity
@Table(name = "wm_pick", schema = "public", catalog = "wms")
public class WmPick {
    private Long pickId;
    private Long sourceBinId;
    private Long sourceUnitId;
    private Long destUnitId;
    private Long articleId;
    private Long skuId;
    private Long qualityStateId;
    private Long lotId;
    private Long quantity;
    private Long createUser;
    private Timestamp createDate;
    private String boxBarcode;
    private Long orderId;
    private Long orderPosId;
    private Long statusId;

    @Id
    @Column(name = "pick_id", nullable = false)
    public Long getPickId() {
        return pickId;
    }

    public void setPickId(Long pickId) {
        this.pickId = pickId;
    }

    @Basic
    @Column(name = "order_id", nullable = false)
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "order_pos_id", nullable = false)
    public Long getOrderPosId() {
        return orderPosId;
    }

    public void setOrderPosId(Long orderPosId) {
        this.orderPosId = orderPosId;
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
    @Column(name = "source_bin_id", nullable = true)
    public Long getSourceBinId() {
        return sourceBinId;
    }

    public void setSourceBinId(Long sourceBinId) {
        this.sourceBinId = sourceBinId;
    }

    @Basic
    @Column(name = "source_unit_id", nullable = true)
    public Long getSourceUnitId() {
        return sourceUnitId;
    }

    public void setSourceUnitId(Long sourceUnitId) {
        this.sourceUnitId = sourceUnitId;
    }

    @Basic
    @Column(name = "dest_unit_id", nullable = true)
    public Long getDestUnitId() {
        return destUnitId;
    }

    public void setDestUnitId(Long destUnitId) {
        this.destUnitId = destUnitId;
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
    @Column(name = "box_barcode", nullable = true, length = 50)
    public String getBoxBarcode() {
        return boxBarcode;
    }

    public void setBoxBarcode(String boxBarcode) {
        this.boxBarcode = boxBarcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmPick wmPick = (WmPick) o;

        if (pickId != null ? !pickId.equals(wmPick.pickId) : wmPick.pickId != null) return false;

        if (orderId != null ? !orderId.equals(wmPick.orderId) : wmPick.orderId != null) return false;
        if (orderPosId != null ? !orderPosId.equals(wmPick.orderPosId) : wmPick.orderPosId != null) return false;

        if (sourceBinId != null ? !sourceBinId.equals(wmPick.sourceBinId) : wmPick.sourceBinId != null) return false;
        if (sourceUnitId != null ? !sourceUnitId.equals(wmPick.sourceUnitId) : wmPick.sourceUnitId != null)
            return false;
        if (destUnitId != null ? !destUnitId.equals(wmPick.destUnitId) : wmPick.destUnitId != null) return false;
        if (articleId != null ? !articleId.equals(wmPick.articleId) : wmPick.articleId != null) return false;
        if (skuId != null ? !skuId.equals(wmPick.skuId) : wmPick.skuId != null) return false;
        if (qualityStateId != null ? !qualityStateId.equals(wmPick.qualityStateId) : wmPick.qualityStateId != null)
            return false;
        if (lotId != null ? !lotId.equals(wmPick.lotId) : wmPick.lotId != null) return false;
        if (quantity != null ? !quantity.equals(wmPick.quantity) : wmPick.quantity != null) return false;
        if (createUser != null ? !createUser.equals(wmPick.createUser) : wmPick.createUser != null) return false;
        if (createDate != null ? !createDate.equals(wmPick.createDate) : wmPick.createDate != null) return false;
        if (boxBarcode != null ? !boxBarcode.equals(wmPick.boxBarcode) : wmPick.boxBarcode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pickId != null ? pickId.hashCode() : 0;
        result = 31 * result + (sourceBinId != null ? sourceBinId.hashCode() : 0);
        result = 31 * result + (sourceUnitId != null ? sourceUnitId.hashCode() : 0);
        result = 31 * result + (destUnitId != null ? destUnitId.hashCode() : 0);
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (qualityStateId != null ? qualityStateId.hashCode() : 0);
        result = 31 * result + (lotId != null ? lotId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (boxBarcode != null ? boxBarcode.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (orderPosId != null ? orderPosId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        return result;
    }
}
