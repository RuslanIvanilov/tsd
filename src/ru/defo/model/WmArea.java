package ru.defo.model;

import javax.persistence.*;

/**
 * Created by syn-wms on 26.04.2018.
 */
@Entity
@Table(name = "wm_area", schema = "public", catalog = "wms")
public class WmArea {
    private String areaCode;
    private String description;
    private boolean blockIn;
    private boolean blockOut;
    private Long locationId;

    @Id
    @Column(name = "area_code", nullable = false, length = 50)
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 150)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "block_in", nullable = true)
    public boolean getBlockIn() {
        return blockIn;
    }

    public void setBlockIn(boolean blockIn) {
        this.blockIn = blockIn;
    }

    @Basic
    @Column(name = "block_out", nullable = true)
    public boolean getBlockOut() {
        return blockOut;
    }


    public void setBlockOut(boolean blockOut) {
        this.blockOut = blockOut;
    }

    @Basic
    @Column(name = "location_id", nullable = true)
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmArea wmArea = (WmArea) o;
        if (areaCode != null ? !areaCode.equals(wmArea.areaCode) : wmArea.areaCode != null) return false;
        if (description != null ? !description.equals(wmArea.description) : wmArea.description != null) return false;
        if (blockIn != wmArea.blockIn) return false;
        if (blockOut != wmArea.blockOut) return false;
        if (locationId != null ? !locationId.equals(wmArea.locationId) : wmArea.locationId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = areaCode != null ? areaCode.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (blockIn != false ? 1 : 0);
        result = 31 * result + (blockOut != false ? 1 : 0);
        result = 31 * result + (locationId != null ? locationId.hashCode() : 0);
        return result;
    }
}
