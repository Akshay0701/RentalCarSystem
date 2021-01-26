package com.example.rentalcarsystem.Models;

import java.io.Serializable;

public class OrderPlaced implements Serializable {
    String dueDate,issuedDate,pAddress,pAvailable,pId,pImageUrl,pName,pType,totalPrice,userEmail,userId,vendorId;

    public OrderPlaced() {
    }

    public OrderPlaced(String dueDate, String issuedDate, String pAddress, String pAvailable, String pId, String pImageUrl, String pName, String pType, String totalPrice, String userEmail, String userId, String vendorId) {
        this.dueDate = dueDate;
        this.issuedDate = issuedDate;
        this.pAddress = pAddress;
        this.pAvailable = pAvailable;
        this.pId = pId;
        this.pImageUrl = pImageUrl;
        this.pName = pName;
        this.pType = pType;
        this.totalPrice = totalPrice;
        this.userEmail = userEmail;
        this.userId = userId;
        this.vendorId = vendorId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
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
