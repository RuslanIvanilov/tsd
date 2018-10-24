package ru.defo.model.views;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by syn-wms on 11.08.2017.
 */
@Entity
public class Varticleqtyr {
    private Long articleId;
    private String articleCode;
    private String articleName;
    private Long qtySum;
    private Long hostQuantity;
    private Long factQuantity;

    private Long dWmsFact;
    private Long dWmsHost;
    private Long dFactHost;

    @Id
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
    @Column(name = "qty_sum", nullable = true)
    public Long getQtySum() {
        return qtySum;
    }

    public void setQtySum(Long qtySum) {
        this.qtySum = qtySum;
    }

    @Basic
    @Column(name = "host_quantity", nullable = true)
    public Long getHostQuantity() {
        return hostQuantity;
    }

    public void setHostQuantity(Long hostQuantity) {
        this.hostQuantity = hostQuantity;
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
    @Column(name = "d_wms_fact", nullable = true)
	public Long getdWmsFact() {
		return dWmsFact;
	}

	public void setdWmsFact(Long dWmsFact) {
		this.dWmsFact = dWmsFact;
	}

	@Basic
    @Column(name = "d_wms_host", nullable = true)
	public Long getdWmsHost() {
		return dWmsHost;
	}

	public void setdWmsHost(Long dWmsHost) {
		this.dWmsHost = dWmsHost;
	}

	@Basic
    @Column(name = "d_fact_host", nullable = true)
	public Long getdFactHost() {
		return dFactHost;
	}

	public void setdFactHost(Long dFactHost) {
		this.dFactHost = dFactHost;
	}



	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Varticleqtyr that = (Varticleqtyr) o;

        if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null) return false;
        if (articleCode != null ? !articleCode.equals(that.articleCode) : that.articleCode != null) return false;
        if (articleName != null ? !articleName.equals(that.articleName) : that.articleName != null) return false;
        if (qtySum != null ? !qtySum.equals(that.qtySum) : that.qtySum != null) return false;
        if (hostQuantity != null ? !hostQuantity.equals(that.hostQuantity) : that.hostQuantity != null) return false;
        if (factQuantity != null ? !factQuantity.equals(that.factQuantity) : that.factQuantity != null) return false;
        if (dWmsFact != null ? !dWmsFact.equals(that.dWmsFact) : that.dWmsFact != null) return false;
        if (dWmsHost != null ? !dWmsHost.equals(that.dWmsHost) : that.dWmsHost != null) return false;
        if (dFactHost != null ? !dFactHost.equals(that.dFactHost) : that.dFactHost != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = articleId != null ? articleId.hashCode() : 0;
        result = 31 * result + (articleCode != null ? articleCode.hashCode() : 0);
        result = 31 * result + (articleName != null ? articleName.hashCode() : 0);
        result = 31 * result + (qtySum != null ? qtySum.hashCode() : 0);
        result = 31 * result + (hostQuantity != null ? hostQuantity.hashCode() : 0);

        result = 31 * result + (factQuantity != null ? factQuantity.hashCode() : 0);
        result = 31 * result + (dWmsFact != null ? dWmsFact.hashCode() : 0);
        result = 31 * result + (dWmsHost != null ? dWmsHost.hashCode() : 0);
        result = 31 * result + (dFactHost != null ? dFactHost.hashCode() : 0);

        return result;
    }
}
