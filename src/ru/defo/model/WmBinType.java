package ru.defo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

/**
 * Created by syn-wms on 12.02.2018.
 */
@Entity
@Table(name = "wm_bin_type", schema = "public", catalog = "wms")
public class WmBinType {
    private Long binTypeId;
    private String description;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_bin_type_id")
    @SequenceGenerator(name="seq_bin_type_id", sequenceName="seq_bin_type_id", allocationSize=1)
    @Column(name = "bin_type_id", nullable = false)
    public Long getBinTypeId() {
        return binTypeId;
    }

    public void setBinTypeId(Long binTypeId) {
        this.binTypeId = binTypeId;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 150)
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

        WmBinType wmBinType = (WmBinType) o;

        if (binTypeId != null ? !binTypeId.equals(wmBinType.binTypeId) : wmBinType.binTypeId != null) return false;
        if (description != null ? !description.equals(wmBinType.description) : wmBinType.description != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = binTypeId != null ? binTypeId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
