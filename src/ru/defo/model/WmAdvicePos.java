package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Created by syn-wms on 21.03.2017.
 */
@Entity
@Table(name = "wm_advice_pos", schema = "public", catalog = "wms")
@IdClass(WmAdvicePosPK.class)
public class WmAdvicePos {
    private Long adviceId;
    private Long advicePosId;
    private Long articleId;
    private Long skuId;
    private Long expectQuantity;
    private Long factQuantity;
    private Long qualityStateId;
    private Long lotId;
    private Long statusId;
    private Long createUser;
    private Timestamp createDate;
    private Long modUser;
    private Timestamp modDate;
    private Long errorId;
    private String errorComment;

    @Id
    @Column(name = "advice_id", nullable = false)
    public Long getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(Long adviceId) {
        this.adviceId = adviceId;
    }

    @Id
    @Column(name = "advice_pos_id", nullable = false)
    public Long getAdvicePosId() {
        return advicePosId;
    }

    public void setAdvicePosId(Long advicePosId) {
        this.advicePosId = advicePosId;
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
    @Column(name = "expect_quantity", nullable = true)
    public Long getExpectQuantity() {
        return expectQuantity;
    }

    public void setExpectQuantity(Long expectQuantity) {
        this.expectQuantity = expectQuantity;
    }

    @Basic
    @Column(name = "fact_quantity", nullable = true)
    public Long getFactQuantity() {
        return factQuantity;
    }

    public void setFactQuantity(Long factQuantity) {
        this.factQuantity = factQuantity;
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
    @Column(name = "status_id", nullable = true)
    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
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

    @Basic
    @Column(name = "mod_date", nullable = true)
    public Timestamp getModDate() {
        return modDate;
    }

    public void setModDate(Timestamp modDate) {
        this.modDate = modDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmAdvicePos that = (WmAdvicePos) o;

        if (adviceId != null ? !adviceId.equals(that.adviceId) : that.adviceId != null) return false;
        if (advicePosId != null ? !advicePosId.equals(that.advicePosId) : that.advicePosId != null) return false;
        if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null) return false;
        if (skuId != null ? !skuId.equals(that.skuId) : that.skuId != null) return false;
        if (expectQuantity != null ? !expectQuantity.equals(that.expectQuantity) : that.expectQuantity != null)
            return false;
        if (factQuantity != null ? !factQuantity.equals(that.factQuantity) : that.factQuantity != null) return false;
        if (qualityStateId != null ? !qualityStateId.equals(that.qualityStateId) : that.qualityStateId != null)
            return false;
        if (lotId != null ? !lotId.equals(that.lotId) : that.lotId != null) return false;
        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (modUser != null ? !modUser.equals(that.modUser) : that.modUser != null) return false;
        if (modDate != null ? !modDate.equals(that.modDate) : that.modDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adviceId != null ? adviceId.hashCode() : 0;
        result = 31 * result + (advicePosId != null ? advicePosId.hashCode() : 0);
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (expectQuantity != null ? expectQuantity.hashCode() : 0);
        result = 31 * result + (factQuantity != null ? factQuantity.hashCode() : 0);
        result = 31 * result + (qualityStateId != null ? qualityStateId.hashCode() : 0);
        result = 31 * result + (lotId != null ? lotId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (modUser != null ? modUser.hashCode() : 0);
        result = 31 * result + (modDate != null ? modDate.hashCode() : 0);
        return result;
    }
}
