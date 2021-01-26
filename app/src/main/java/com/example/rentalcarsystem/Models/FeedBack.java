package com.example.rentalcarsystem.Models;

public class FeedBack {
    String iD,message;

    public FeedBack() {
    }

    public FeedBack(String iD, String message) {
        this.iD = iD;
        this.message = message;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
