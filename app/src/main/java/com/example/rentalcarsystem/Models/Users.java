package com.example.rentalcarsystem.Models;

public class Users {
    String address,country,email,password,phoneNo,uId,uName;

    public Users() {
    }

    public Users(String address, String country, String email, String password, String phoneNo, String uId, String uName) {
        this.address = address;
        this.country = country;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.uId = uId;
        this.uName = uName;
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

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}
