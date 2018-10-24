package ru.defo.model.views;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by syn-wms on 21.03.2017.
 */
public class VuserpermissionPK implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;
    private Long permissionId;

    @Column(name = "user_id", nullable = false)
    @Id
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "permission_id", nullable = false)
    @Id
    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VuserpermissionPK that = (VuserpermissionPK) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (permissionId != null ? !permissionId.equals(that.permissionId) : that.permissionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (permissionId != null ? permissionId.hashCode() : 0);
        return result;
    }
}
