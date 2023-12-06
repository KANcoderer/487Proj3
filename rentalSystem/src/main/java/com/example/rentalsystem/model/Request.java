package com.example.rentalsystem.model;

import java.sql.Timestamp;

public class Request {
    private int requestId;
    private int aptNum;
    private String area;
    private String description;
    private Timestamp date_time;
    private String image;
    private String status;
    private Boolean btn;

    public Request(int requestId, int aptNum, String area, String description, Timestamp date_time, String image, String status) {
        this.requestId = requestId;
        this.aptNum = aptNum;
        this.area = area;
        this.description = description;
        this.date_time = date_time;
        this.image = image;
        this.status = status;
        if(status.equals("Pending")){
            btn=true;
        }else if(status.equals("Completed")){
            btn=false;
        }
    }

    public Boolean getBtn() {
        return btn;
    }

    public void setBtn(Boolean btn) {
        this.btn = btn;
    }

    public Request(int requestId, int aptNum, String area, String description, Timestamp date_time, String status) {
        this.requestId = requestId;
        this.aptNum = aptNum;
        this.area = area;
        this.description = description;
        this.date_time = date_time;
        image=null;
        this.status = status;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getAptNum() {
        return aptNum;
    }

    public void setAptNum(int aptNum) {
        this.aptNum = aptNum;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate_time() {
        return date_time;
    }

    public void setDate_time(Timestamp date_time) {
        this.date_time = date_time;
    }

    public String getImage() {

        return "/tenant-photos/"+requestId+"/"+image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
