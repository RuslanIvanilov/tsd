package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 21.03.2017.
 */
@Entity
@Table(name = "wm_equipment", schema = "public", catalog = "wms")
public class WmEquipment {
    private Long equipmentId;
    private Long equipmentTypeId;
    private Long equipmentStateId;
    private String ipAddressCode;
    private String equipmentCode;
    private String description;
    private boolean blocked;
    private Long userId;
    private Timestamp useDate;

    @Id
    @Column(name = "equipment_id", nullable = false)
    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Basic
    @Column(name = "equipment_type_id", nullable = true)
    public Long getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(Long equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }

    @Basic
    @Column(name = "equipment_state_id", nullable = true)
    public Long getEquipmentStateId() {
        return equipmentStateId;
    }

    public void setEquipmentStateId(Long equipmentStateId) {
        this.equipmentStateId = equipmentStateId;
    }

    @Basic
    @Column(name = "ip_address_code", nullable = true, length = 250)
    public String getIpAddressCode() {
        return ipAddressCode;
    }

    public void setIpAddressCode(String ipAddressCode) {
        this.ipAddressCode = ipAddressCode;
    }

    @Basic
    @Column(name = "equipment_code", nullable = true, length = 250)
    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
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
    @Column(name = "blocked", nullable = true)
    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "use_date", nullable = true)
    public Timestamp getUseDate() {
        return useDate;
    }

    public void setUseDate(Timestamp useDate) {
        this.useDate = useDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmEquipment that = (WmEquipment) o;

        if (equipmentId != null ? !equipmentId.equals(that.equipmentId) : that.equipmentId != null) return false;
        if (equipmentTypeId != null ? !equipmentTypeId.equals(that.equipmentTypeId) : that.equipmentTypeId != null)
            return false;
        if (equipmentStateId != null ? !equipmentStateId.equals(that.equipmentStateId) : that.equipmentStateId != null)
            return false;
        if (ipAddressCode != null ? !ipAddressCode.equals(that.ipAddressCode) : that.ipAddressCode != null)
            return false;
        if (equipmentCode != null ? !equipmentCode.equals(that.equipmentCode) : that.equipmentCode != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (blocked != that.blocked) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (useDate != null ? !useDate.equals(that.useDate) : that.useDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = equipmentId != null ? equipmentId.hashCode() : 0;
        result = 31 * result + (equipmentTypeId != null ? equipmentTypeId.hashCode() : 0);
        result = 31 * result + (equipmentStateId != null ? equipmentStateId.hashCode() : 0);
        result = 31 * result + (ipAddressCode != null ? ipAddressCode.hashCode() : 0);
        result = 31 * result + (equipmentCode != null ? equipmentCode.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (blocked != false ? 1 : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (useDate != null ? useDate.hashCode() : 0);
        return result;
    }
}
