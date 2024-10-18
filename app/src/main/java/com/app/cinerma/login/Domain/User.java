package com.app.cinerma.login.Domain;

import java.util.Date;

public class User {

    private String id;
    private String fullName;
    private String paternalSurname;
    private String maternalSurname;
    private String phone;
    private String Dni;
    private String department;
    private String province;
    private String district;
    private String gender;
    private Date birthdate;
    private String name;
    private String email;
    private String password;
    private int favoriteCinema;

    public User() {

    }

    public User(String id, String fullName, String paternalSurname, String maternalSurname, String phone, String department, String dni, String province, String district, String gender, Date birthdate, String name, String email, String password, int favoriteCinema) {
        this.id = id;
        this.fullName = fullName;
        this.paternalSurname = paternalSurname;
        this.maternalSurname = maternalSurname;
        this.phone = phone;
        this.department = department;
        Dni = dni;
        this.province = province;
        this.district = district;
        this.gender = gender;
        this.birthdate = birthdate;
        this.name = name;
        this.email = email;
        this.password = password;
        this.favoriteCinema = favoriteCinema;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPaternalSurname() {
        return paternalSurname;
    }

    public void setPaternalSurname(String paternalSurname) {
        this.paternalSurname = paternalSurname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMaternalSurname() {
        return maternalSurname;
    }

    public void setMaternalSurname(String maternalSurname) {
        this.maternalSurname = maternalSurname;
    }

    public String getDni() {
        return Dni;
    }

    public void setDni(String dni) {
        Dni = dni;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFavoriteCinema() {
        return favoriteCinema;
    }

    public void setFavoriteCinema(int favoriteCinema) {
        this.favoriteCinema = favoriteCinema;
    }
}
