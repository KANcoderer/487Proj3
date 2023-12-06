package com.example.rentalsystem.service.Impl;

import com.example.rentalsystem.database.RequestDB;
import com.example.rentalsystem.model.Request;
import com.example.rentalsystem.repository.RequestRepository;
import com.example.rentalsystem.service.RequestService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    public RequestServiceImpl(RequestRepository requestRepository){this.requestRepository=requestRepository;}

    @Override
    public List<Request> getRequests(){
        return requestRepository.getRequests();
    }

    @Override
    public List<Request> filterRequests(String apt_num, String area, String startDate, String endDate, String status){
        try {
            return requestRepository.filterRequests(apt_num, area, startDate, endDate, status);

        }catch (Exception ex){

            throw new IllegalStateException("Filter error");
        }
    }

    @Override
    public Request getRequestById(int requestId){
        if (requestId <=0) {
            throw new IllegalArgumentException("request ID is required");
        }

        return requestRepository.getRequestById(requestId);
    }
    @Override
    public void completeRequest(int requestId){
        requestRepository.completeRequest(requestId);
    }

    @Override
    public int getMaxId(){
        try {
            ResultSet rs=RequestDB.GetMaxID();
            if(rs==null){
                throw new Exception();
            }else{
                return rs.getInt("max(id)");
            }

        }catch (Exception ex){
            System.err.println("no max id");
        }
        return -1;
    }
    @Override
    public void addRequest(int aptNum, String area, String description, Timestamp date_time, String image){
        if (! StringUtils.hasText(area)) {
            throw new IllegalArgumentException("area is required");
        }
        if (! StringUtils.hasText(description)) {
            throw new IllegalArgumentException("description is required");
        }
        try {
            RequestDB.addRequest(aptNum, area, description, date_time, image);
        }catch (Exception ex){
            throw new IllegalStateException("Request not added");
        }
        ResultSet rs;
        try {
            rs= RequestDB.GetMaxID();
            if(rs!=null) {
                if(rs.next()){
                    Request request = new Request(rs.getInt("max(id)"), aptNum,area, description,date_time, image,"Pending");
                    requestRepository.addRequest(request);
                }
            }else{
                throw new SQLException();
            }
        }catch (SQLException sqle){
            System.err.println("No max id");
        }
    }
    @Override
    public void reset(){
        requestRepository.reset();
    }
}
