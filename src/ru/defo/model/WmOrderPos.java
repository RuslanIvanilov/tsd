package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by syn-wms on 21.03.2017.
 */
@Entity
@Table(name = "wm_order_pos", schema = "public", catalog = "wms")
@IdClass(WmOrderPosPK.class)
public class WmOrderPos {
    private Long orderId;
    private Long orderPosId;
    private Long articleId;
    private Long skuId;
    private Long expectQuantity;
    private Long factQuantity;
    private Long ctrlQuantity;
    private Long qualityStateId;
    private Long lotId;
    private Long statusId;
    private Long createUser;
    private Timestamp createDate;
    private Long errorId;
    private String errorComment;
    private long sequence;

    @Id
    @Column(name = "order_id", nullable = false)
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Id
    @Column(name = "order_pos_id", nullable = false)
    public Long getOrderPosId() {
        return orderPosId;
    }


    public void setOrderPosId(Long orderPosId) {
        this.orderPosId = orderPosId;
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
    @Column(name="control_quantity", nullable = true)
    public Long getCtrlQuantity(){
    	return ctrlQuantity;
    }

    public void setCtrlQuantity(Long ctrlQuantity){
    	this.ctrlQuantity = ctrlQuantity;
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
/***/
    @Transient
    public long getSequence() {
		return sequence;
	}

    @Transient
	public void setSequence(long sequence) {
		this.sequence = sequence;
	}
/***/

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmOrderPos that = (WmOrderPos) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (orderPosId != null ? !orderPosId.equals(that.orderPosId) : that.orderPosId != null) return false;
        if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null) return false;
        if (skuId != null ? !skuId.equals(that.skuId) : that.skuId != null) return false;
        if (expectQuantity != null ? !expectQuantity.equals(that.expectQuantity) : that.expectQuantity != null)
            return false;
        if (factQuantity != null ? !factQuantity.equals(that.factQuantity) : that.factQuantity != null) return false;
        if (qualityStateId != null ? !qualityStateId.equals(that.qualityStateId) : that.qualityStateId != null)
            return false;
        if (lotId != null ? !lotId.equals(that.lotId) : that.lotId != null) return false;
        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (orderPosId != null ? orderPosId.hashCode() : 0);
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (expectQuantity != null ? expectQuantity.hashCode() : 0);
        result = 31 * result + (factQuantity != null ? factQuantity.hashCode() : 0);
        result = 31 * result + (qualityStateId != null ? qualityStateId.hashCode() : 0);
        result = 31 * result + (lotId != null ? lotId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        return result;
    }
}
