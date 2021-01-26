package com.example.rentalcarsystem.Models;

import java.io.Serializable;

public class OrderRequest implements Serializable {
    String pAddress,pAvailable,pId,pImageUrl,pName,pType,rentForNumberOfDays,totalPrice,userEmail,userId,vendorId;

    public OrderRequest() {
    }

    public OrderRequest(String pAddress, String pAvailable, String pId, String pImageUrl, String pName, String pType, String rentForNumberOfDays, String totalPrice, String userEmail, String userId, String vendorId) {
        this.pAddress = pAddress;
        this.pAvailable = pAvailable;
        this.pId = pId;
        this.pImageUrl = pImageUrl;
        this.pName = pName;
        this.pType = pType;
        this.rentForNumberOfDays = rentForNumberOfDays;
        this.totalPrice = totalPrice;
        this.userEmail = userEmail;
        this.userId = userId;
        this.vendorId = vendorId;
    }

    public String getpAddress() {
        return pAddress;
    }

    public void setpAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public String getpAvailable() {
        return pAvailable;
    }

    public void setpAvailable(String pAvailable) {
        this.pAvailable = pAvailable;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpImageUrl() {
        return pImageUrl;
    }

    public void setpImageUrl(String pImageUrl) {
        this.pImageUrl = pImageUrl;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getRentForNumberOfDays() {
        return rentForNumberOfDays;
    }

    public void setRentForNumberOfDays(String rentForNumberOfDays) {
        this.rentForNumberOfDays = rentForNumberOfDays;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
}
