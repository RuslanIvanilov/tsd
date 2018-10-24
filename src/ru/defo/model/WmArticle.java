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
@Table(name = "wm_article", schema = "public", catalog = "wms")
public class WmArticle {
    private Long articleId;
    private String articleCode;
    private String description;
    private String descriptionClient;
    private String articleClientCode;
    private Long clientId;
    private Boolean blocked;
    private String abcCode;
    private Long articleTypeId;
    private Boolean lotNeed;
    private Long articleKitId;

    @Id
    @Column(name = "article_id", nullable = false)
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
    @Column(name = "description", nullable = true, length = 250)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "description_client", nullable = true, length = 250)
    public String getDescriptionClient() {
        return descriptionClient;
    }

    public void setDescriptionClient(String descriptionClient) {
        this.descriptionClient = descriptionClient;
    }

    @Basic
    @Column(name = "article_client_code", nullable = true, length = 50)
    public String getArticleClientCode() {
        return articleClientCode;
    }

    public void setArticleClientCode(String articleClientCode) {
        this.articleClientCode = articleClientCode;
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
    @Column(name = "blocked", nullable = true)
    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    @Basic
    @Column(name = "abc_code", nullable = true, length = -1)
    public String getAbcCode() {
        return abcCode;
    }

    public void setAbcCode(String abcCode) {
        this.abcCode = abcCode;
    }

    @Basic
    @Column(name = "article_type_id", nullable = true)
    public Long getArticleTypeId() {
        return articleTypeId;
    }

    public void setArticleTypeId(Long articleTypeId) {
        this.articleTypeId = articleTypeId;
    }

    @Basic
    @Column(name = "lot_need", nullable = true)
    public Boolean getLotNeed() {
        return lotNeed;
    }

    public void setLotNeed(Boolean lotNeed) {
        this.lotNeed = lotNeed;
    }

    @Basic
    @Column(name = "article_kit_id", nullable = true)
    public Long getArticleKitId() {
        return articleKitId;
    }

    public void setArticleKitId(Long articleKitId) {
        this.articleKitId = articleKitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmArticle wmArticle = (WmArticle) o;

        if (articleId != null ? !articleId.equals(wmArticle.articleId) : wmArticle.articleId != null) return false;
        if (articleCode != null ? !articleCode.equals(wmArticle.articleCode) : wmArticle.articleCode != null)
            return false;
        if (description != null ? !description.equals(wmArticle.description) : wmArticle.description != null)
            return false;
        if (descriptionClient != null ? !descriptionClient.equals(wmArticle.descriptionClient) : wmArticle.descriptionClient != null)
            return false;
        if (articleClientCode != null ? !articleClientCode.equals(wmArticle.articleClientCode) : wmArticle.articleClientCode != null)
            return false;
        if (clientId != null ? !clientId.equals(wmArticle.clientId) : wmArticle.clientId != null) return false;
        if (blocked != null ? !blocked.equals(wmArticle.blocked) : wmArticle.blocked != null) return false;
        if (abcCode != null ? !abcCode.equals(wmArticle.abcCode) : wmArticle.abcCode != null) return false;
        if (articleTypeId != null ? !articleTypeId.equals(wmArticle.articleTypeId) : wmArticle.articleTypeId != null)
            return false;
        if (lotNeed != null ? !lotNeed.equals(wmArticle.lotNeed) : wmArticle.lotNeed != null) return false;
        if (articleKitId != null ? !articleKitId.equals(wmArticle.articleKitId) : wmArticle.articleKitId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = articleId != null ? articleId.hashCode() : 0;
        result = 31 * result + (articleCode != null ? articleCode.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (descriptionClient != null ? descriptionClient.hashCode() : 0);
        result = 31 * result + (articleClientCode != null ? articleClientCode.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (blocked != null ? blocked.hashCode() : 0);
        result = 31 * result + (abcCode != null ? abcCode.hashCode() : 0);
        result = 31 * result + (articleTypeId != null ? articleTypeId.hashCode() : 0);
        result = 31 * result + (lotNeed != null ? lotNeed.hashCode() : 0);
        result = 31 * result + (articleKitId != null ? articleKitId.hashCode() : 0);
        return result;
    }
}
