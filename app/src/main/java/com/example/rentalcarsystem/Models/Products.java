package com.example.rentalcarsystem.Models;

import java.io.Serializable;

public class Products implements Serializable {
    String pAddress,pAvailable,pId,pImageUrl,pName,pType,pricePerDay,vendorId,vendorNo;

    public Products() {
    }

    public Products(String pAddress, String pAvailable, String pId, String pImageUrl, String pName, String pType, String pricePerDay, String vendorId, String vendorNo) {
        this.pAddress = pAddress;
        this.pAvailable = pAvailable;
        this.pId = pId;
        this.pImageUrl = pImageUrl;
        this.pName = pName;
        this.pType = pType;
        this.pricePerDay = pricePerDay;
        this.vendorId = vendorId;
        this.vendorNo = vendorNo;
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

    public String getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(String pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorNo() {
        return vendorNo;
    }

    public void setVendorNo(String vendorNo) {
        this.vendorNo = vendorNo;
    }
}
