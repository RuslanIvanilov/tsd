package ru.defo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by syn-wms on 09.04.2018.
 */
public class WmCommentPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long commentId;
    private Long commentPosId;

    @Column(name = "comment_id", nullable = false)
    @Id
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Column(name = "comment_pos_id", nullable = false)
    @Id
    public Long getCommentPosId() {
        return commentPosId;
    }

    public void setCommentPosId(Long commentPosId) {
        this.commentPosId = commentPosId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmCommentPK that = (WmCommentPK) o;

        if (commentId != null ? !commentId.equals(that.commentId) : that.commentId != null) return false;
        if (commentPosId != null ? !commentPosId.equals(that.commentPosId) : that.commentPosId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentId != null ? commentId.hashCode() : 0;
        result = 31 * result + (commentPosId != null ? commentPosId.hashCode() : 0);
        return result;
    }
}
