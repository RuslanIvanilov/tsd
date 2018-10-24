package ru.defo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 27.03.2017.
 */
@Entity
@Table(name = "wm_barcode", schema = "public", catalog = "wms")
public class WmBarcode {
    private String barCode;
    private Long skuId;
    private Long articleId;
    private Boolean blocked;

    @Id
    @Column(name = "bar_code", nullable = false, length = 50)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
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
    @Column(name = "article_id", nullable = true)
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Basic
    @Column(name = "blocked", nullable = true)
    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmBarcode wmBarcode = (WmBarcode) o;

        if (barCode != null ? !barCode.equals(wmBarcode.barCode) : wmBarcode.barCode != null) return false;
        if (skuId != null ? !skuId.equals(wmBarcode.skuId) : wmBarcode.skuId != null) return false;
        if (articleId != null ? !articleId.equals(wmBarcode.articleId) : wmBarcode.articleId != null) return false;
        if (blocked != null ? !blocked.equals(wmBarcode.blocked) : wmBarcode.blocked != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = barCode != null ? barCode.hashCode() : 0;
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (blocked != null ? blocked.hashCode() : 0);
        return result;
    }
}
