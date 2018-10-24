package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 12.04.2018.
 */
@Entity
@Table(name = "wm_income", schema = "public", catalog = "wms")
public class WmIncome {
    private Long incomeId;
    private Long binId;
    private Long unitId;
    private Long articleId;
    private Long skuId;
    private Long qualityStateId;
    private Long lotId;
    private Long quantity;
    private Long createUser;
    private Timestamp createDate;
    private String boxBarcode;
    private Long adviceId;
    private Long advicePosId;
    private Long statusId;

    @Id
    @Column(name = "income_id", nullable = false)
    public Long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Long incomeId) {
        this.incomeId = incomeId;
    }

    @Basic
    @Column(name = "bin_id", nullable = false)
    public Long getBinId() {
        return binId;
    }

    public void setBinId(Long binId) {
        this.binId = binId;
    }

    @Basic
    @Column(name = "unit_id", nullable = false)
    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    @Basic
    @Column(name = "article_id", nullable = false)
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Basic
    @Column(name = "sku_id", nullable = false)
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Basic
    @Column(name = "quality_state_id", nullable = false)
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
    @Column(name = "quantity", nullable = false)
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "create_user", nullable = false)
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "create_date", nullable = false)
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
    @Column(name = "status_id", nullable = true)
    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmIncome wmIncome = (WmIncome) o;

        if (incomeId != null ? !incomeId.equals(wmIncome.incomeId) : wmIncome.incomeId != null) return false;
        if (binId != null ? !binId.equals(wmIncome.binId) : wmIncome.binId != null) return false;
        if (unitId != null ? !unitId.equals(wmIncome.unitId) : wmIncome.unitId != null) return false;
        if (articleId != null ? !articleId.equals(wmIncome.articleId) : wmIncome.articleId != null) return false;
        if (skuId != null ? !skuId.equals(wmIncome.skuId) : wmIncome.skuId != null) return false;
        if (qualityStateId != null ? !qualityStateId.equals(wmIncome.qualityStateId) : wmIncome.qualityStateId != null)
            return false;
        if (lotId != null ? !lotId.equals(wmIncome.lotId) : wmIncome.lotId != null) return false;
        if (quantity != null ? !quantity.equals(wmIncome.quantity) : wmIncome.quantity != null) return false;
        if (createUser != null ? !createUser.equals(wmIncome.createUser) : wmIncome.createUser != null) return false;
        if (createDate != null ? !createDate.equals(wmIncome.createDate) : wmIncome.createDate != null) return false;
        if (boxBarcode != null ? !boxBarcode.equals(wmIncome.boxBarcode) : wmIncome.boxBarcode != null) return false;
        if (adviceId != null ? !adviceId.equals(wmIncome.adviceId) : wmIncome.adviceId != null) return false;
        if (advicePosId != null ? !advicePosId.equals(wmIncome.advicePosId) : wmIncome.advicePosId != null)
            return false;
        if (statusId != null ? !statusId.equals(wmIncome.statusId) : wmIncome.statusId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = incomeId != null ? incomeId.hashCode() : 0;
        result = 31 * result + (binId != null ? binId.hashCode() : 0);
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (qualityStateId != null ? qualityStateId.hashCode() : 0);
        result = 31 * result + (lotId != null ? lotId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (boxBarcode != null ? boxBarcode.hashCode() : 0);
        result = 31 * result + (adviceId != null ? adviceId.hashCode() : 0);
        result = 31 * result + (advicePosId != null ? advicePosId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        return result;
    }
}
