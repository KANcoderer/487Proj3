package com.example.rentalsystem.model;

import java.time.LocalDate;


public class Tenant {
    private int tenantId;
    private String tenantName;
    private String tenantPhoneNum;
    private String tenantEmail;
    private String checkIn;
    private String checkOut;
    private int aptNum;

    public Tenant(int tenantId, String tenantName, String tenantPhoneNum, String tenantEmail, String checkIn, String checkOut, int aptNum) {
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.tenantPhoneNum = tenantPhoneNum;
        this.tenantEmail = tenantEmail;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.aptNum = aptNum;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantPhoneNum() {
        return tenantPhoneNum;
    }

    public void setTenantPhoneNum(String tenantPhoneNum) {
        this.tenantPhoneNum = tenantPhoneNum;
    }

    public String getTenantEmail() {
        return tenantEmail;
    }

    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public int getAptNum() {
        return aptNum;
    }

    public void setAptNum(int aptNum) {
        this.aptNum = aptNum;
    }
}
