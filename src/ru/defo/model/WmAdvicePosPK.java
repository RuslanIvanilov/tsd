package ru.defo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by syn-wms on 21.03.2017.
 */
public class WmAdvicePosPK implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long adviceId;
    private Long advicePosId;

    @Column(name = "advice_id", nullable = false)
    @Id
    public Long getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(Long adviceId) {
        this.adviceId = adviceId;
    }

    @Column(name = "advice_pos_id", nullable = false)
    @Id
    public Long getAdvicePosId() {
        return advicePosId;
    }

    public void setAdvicePosId(Long advicePosId) {
        this.advicePosId = advicePosId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmAdvicePosPK that = (WmAdvicePosPK) o;

        if (adviceId != null ? !adviceId.equals(that.adviceId) : that.adviceId != null) return false;
        if (advicePosId != null ? !advicePosId.equals(that.advicePosId) : that.advicePosId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adviceId != null ? adviceId.hashCode() : 0;
        result = 31 * result + (advicePosId != null ? advicePosId.hashCode() : 0);
        return result;
    }
}
