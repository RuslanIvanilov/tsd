package ru.defo.model;

import java.sql.Timestamp;

/**
 * Created by syn-wms on 21.03.2017.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "wm_permission", schema = "public", catalog = "wms")
public class WmPermission {
    private Long permissionId;

    @javax.persistence.Id
    @javax.persistence.Column(name = "permission_id", nullable = false)
    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    private String permissionCode;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "permission_code", nullable = true, length = 50)
    public String getPermissionCode() {
        return permissionCode==null?"":permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    private String description;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "description", nullable = true, length = 250)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Long createUser;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "create_user", nullable = true)
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }    private Timestamp createDate;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "create_date", nullable = true)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    private String groupCode;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "group_code", nullable = true, length = 50)
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmPermission that = (WmPermission) o;

        if (permissionId != null ? !permissionId.equals(that.permissionId) : that.permissionId != null) return false;
        if (permissionCode != null ? !permissionCode.equals(that.permissionCode) : that.permissionCode != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = permissionId != null ? permissionId.hashCode() : 0;
        result = 31 * result + (permissionCode != null ? permissionCode.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }
}
