package ru.defo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 21.03.2017.
 */
@Entity
@Table(name = "wm_car", schema = "public", catalog = "wms")
public class WmCar {
    private Long carId;
    private String carCode;
    private String carMark;
    private String carModel;

    @Id
    @Column(name = "car_id", nullable = false)
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmCar wmCar = (WmCar) o;

        if (carId != null ? !carId.equals(wmCar.carId) : wmCar.carId != null) return false;
        if (carCode != null ? !carCode.equals(wmCar.carCode) : wmCar.carCode != null) return false;
        if (carMark != null ? !carMark.equals(wmCar.carMark) : wmCar.carMark != null) return false;
        if (carModel != null ? !carModel.equals(wmCar.carModel) : wmCar.carModel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = carId != null ? carId.hashCode() : 0;
        result = 31 * result + (carCode != null ? carCode.hashCode() : 0);
        result = 31 * result + (carMark != null ? carMark.hashCode() : 0);
        result = 31 * result + (carModel != null ? carModel.hashCode() : 0);
        return result;
    }
}
