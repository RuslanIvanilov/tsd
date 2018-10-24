package ru.defo.model.views;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by syn-wms on 22.06.2017.
 */
@Entity
public class Vadvice {
    private Long adviceId;
    private Long adviceTypeId;
    private String adviceCode;
    private Timestamp expectedDate;
    private Timestamp factDate;
    private Long carId;
    private Long placeCount;
    private Long statusId;
    private String clientDocCode;
    private String type;
    private String carCode;
    private String carMark;
    private String carModel;
    private String binCode;
    private String status;
    private Long errorId;
    private String errorComment;


    @Basic
    @Column(name = "advice_id", nullable = true)
    public Long getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(Long adviceId) {
        this.adviceId = adviceId;
    }

    @Basic
    @Column(name = "advice_type_id", nullable = true)
    public Long getAdviceTypeId() {
        return adviceTypeId;
    }

    public void setAdviceTypeId(Long adviceTypeId) {
        this.adviceTypeId = adviceTypeId;
    }

    @Basic
    @Column(name = "advice_code", nullable = true, length = 50)
    public String getAdviceCode() {
        return adviceCode;
    }

    public void setAdviceCode(String adviceCode) {
        this.adviceCode = adviceCode;
    }

    @Basic
    @Column(name = "expected_date", nullable = true)
    public Timestamp getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Timestamp expectedDate) {
        this.expectedDate = expectedDate;
    }

    @Basic
    @Column(name = "fact_date", nullable = true)
    public Timestamp getFactDate() {
        return factDate;
    }

    public void setFactDate(Timestamp factDate) {
        this.factDate = factDate;
    }

    @Basic
    @Column(name = "car_id", nullable = true)
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Basic
    @Column(name = "place_count", nullable = true)
    public Long getPlaceCount() {
        return placeCount;
    }

    public void setPlaceCount(Long placeCount) {
        this.placeCount = placeCount;
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
    @Column(name ="error_id", nullable = true)
    public Long getErrorId(){
    	return errorId;
    }

    public void setErrorId(Long errorId){
    	this.errorId = errorId;
    }

    @Basic
    @Column(name = "error_comment", nullable = true, length = 250)
    public String getErrorComment(){
    	return errorComment;
    }

    public void setErrorComment(String errorComment){
    	this.errorComment = errorComment;
    }


    @Basic
    @Column(name = "client_doc_code", nullable = true, length = 50)
    public String getClientDocCode() {
        return clientDocCode;
    }

    public void setClientDocCode(String clientDocCode) {
        this.clientDocCode = clientDocCode;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 250)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "car_code", nullable = true, length = 250)
    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    @Basic
    @Column(name = "car_mark", nullable = true, length = 250)
    public String getCarMark() {
        return carMark;
    }

    public void setCarMark(String carMark) {
        this.carMark = carMark;
    }

    @Basic
    @Column(name = "car_model", nullable = true, length = 250)
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @Basic
    @Column(name = "bin_code", nullable = true, length = 150)
    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 250)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vadvice vadvice = (Vadvice) o;

        if (adviceId != null ? !adviceId.equals(vadvice.adviceId) : vadvice.adviceId != null) return false;
        if (adviceTypeId != null ? !adviceTypeId.equals(vadvice.adviceTypeId) : vadvice.adviceTypeId != null)
            return false;
        if (adviceCode != null ? !adviceCode.equals(vadvice.adviceCode) : vadvice.adviceCode != null) return false;
        if (expectedDate != null ? !expectedDate.equals(vadvice.expectedDate) : vadvice.expectedDate != null)
            return false;
        if (factDate != null ? !factDate.equals(vadvice.factDate) : vadvice.factDate != null) return false;
        if (carId != null ? !carId.equals(vadvice.carId) : vadvice.carId != null) return false;
        if (placeCount != null ? !placeCount.equals(vadvice.placeCount) : vadvice.placeCount != null) return false;
        if (statusId != null ? !statusId.equals(vadvice.statusId) : vadvice.statusId != null) return false;
        if (clientDocCode != null ? !clientDocCode.equals(vadvice.clientDocCode) : vadvice.clientDocCode != null)
            return false;
        if (type != null ? !type.equals(vadvice.type) : vadvice.type != null) return false;
        if (carCode != null ? !carCode.equals(vadvice.carCode) : vadvice.carCode != null) return false;
        if (carMark != null ? !carMark.equals(vadvice.carMark) : vadvice.carMark != null) return false;
        if (carModel != null ? !carModel.equals(vadvice.carModel) : vadvice.carModel != null) return false;
        if (binCode != null ? !binCode.equals(vadvice.binCode) : vadvice.binCode != null) return false;
        if (status != null ? !status.equals(vadvice.status) : vadvice.status != null) return false;

        if (errorId != null ? !errorId.equals(vadvice.errorId) : vadvice.errorId != null) return false;
        if (errorComment != null ? !errorComment.equals(vadvice.errorComment) : vadvice.errorComment != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = adviceId != null ? adviceId.hashCode() : 0;
        result = 31 * result + (adviceTypeId != null ? adviceTypeId.hashCode() : 0);
        result = 31 * result + (adviceCode != null ? adviceCode.hashCode() : 0);
        result = 31 * result + (expectedDate != null ? expectedDate.hashCode() : 0);
        result = 31 * result + (factDate != null ? factDate.hashCode() : 0);
        result = 31 * result + (carId != null ? carId.hashCode() : 0);
        result = 31 * result + (placeCount != null ? placeCount.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (clientDocCode != null ? clientDocCode.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (carCode != null ? carCode.hashCode() : 0);
        result = 31 * result + (carMark != null ? carMark.hashCode() : 0);
        result = 31 * result + (carModel != null ? carModel.hashCode() : 0);
        result = 31 * result + (binCode != null ? binCode.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (errorId != null ? errorId.hashCode() : 0);
        result = 31 * result + (errorComment != null ? errorComment.hashCode() : 0);
        return result;
    }
}
