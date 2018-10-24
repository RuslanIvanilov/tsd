package ru.defo.model.views;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Created by syn-wms on 29.08.2017.
 */
@Entity
@IdClass(VpreorderposPK.class)
public class Vpreorderpos {
    private Long orderId;
    private Long orderPosId;
    private Long articleId;
    private String articleCode;
    private String articleName;
    private Long skuId;
    private String skuName;
    private Long expectQuantity;
    private Long factQuantity;
    private Long qualityStateId;
    private Long statusId;
    private String statusName;
    private Long errorId;
    private String errorComment;
    private Long createUser;
    private String firstName;
    private String surname;
    private Timestamp createDate;
    private Long wmPosOrderLink;

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
    @Column(name = "article_code", nullable = true, length = 50)
    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    @Basic
    @Column(name = "article_name", nullable = true, length = 250)
    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
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
    @Column(name = "sku_name", nullable = true, length = 250)
    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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
    @Column(name = "status_id", nullable = true)
    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "status_name", nullable = true, length = 250)
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
    @Column(name = "create_user", nullable = true)
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
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
    @Column(name = "surname", nullable = true, length = 250)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "create_date", nullable = true)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Id
    @Column(name = "wm_pos_order_link", nullable = false)
    public Long getWmPosOrderLink() {
        return wmPosOrderLink;
    }

    public void setWmPosOrderLink(Long wmPosOrderLink) {
        this.wmPosOrderLink = wmPosOrderLink;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vpreorderpos vorderpos = (Vpreorderpos) o;

        if (orderId != null ? !orderId.equals(vorderpos.orderId) : vorderpos.orderId != null) return false;
        if (orderPosId != null ? !orderPosId.equals(vorderpos.orderPosId) : vorderpos.orderPosId != null) return false;
        if (articleId != null ? !articleId.equals(vorderpos.articleId) : vorderpos.articleId != null) return false;
        if (articleCode != null ? !articleCode.equals(vorderpos.articleCode) : vorderpos.articleCode != null)
            return false;
        if (articleName != null ? !articleName.equals(vorderpos.articleName) : vorderpos.articleName != null)
            return false;
        if (skuId != null ? !skuId.equals(vorderpos.skuId) : vorderpos.skuId != null) return false;
        if (skuName != null ? !skuName.equals(vorderpos.skuName) : vorderpos.skuName != null) return false;
        if (expectQuantity != null ? !expectQuantity.equals(vorderpos.expectQuantity) : vorderpos.expectQuantity != null)
            return false;
        if (factQuantity != null ? !factQuantity.equals(vorderpos.factQuantity) : vorderpos.factQuantity != null)
            return false;
        if (qualityStateId != null ? !qualityStateId.equals(vorderpos.qualityStateId) : vorderpos.qualityStateId != null)
            return false;
        if (statusId != null ? !statusId.equals(vorderpos.statusId) : vorderpos.statusId != null) return false;
        if (statusName != null ? !statusName.equals(vorderpos.statusName) : vorderpos.statusName != null) return false;
        if (errorId != null ? !errorId.equals(vorderpos.errorId) : vorderpos.errorId != null) return false;
        if (errorComment != null ? !errorComment.equals(vorderpos.errorComment) : vorderpos.errorComment != null)
            return false;
        if (createUser != null ? !createUser.equals(vorderpos.createUser) : vorderpos.createUser != null) return false;
        if (firstName != null ? !firstName.equals(vorderpos.firstName) : vorderpos.firstName != null) return false;
        if (surname != null ? !surname.equals(vorderpos.surname) : vorderpos.surname != null) return false;
        if (createDate != null ? !createDate.equals(vorderpos.createDate) : vorderpos.createDate != null) return false;
        if (wmPosOrderLink != null ? !wmPosOrderLink.equals(vorderpos.wmPosOrderLink) : vorderpos.wmPosOrderLink != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (orderPosId != null ? orderPosId.hashCode() : 0);
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (articleCode != null ? articleCode.hashCode() : 0);
        result = 31 * result + (articleName != null ? articleName.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (skuName != null ? skuName.hashCode() : 0);
        result = 31 * result + (expectQuantity != null ? expectQuantity.hashCode() : 0);
        result = 31 * result + (factQuantity != null ? factQuantity.hashCode() : 0);
        result = 31 * result + (qualityStateId != null ? qualityStateId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (statusName != null ? statusName.hashCode() : 0);
        result = 31 * result + (errorId != null ? errorId.hashCode() : 0);
        result = 31 * result + (errorComment != null ? errorComment.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (wmPosOrderLink != null ? wmPosOrderLink.hashCode() : 0);
        return result;
    }
}
