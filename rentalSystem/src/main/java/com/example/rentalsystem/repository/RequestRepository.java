package com.example.rentalsystem.repository;

import com.example.rentalsystem.model.Request;

import java.util.List;

public interface RequestRepository {
    List<Request> getRequests();

    void addRequest(Request request);
    Request getRequestById(int id);
    void completeRequest(int id);
    List<Request> filterRequests(String apt_num, String area, String startDate, String endDate, String status);
    void reset();
}
