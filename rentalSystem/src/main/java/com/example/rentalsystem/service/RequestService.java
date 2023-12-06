package com.example.rentalsystem.service;

import com.example.rentalsystem.model.Request;

import java.sql.Timestamp;
import java.util.List;

public interface RequestService {
    List<Request> getRequests();

    List<Request> filterRequests(String apt_num, String area, String startDate, String endDate, String status);

    void addRequest(int aptNum, String area, String description, Timestamp date_time, String image);
    int getMaxId();
    void completeRequest(int requestId);
    Request getRequestById(int requestId);
    void reset();
}
