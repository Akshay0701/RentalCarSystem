package com.example.rentalcarsystem.Models;

public class Vendors {
    String address,country,email,password,phoneNo,vId,vName;

    public Vendors() {
    }

    public Vendors(String address, String country, String email, String password, String phoneNo, String vId, String vName) {
        this.address = address;
        this.country = country;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.vId = vId;
        this.vName = vName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }
}
