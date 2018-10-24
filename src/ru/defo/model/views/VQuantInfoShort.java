package ru.defo.model.views;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Created by syn-wms on 25.03.2017.
 */
@NamedQueries({ @NamedQuery(name = "@SQL_GET_QUANT_INFO", query = "from VQuantInfoShort where q.quant_id = :quantId") })


@Entity
public class VQuantInfoShort{
    private Long quantId;
    private String articleCode;
    private String artName;
    private String skuName;
    private Long quantity;
    private String qualityName;
    private String unitCode;
    private String binCode;

    @Id
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
    @Column(name = "art_name", nullable = true, length = 250)
    public String getArtName() {
        return artName;
    }

    public void setArtName(String artName) {
        this.artName = artName;
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
    @Column(name = "quantity", nullable = true)
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "quality_name", nullable = true, length = 250)
    public String getQualityName() {
        return qualityName;
    }

    public void setQualityName(String qualityName) {
        this.qualityName = qualityName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VQuantInfoShort that = (VQuantInfoShort) o;

        if (quantId != null ? !quantId.equals(that.quantId) : that.quantId != null) return false;
        if (articleCode != null ? !articleCode.equals(that.articleCode) : that.articleCode != null) return false;
        if (artName != null ? !artName.equals(that.artName) : that.artName != null) return false;
        if (skuName != null ? !skuName.equals(that.skuName) : that.skuName != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (qualityName != null ? !qualityName.equals(that.qualityName) : that.qualityName != null) return false;
        if (unitCode != null ? !unitCode.equals(that.unitCode) : that.unitCode != null) return false;
        if (binCode != null ? !binCode.equals(that.binCode) : that.binCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = quantId != null ? quantId.hashCode() : 0;
        result = 31 * result + (articleCode != null ? articleCode.hashCode() : 0);
        result = 31 * result + (artName != null ? artName.hashCode() : 0);
        result = 31 * result + (skuName != null ? skuName.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (qualityName != null ? qualityName.hashCode() : 0);
        result = 31 * result + (unitCode != null ? unitCode.hashCode() : 0);
        result = 31 * result + (binCode != null ? binCode.hashCode() : 0);
        return result;
    }


}
