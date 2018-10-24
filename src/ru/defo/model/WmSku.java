package ru.defo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 21.03.2017.
 */
@Entity
@Table(name = "wm_sku", schema = "public", catalog = "wms")
public class WmSku {
    private Long skuId;
    private String description;
    private Long weight;
    private Long heigh;
    private Long width;
    private Long length;
    private Boolean isBase;
    private Long skuCrushId;
    private Long articleId;

    @Id
    @Column(name = "sku_id", nullable = false)
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 250)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "weight", nullable = true)
    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "heigh", nullable = true)
    public Long getHeigh() {
        return heigh;
    }

    public void setHeigh(Long heigh) {
        this.heigh = heigh;
    }

    @Basic
    @Column(name = "width", nullable = true)
    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    @Basic
    @Column(name = "length", nullable = true)
    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    @Basic
    @Column(name = "is_base", nullable = true)
    public Boolean getIsBase() {
        return isBase;
    }

    public void setIsBase(Boolean isBase) {
        this.isBase = isBase;
    }

    @Basic
    @Column(name = "sku_crush_id", nullable = true)
    public Long getSkuCrushId() {
        return skuCrushId;
    }

    public void setSkuCrushId(Long skuCrushId) {
        this.skuCrushId = skuCrushId;
    }

    @Basic
    @Column(name = "article_id", nullable = true)
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmSku wmSku = (WmSku) o;

        if (skuId != null ? !skuId.equals(wmSku.skuId) : wmSku.skuId != null) return false;
        if (description != null ? !description.equals(wmSku.description) : wmSku.description != null) return false;
        if (weight != null ? !weight.equals(wmSku.weight) : wmSku.weight != null) return false;
        if (heigh != null ? !heigh.equals(wmSku.heigh) : wmSku.heigh != null) return false;
        if (width != null ? !width.equals(wmSku.width) : wmSku.width != null) return false;
        if (length != null ? !length.equals(wmSku.length) : wmSku.length != null) return false;
        if (isBase != null ? !isBase.equals(wmSku.isBase) : wmSku.isBase != null) return false;
        if (skuCrushId != null ? !skuCrushId.equals(wmSku.skuCrushId) : wmSku.skuCrushId != null) return false;
        if (articleId != null ? !articleId.equals(wmSku.articleId) : wmSku.articleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = skuId != null ? skuId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (heigh != null ? heigh.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (isBase != null ? isBase.hashCode() : 0);
        result = 31 * result + (skuCrushId != null ? skuCrushId.hashCode() : 0);
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        return result;
    }
}
