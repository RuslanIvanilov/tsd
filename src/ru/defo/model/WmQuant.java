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
@Table(name = "wm_quant", schema = "public", catalog = "wms")
public class WmQuant {
    private Long quantId;
    private Long articleId;
    private Long skuId;
    private Long lotId;
    private Long qualityStateId;
    private String boxBarcode;
    private Long quantity;
    private Long clientId;
    private Long unitId;
    private Long adviceId;
    private Long advicePosId;
    private boolean needCheck;
    private Timestamp createDate;
    private Long createUser;

    @Id
    @Column(name = "quant_id", nullable = false)
    public Long getQuantId() {
        return quantId;
    }

    public void setQuantId(Long quantId) {
        this.quantId = quantId;
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
    @Column(name = "lot_id", nullable = true)
    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
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
    @Column(name = "box_barcode", nullable = true, length = 50)
    public String getBoxBarcode() {
        return boxBarcode;
    }

    public void setBoxBarcode(String boxBarcode) {
        this.boxBarcode = boxBarcode;
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
    @Column(name = "client_id", nullable = true)
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
    @Column(name = "advice_id", nullable = true)
    public Long getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(Long adviceId) {
        this.adviceId = adviceId;
    }

    @Basic
    @Column(name = "advice_pos_id", nullable = true)
    public Long getAdvicePosId() {
        return advicePosId;
    }

    public void setAdvicePosId(Long advicePosId) {
        this.advicePosId = advicePosId;
    }

    @Basic
    @Column(name = "need_check", nullable = true)
    public boolean getNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(boolean needCheck) {
        this.needCheck = needCheck;
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
    @Column(name = "create_user", nullable = true)
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmQuant wmQuant = (WmQuant) o;

        if (quantId != null ? !quantId.equals(wmQuant.quantId) : wmQuant.quantId != null) return false;
        if (articleId != null ? !articleId.equals(wmQuant.articleId) : wmQuant.articleId != null) return false;
        if (skuId != null ? !skuId.equals(wmQuant.skuId) : wmQuant.skuId != null) return false;
        if (lotId != null ? !lotId.equals(wmQuant.lotId) : wmQuant.lotId != null) return false;
        if (qualityStateId != null ? !qualityStateId.equals(wmQuant.qualityStateId) : wmQuant.qualityStateId != null)
            return false;
        if (boxBarcode != null ? !boxBarcode.equals(wmQuant.boxBarcode) : wmQuant.boxBarcode != null) return false;
        if (quantity != null ? !quantity.equals(wmQuant.quantity) : wmQuant.quantity != null) return false;
        if (clientId != null ? !clientId.equals(wmQuant.clientId) : wmQuant.clientId != null) return false;
        if (unitId != null ? !unitId.equals(wmQuant.unitId) : wmQuant.unitId != null) return false;
        if (adviceId != null ? !adviceId.equals(wmQuant.adviceId) : wmQuant.adviceId != null) return false;
        if (advicePosId != null ? !advicePosId.equals(wmQuant.advicePosId) : wmQuant.advicePosId != null) return false;
        if (needCheck != wmQuant.needCheck) return false;
        if (createDate != null ? !createDate.equals(wmQuant.createDate) : wmQuant.createDate != null) return false;
        if (createUser != null ? !createUser.equals(wmQuant.createUser) : wmQuant.createUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = quantId != null ? quantId.hashCode() : 0;
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (lotId != null ? lotId.hashCode() : 0);
        result = 31 * result + (qualityStateId != null ? qualityStateId.hashCode() : 0);
        result = 31 * result + (boxBarcode != null ? boxBarcode.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        result = 31 * result + (adviceId != null ? adviceId.hashCode() : 0);
        result = 31 * result + (advicePosId != null ? advicePosId.hashCode() : 0);
        result = 31 * result + (needCheck != false ? 1 : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        return result;
    }
}
