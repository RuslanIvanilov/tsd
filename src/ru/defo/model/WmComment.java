package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Created by syn-wms on 09.04.2018.
 */
@Entity
@Table(name = "wm_comment", schema = "public", catalog = "wms")
@IdClass(WmCommentPK.class)
public class WmComment {
    private Long commentId;
    private Long commentPosId;
    private String commentText;
    private Timestamp createDate;
    private Long createUser;

    @Id
    @Column(name = "comment_id", nullable = false)
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Id
    @Column(name = "comment_pos_id", nullable = false)
    public Long getCommentPosId() {
        return commentPosId;
    }

    public void setCommentPosId(Long commentPosId) {
        this.commentPosId = commentPosId;
    }

    @Basic
    @Column(name = "comment_text", nullable = true, length = 1024)
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmComment wmComment = (WmComment) o;

        if (commentId != null ? !commentId.equals(wmComment.commentId) : wmComment.commentId != null) return false;
        if (commentPosId != null ? !commentPosId.equals(wmComment.commentPosId) : wmComment.commentPosId != null)
            return false;
        if (commentText != null ? !commentText.equals(wmComment.commentText) : wmComment.commentText != null)
            return false;
        if (createDate != null ? !createDate.equals(wmComment.createDate) : wmComment.createDate != null) return false;
        if (createUser != null ? !createUser.equals(wmComment.createUser) : wmComment.createUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentId != null ? commentId.hashCode() : 0;
        result = 31 * result + (commentPosId != null ? commentPosId.hashCode() : 0);
        result = 31 * result + (commentText != null ? commentText.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        return result;
    }
}
