package ru.defo.model.views;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by syn-wms on 12.08.2017.
 */
@Entity
public class Vunitinfo {
    private String unitCode;
    private String binCode;
    private String areaCode;
    private Long rackNo;
    private Long levelNo;
    private String skuName;
    private String qstateName;
    private Long quantId;
    private Long articleId;
    private Long skuId;
    private Long unitId;
    private Long qualityStateId;
    private Long quantity;
    private Long adviceId;
    private Long advicePosId;
    private Boolean needCheck;
    private Timestamp createDate;
    private Long createUser;
    private String surname;
    private String firstName;
    private String adviceCode;
    private Timestamp expectedDate;
    private String articleCode;
    private String articleName;
    private String vendorGroupName;

    @Id
    @Column(name = "quant_id", nullable = true)
    public Long getQuantId() {
        return quantId;
    }

    public void setQuantId(Long quantId) {
        this.quantId = quantId;
    }

    @Basic
    @Column(name = "unit_code", nullable = true, length = 50)
    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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
    @Column(name = "area_code", nullable = true, length = 50)
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Basic
    @Column(name = "rack_no", nullable = true)
    public Long getRackNo() {
        return rackNo;
    }

    public void setRackNo(Long rackNo) {
        this.rackNo = rackNo;
    }

    @Basic
    @Column(name = "level_no", nullable = true)
    public Long getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(Long levelNo) {
        this.levelNo = levelNo;
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
    @Column(name = "qstate_name", nullable = true, length = 250)
    public String getQstateName() {
        return qstateName;
    }

    public void setQstateName(String qstateName) {
        this.qstateName = qstateName;
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
    @Column(name = "unit_id", nullable = true)
    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
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
    public Boolean getNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(Boolean needCheck) {
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
    @Column(name = "advice_code", nullable = true, length = 50)
    public String getAdviceCode() {
        return adviceCode;
    }

    public void setAdviceCode(String adviceCode) {
        this.adviceCode = adviceCode;
    }

    @Basic
    @Column(name = "expected_date", nullable = true)
    public Timestamp getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Timestamp expectedDate) {
        this.expectedDate = expectedDate;
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
    @Column(name = "vendor_group_name", nullable = true, length = 250)
    public String getVendorGroupName() {
        return this.vendorGroupName;
    }

    public void setVendorGroupName(String vendorGroupName) {
        this.vendorGroupName = vendorGroupName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vunitinfo vunitinfo = (Vunitinfo) o;

        if (unitCode != null ? !unitCode.equals(vunitinfo.unitCode) : vunitinfo.unitCode != null) return false;
        if (binCode != null ? !binCode.equals(vunitinfo.binCode) : vunitinfo.binCode != null) return false;
        if (areaCode != null ? !areaCode.equals(vunitinfo.areaCode) : vunitinfo.areaCode != null) return false;
        if (rackNo != null ? !rackNo.equals(vunitinfo.rackNo) : vunitinfo.rackNo != null) return false;
        if (levelNo != null ? !levelNo.equals(vunitinfo.levelNo) : vunitinfo.levelNo != null) return false;
        if (skuName != null ? !skuName.equals(vunitinfo.skuName) : vunitinfo.skuName != null) return false;
        if (qstateName != null ? !qstateName.equals(vunitinfo.qstateName) : vunitinfo.qstateName != null)
            return false;
        if (quantId != null ? !quantId.equals(vunitinfo.quantId) : vunitinfo.quantId != null) return false;
        if (articleId != null ? !articleId.equals(vunitinfo.articleId) : vunitinfo.articleId != null) return false;
        if (skuId != null ? !skuId.equals(vunitinfo.skuId) : vunitinfo.skuId != null) return false;
        if (unitId != null ? !unitId.equals(vunitinfo.unitId) : vunitinfo.unitId != null) return false;
        if (qualityStateId != null ? !qualityStateId.equals(vunitinfo.qualityStateId) : vunitinfo.qualityStateId != null)
            return false;
        if (quantity != null ? !quantity.equals(vunitinfo.quantity) : vunitinfo.quantity != null) return false;
        if (adviceId != null ? !adviceId.equals(vunitinfo.adviceId) : vunitinfo.adviceId != null) return false;
        if (advicePosId != null ? !advicePosId.equals(vunitinfo.advicePosId) : vunitinfo.advicePosId != null)
            return false;
        if (needCheck != null ? !needCheck.equals(vunitinfo.needCheck) : vunitinfo.needCheck != null) return false;
        if (createDate != null ? !createDate.equals(vunitinfo.createDate) : vunitinfo.createDate != null) return false;
        if (createUser != null ? !createUser.equals(vunitinfo.createUser) : vunitinfo.createUser != null) return false;
        if (surname != null ? !surname.equals(vunitinfo.surname) : vunitinfo.surname != null) return false;
        if (firstName != null ? !firstName.equals(vunitinfo.firstName) : vunitinfo.firstName != null) return false;
        if (adviceCode != null ? !adviceCode.equals(vunitinfo.adviceCode) : vunitinfo.adviceCode != null) return false;
        if (expectedDate != null ? !expectedDate.equals(vunitinfo.expectedDate) : vunitinfo.expectedDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = unitCode != null ? unitCode.hashCode() : 0;
        result = 31 * result + (binCode != null ? binCode.hashCode() : 0);
        result = 31 * result + (areaCode != null ? areaCode.hashCode() : 0);
        result = 31 * result + (rackNo != null ? rackNo.hashCode() : 0);
        result = 31 * result + (levelNo != null ? levelNo.hashCode() : 0);
        result = 31 * result + (skuName != null ? skuName.hashCode() : 0);
        result = 31 * result + (qstateName != null ? qstateName.hashCode() : 0);
        result = 31 * result + (quantId != null ? quantId.hashCode() : 0);
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        result = 31 * result + (qualityStateId != null ? qualityStateId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (adviceId != null ? adviceId.hashCode() : 0);
        result = 31 * result + (advicePosId != null ? advicePosId.hashCode() : 0);
        result = 31 * result + (needCheck != null ? needCheck.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (adviceCode != null ? adviceCode.hashCode() : 0);
        result = 31 * result + (expectedDate != null ? expectedDate.hashCode() : 0);
        return result;
    }
}
