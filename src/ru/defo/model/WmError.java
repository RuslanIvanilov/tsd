package ru.defo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 16.10.2017.
 */
@Entity
@Table(name = "wm_error", schema = "public", catalog = "wms")
public class WmError {
    private Long errorId;
    private String description;

    @Id
    @Column(name = "error_id", nullable = false)
    public Long getErrorId() {
        return errorId;
    }

    public void setErrorId(Long errorId) {
        this.errorId = errorId;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 250)
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

        WmError wmError = (WmError) o;

        if (errorId != null ? !errorId.equals(wmError.errorId) : wmError.errorId != null) return false;
        if (description != null ? !description.equals(wmError.description) : wmError.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = errorId != null ? errorId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
