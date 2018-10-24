package ru.defo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 02.11.2017.
 */
@Entity
@Table(name = "wm_vendor_group", schema = "public", catalog = "wms")
public class WmVendorGroup {
    private Long vendorGroupId;
    private String description;

    public WmVendorGroup(){
    	super();
    }

    public WmVendorGroup(Long vendorGroupId, String description){
    	super();
    	this.vendorGroupId = vendorGroupId;
    	this.description = description;
    }

    @Id
    @Column(name = "vendor_group_id", nullable = false)
    public Long getVendorGroupId() {
        return vendorGroupId;
    }

    public void setVendorGroupId(Long vendorGroupId) {
        this.vendorGroupId = vendorGroupId;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 250)
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

        WmVendorGroup that = (WmVendorGroup) o;

        if (vendorGroupId != null ? !vendorGroupId.equals(that.vendorGroupId) : that.vendorGroupId != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vendorGroupId != null ? vendorGroupId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
