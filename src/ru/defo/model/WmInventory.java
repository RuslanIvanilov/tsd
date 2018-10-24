package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 04.10.2017.
 */
@Entity
@Table(name = "wm_inventory", schema = "public", catalog = "wms")
public class WmInventory {
    private Long inventoryId;
    private Long statusId;
    private String comment;
    private Timestamp startDate;
    private Timestamp endDate;
    private Long createUser;
    private Timestamp createDate;
    private Long initiatorId;
    private Long inventoryType;

    private String areaCodeFilter;
    private Long rackFromFilter;
    private Long rackToFilter;
    private Long levelFromFilter;
    private Long levelToFilter;
    private Long binFromFilter;
    private Long binToFilter;

    @Id
    @Column(name = "inventory_id", nullable = false)
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Basic
    @Column(name = "status_id", nullable = true)
    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "comment", nullable = true, length = 250)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "start_date", nullable = true)
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = true)
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "create_user", nullable = true)
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
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
    @Column(name = "initiator_id", nullable = true)
    public Long getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Long initiatorId) {
        this.initiatorId = initiatorId;
    }

    @Basic
    @Column(name = "inventory_type", nullable = true)
    public Long getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(Long inventoryType) {
		this.inventoryType = inventoryType;
	}



	@Basic
    @Column(name = "area_code_filter", nullable = true, length = 50)
	public String getAreaCodeFilter() {
		return areaCodeFilter;
	}

	public void setAreaCodeFilter(String areaCodeFilter) {
		this.areaCodeFilter = areaCodeFilter;
	}

	@Basic
    @Column(name = "rack_from_filter", nullable = true)
	public Long getRackFromFilter() {
		return rackFromFilter;
	}

	public void setRackFromFilter(Long rackFromFilter) {
		this.rackFromFilter = rackFromFilter;
	}

	@Basic
    @Column(name = "rack_to_filter", nullable = true)
	public Long getRackToFilter() {
		return rackToFilter;
	}

	public void setRackToFilter(Long rackToFilter) {
		this.rackToFilter = rackToFilter;
	}

	@Basic
    @Column(name = "level_from_filter", nullable = true)
	public Long getLevelFromFilter() {
		return levelFromFilter;
	}

	public void setLevelFromFilter(Long levelFromFilter) {
		this.levelFromFilter = levelFromFilter;
	}

	@Basic
    @Column(name = "level_to_filter", nullable = true)
	public Long getLevelToFilter() {
		return levelToFilter;
	}

	public void setLevelToFilter(Long levelToFilter) {
		this.levelToFilter = levelToFilter;
	}

	@Basic
    @Column(name = "bin_from_filter", nullable = true)
	public Long getBinFromFilter() {
		return binFromFilter;
	}

	public void setBinFromFilter(Long binFromFilter) {
		this.binFromFilter = binFromFilter;
	}

	@Basic
    @Column(name = "bin_to_filter", nullable = true)
	public Long getBinToFilter() {
		return binToFilter;
	}

	public void setBinToFilter(Long binToFilter) {
		this.binToFilter = binToFilter;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmInventory that = (WmInventory) o;

        if (inventoryId != null ? !inventoryId.equals(that.inventoryId) : that.inventoryId != null) return false;
        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (initiatorId != null ? !initiatorId.equals(that.initiatorId) : that.initiatorId != null) return false;
        if (inventoryType != null ? !inventoryType.equals(that.inventoryType) : that.inventoryType != null) return false;

        if (areaCodeFilter != null ? 	!areaCodeFilter.equals(that.areaCodeFilter) 	: that.areaCodeFilter != null) return false;
        if (rackFromFilter != null ? 	!rackFromFilter.equals(that.rackFromFilter) 	: that.rackFromFilter != null) return false;
        if (rackToFilter != null ? 		!rackToFilter.equals(that.rackToFilter) 		: that.rackToFilter != null) return false;
        if (levelFromFilter != null ? 	!levelFromFilter.equals(that.levelFromFilter) 	: that.levelFromFilter != null) return false;
        if (levelToFilter != null ? !	levelToFilter.equals(that.levelToFilter) 		: that.levelToFilter != null) return false;
        if (binFromFilter != null ? 	!binFromFilter.equals(that.binFromFilter) 		: that.binFromFilter != null) return false;
        if (binToFilter != null ? 		!binToFilter.equals(that.binToFilter) 			: that.binToFilter != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = inventoryId != null ? inventoryId.hashCode() : 0;
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (initiatorId != null ? initiatorId.hashCode() : 0);
        result = 31 * result + (inventoryType != null ? inventoryType.hashCode() : 0);

        result = 31 * result + (areaCodeFilter != null ? areaCodeFilter.hashCode() : 0);
        result = 31 * result + (rackFromFilter != null ? rackFromFilter.hashCode() : 0);
        result = 31 * result + (rackToFilter != null ? rackToFilter.hashCode() : 0);
        result = 31 * result + (levelFromFilter != null ? levelFromFilter.hashCode() : 0);
        result = 31 * result + (levelToFilter != null ? levelToFilter.hashCode() : 0);
        result = 31 * result + (binFromFilter != null ? binFromFilter.hashCode() : 0);
        result = 31 * result + (binToFilter != null ? binToFilter.hashCode() : 0);

        return result;
    }
}
