package ru.defo.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 25.01.2018.
 */
@Entity
@Table(name = "ws_job", schema = "public", catalog = "wms")
public class WsJob {
    private String wsClientCode;
    private Long userId;
    private Timestamp startDate;
    private Timestamp finishDate;

    @Id
    @Column(name = "ws_client_code", nullable = false, length = 20)
    public String getWsClientCode() {
        return wsClientCode;
    }

    public void setWsClientCode(String wsClientCode) {
        this.wsClientCode = wsClientCode;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
    @Column(name = "finish_date", nullable = true)
    public Timestamp getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Timestamp finishDate) {
        this.finishDate = finishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WsJob wsJob = (WsJob) o;

        if (wsClientCode != null ? !wsClientCode.equals(wsJob.wsClientCode) : wsJob.wsClientCode != null) return false;
        if (userId != null ? !userId.equals(wsJob.userId) : wsJob.userId != null) return false;
        if (startDate != null ? !startDate.equals(wsJob.startDate) : wsJob.startDate != null) return false;
        if (finishDate != null ? !finishDate.equals(wsJob.finishDate) : wsJob.finishDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wsClientCode != null ? wsClientCode.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (finishDate != null ? finishDate.hashCode() : 0);
        return result;
    }
}
