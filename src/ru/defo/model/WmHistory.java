package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by syn-wms on 21.03.2017.
 */
@Entity
@Table(name = "wm_history", schema = "public", catalog = "wms")
public class WmHistory{
    private Long historyId;
    private Long documentId;
    private Long documentTypeId;
    private Long quantId;
    private Long clientId;
    private Long binId;
    private Long unitId;
    private Long articleId;
    private Long skuId;
    private Long qualityStateId;
    private Long lotId;
    private Long quantity;
    private Long createUser;
    private Timestamp createDate;
    private String comment;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_history_id")
    @SequenceGenerator(name = "seq_history_id", sequenceName = "seq_history_id", allocationSize=1)
    @Column(name = "history_id", nullable = false)
    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    @Basic
    @Column(name = "document_id", nullable = true)
    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    @Basic
    @Column(name = "document_type_id", nullable = true)
    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
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
    @Column(name = "comment", nullable = true, length = 250)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmHistory wmHistory = (WmHistory) o;

        if (historyId != null ? !historyId.equals(wmHistory.historyId) : wmHistory.historyId != null) return false;
        if (documentId != null ? !documentId.equals(wmHistory.documentId) : wmHistory.documentId != null) return false;
        if (documentTypeId != null ? !documentTypeId.equals(wmHistory.documentTypeId) : wmHistory.documentTypeId != null)
            return false;
        if (quantId != null ? !quantId.equals(wmHistory.quantId) : wmHistory.quantId != null) return false;
        if (clientId != null ? !clientId.equals(wmHistory.clientId) : wmHistory.clientId != null) return false;
        if (binId != null ? !binId.equals(wmHistory.binId) : wmHistory.binId != null) return false;
        if (unitId != null ? !unitId.equals(wmHistory.unitId) : wmHistory.unitId != null) return false;
        if (articleId != null ? !articleId.equals(wmHistory.articleId) : wmHistory.articleId != null) return false;
        if (skuId != null ? !skuId.equals(wmHistory.skuId) : wmHistory.skuId != null) return false;
        if (qualityStateId != null ? !qualityStateId.equals(wmHistory.qualityStateId) : wmHistory.qualityStateId != null)
            return false;
        if (lotId != null ? !lotId.equals(wmHistory.lotId) : wmHistory.lotId != null) return false;
        if (quantity != null ? !quantity.equals(wmHistory.quantity) : wmHistory.quantity != null) return false;
        if (createUser != null ? !createUser.equals(wmHistory.createUser) : wmHistory.createUser != null) return false;
        if (createDate != null ? !createDate.equals(wmHistory.createDate) : wmHistory.createDate != null) return false;
        if (comment != null ? !comment.equals(wmHistory.comment) : wmHistory.comment != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = historyId != null ? historyId.hashCode() : 0;
        result = 31 * result + (documentId != null ? documentId.hashCode() : 0);
        result = 31 * result + (documentTypeId != null ? documentTypeId.hashCode() : 0);
        result = 31 * result + (quantId != null ? quantId.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (binId != null ? binId.hashCode() : 0);
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (qualityStateId != null ? qualityStateId.hashCode() : 0);
        result = 31 * result + (lotId != null ? lotId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

}
