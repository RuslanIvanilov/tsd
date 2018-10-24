package ru.defo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by syn-wms on 21.03.2017.
 */
/*
@NamedNativeQueries({
	@NamedNativeQuery(
	name = "getUserModules",
	query = "select p.description from wm_user_permission up left join wm_permission p on up.permission_id = p.permission_id where user_id = :userId and p.group_code = 'TSD' order by p.permission_id")
})
*/

@Entity
@Table(name = "wm_user", schema = "public", catalog = "wms")
public class WmUser {
    private Long userId;

    @Id
    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private String userLogin;

    @Basic
    @Column(name = "user_login", nullable = true, length = 50)
    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    private String userBarcode;

    @Basic
    @Column(name = "user_barcode", nullable = true, length = 50)
    public String getUserBarcode() {
        return userBarcode;
    }

    public void setUserBarcode(String userBarcode) {
        this.userBarcode = userBarcode;
    }

    private String surname;

    @Basic
    @Column(name = "surname", nullable = true, length = 250)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    private String firstName;

    @Basic
    @Column(name = "first_name", nullable = true, length = 250)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String patronymic;

    @Basic
    @Column(name = "patronymic", nullable = true, length = 250)
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    private String position;

    @Basic
    @Column(name = "position", nullable = true, length = 250)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    private String emailCode;

    @Basic
    @Column(name = "email_code", nullable = true, length = 250)
    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    private boolean blocked;

    @Basic
    @Column(name = "blocked", nullable = true)
    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    private String pwd;

    @Basic
    @Column(name = "pwd", nullable = true, length = 250)
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    private Long birth;

    @Basic
    @Column(name = "birth", nullable = true)
    public Long getBirth() {
        return birth;
    }

    public void setBirth(Long birth) {
        this.birth = birth;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WmUser wmUser = (WmUser) o;

        if (userId != null ? !userId.equals(wmUser.userId) : wmUser.userId != null) return false;
        if (userLogin != null ? !userLogin.equals(wmUser.userLogin) : wmUser.userLogin != null) return false;
        if (userBarcode != null ? !userBarcode.equals(wmUser.userBarcode) : wmUser.userBarcode != null) return false;
        if (surname != null ? !surname.equals(wmUser.surname) : wmUser.surname != null) return false;
        if (firstName != null ? !firstName.equals(wmUser.firstName) : wmUser.firstName != null) return false;
        if (patronymic != null ? !patronymic.equals(wmUser.patronymic) : wmUser.patronymic != null) return false;
        if (position != null ? !position.equals(wmUser.position) : wmUser.position != null) return false;
        if (emailCode != null ? !emailCode.equals(wmUser.emailCode) : wmUser.emailCode != null) return false;
        if (blocked != wmUser.blocked) return false;
        if (pwd != null ? !pwd.equals(wmUser.pwd) : wmUser.pwd != null) return false;
        if (birth != null ? !birth.equals(wmUser.birth) : wmUser.birth != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userLogin != null ? userLogin.hashCode() : 0);
        result = 31 * result + (userBarcode != null ? userBarcode.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (emailCode != null ? emailCode.hashCode() : 0);
        result = 31 * result + (0);
        result = 31 * result + (pwd != null ? pwd.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        return result;
    }
}
