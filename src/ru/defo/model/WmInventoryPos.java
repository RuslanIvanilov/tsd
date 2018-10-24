package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Created by syn-wms on 04.10.2017.
 */
@Entity
@Table(name = "wm_inventory_pos", schema = "public", catalog = "wms")
@IdClass(WmInventoryPosPK.class)
public class WmInventoryPos {
    private Long inventoryId;
    private Long inventoryPosId;
    private Long clientId;
    private Long binId;
    private Long unitId;
    private Long articleId;
    private Long skuId;
    private Long qualityStateId;
    private Long lotId;
    private Long quantityBefore;
    private Long quantityAfter;
    private Long createUser;
    private Long executorId;
    private Long statusId;
    private Timestamp createDate;
    private Long quantId;
    private String articleCode;
    private String articleDescript;

    @Id
    @Column(name = "inventory_id", nullable = false)
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Id
    @Column(name = "inventory_pos_id", nullable = false)
    public Long getInventoryPosId() {
        return inventoryPosId;
    }

    public void setInventoryPosId(Long inventoryPosId) {
        this.inventoryPosId = inventoryPosId;
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
    @Column(name = "quantity_before", nullable = true)
    public Long getQuantityBefore() {
        return quantityBefore;
    }

    public void setQuantityBefore(Long quantityBefore) {
        this.quantityBefore = quantityBefore;
    }

    @Basic
    @Column(name = "quantity_after", nullable = true)
    public Long getQuantityAfter() {
        return quantityAfter;
    }

    public void setQuantityAfter(Long quantityAfter) {
        this.quantityAfter = quantityAfter;
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
    @Column(name = "executor_id", nullable = true)
    public Long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
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
    @Column(name = "create_date", nullable = true)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "quant_id", nullable = true)
    public Long getQuantId() {
        return quantId;
    }

    public void setQuantId(Long quantId) {
        this.quantId = quantId;
    }

    @Basic
    @Column(name = "article_code", nullable = true, length = 50)
    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    @Basic
    @Column(name = "article_descript", nullable = true, length = 250)
    public String getArticleDescript() {
        return articleDescript;
    }

    public void setArticleDescript(String articleDescript) {
        this.articleDescript = articleDescript;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmInventoryPos that = (WmInventoryPos) o;

        if (inventoryId != null ? !inventoryId.equals(that.inventoryId) : that.inventoryId != null) return false;
        if (inventoryPosId != null ? !inventoryPosId.equals(that.inventoryPosId) : that.inventoryPosId != null)
            return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (binId != null ? !binId.equals(that.binId) : that.binId != null) return false;
        if (unitId != null ? !unitId.equals(that.unitId) : that.unitId != null) return false;
        if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null) return false;
        if (skuId != null ? !skuId.equals(that.skuId) : that.skuId != null) return false;
        if (qualityStateId != null ? !qualityStateId.equals(that.qualityStateId) : that.qualityStateId != null)
            return false;
        if (lotId != null ? !lotId.equals(that.lotId) : that.lotId != null) return false;
        if (quantityBefore != null ? !quantityBefore.equals(that.quantityBefore) : that.quantityBefore != null)
            return false;
        if (quantityAfter != null ? !quantityAfter.equals(that.quantityAfter) : that.quantityAfter != null)
            return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (executorId != null ? !executorId.equals(that.executorId) : that.executorId != null) return false;
        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (quantId != null ? !quantId.equals(that.quantId) : that.quantId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = inventoryId != null ? inventoryId.hashCode() : 0;
        result = 31 * result + (inventoryPosId != null ? inventoryPosId.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (binId != null ? binId.hashCode() : 0);
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (qualityStateId != null ? qualityStateId.hashCode() : 0);
        result = 31 * result + (lotId != null ? lotId.hashCode() : 0);
        result = 31 * result + (quantityBefore != null ? quantityBefore.hashCode() : 0);
        result = 31 * result + (quantityAfter != null ? quantityAfter.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (executorId != null ? executorId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (quantId != null ? quantId.hashCode() : 0);
        return result;
    }
}
