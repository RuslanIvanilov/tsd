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
@Table(name = "wm_article_type", schema = "public", catalog = "wms")
public class WmArticleType {
    private Long articleTypeId;
    private String description;

    @Id
    @Column(name = "article_type_id", nullable = false)
    public Long getArticleTypeId() {
        return articleTypeId;
    }

    public void setArticleTypeId(Long articleTypeId) {
        this.articleTypeId = articleTypeId;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 250)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmArticleType that = (WmArticleType) o;

        if (articleTypeId != null ? !articleTypeId.equals(that.articleTypeId) : that.articleTypeId != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = articleTypeId != null ? articleTypeId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
