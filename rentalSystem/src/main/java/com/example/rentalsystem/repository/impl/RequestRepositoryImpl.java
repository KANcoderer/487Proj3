package com.example.rentalsystem.repository.impl;

import com.example.rentalsystem.database.RequestDB;
import com.example.rentalsystem.model.Request;
import com.example.rentalsystem.repository.RequestRepository;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RequestRepositoryImpl implements RequestRepository {
    private List<Request> requests;

    public RequestRepositoryImpl(){requests=new ArrayList<>();
    }
    @Override
    public List<Request> getRequests(){return requests;}

    @Override
    public void addRequest(Request request){requests.add(request);}

    @Override
    public Request getRequestById(int id){
        var filtered = requests.stream().filter(r -> Objects.equals(r.getRequestId(), id)).collect(Collectors.toList());
        if (filtered.size() > 0) {
            return filtered.get(0);
        }
        throw new IllegalStateException("request not found with ID " + id);
    }
    @Override
    public void completeRequest(int id){
        Request request=getRequestById(id);
        request.setStatus("Completed");
        request.setBtn(false);
        RequestDB.completeRequest(id);
    }

    @Override
    public List<Request> filterRequests(String apt_num, String area, String startDate, String endDate, String status){
        boolean aptNumFilter=false;
        boolean areaFilter=false;
        boolean sDateFilter=false;
        boolean eDateFilter=false;
        boolean statusFilter=false;
        Date sDate=null;
        Date eDate=null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(!apt_num.equals("")){
            aptNumFilter=true;
        }
        if(!area.equals("")){
            areaFilter=true;
        }
        if(!startDate.equals("")){
            sDateFilter=true;

            try {
                sDate =  dateFormat.parse(startDate);
            }catch (Exception e){

                System.err.println("Start Date not parsed");
            }
        }
        if(!endDate.equals("")){
            eDateFilter=true;
            try {
                eDate =  dateFormat.parse(endDate);
                eDate = new Date(eDate.getTime() + (1000 * 60 * 60 * 24));
            }catch (Exception e){
                System.err.println("End Date not parsed");
            }
        }
        if(!status.equals("")){
            statusFilter=true;
        }
        boolean[] filters={aptNumFilter,areaFilter,sDateFilter,eDateFilter, statusFilter};
        List<Request> filtered=requests;
        try {
            for (int i = 0; i < filters.length; i++) {
                if (filters[i]) {
                    switch (i) {
                        case 0 -> {
                            filtered = filtered.stream().filter(r -> Objects.equals(r.getAptNum(), Integer.parseInt(apt_num)))
                                    .collect(Collectors.toList());
                        }
                        case 1 -> {
                            filtered = filtered.stream().filter(r -> Objects.equals(r.getArea(), area))
                                    .collect(Collectors.toList());
                        }
                        case 2 -> {

                            Date finalSDate = sDate;
                            filtered = filtered.stream().filter(r -> r.getDate_time().after(finalSDate))
                                    .collect(Collectors.toList());
                        }
                        case 3 -> {

                            Date finalEDate = eDate;
                            filtered = filtered.stream().filter(r -> r.getDate_time().before(finalEDate))
                                    .collect(Collectors.toList());
                        }
                        case 4 -> {
                            filtered = filtered.stream().filter(r -> Objects.equals(r.getStatus(), status))
                                    .collect(Collectors.toList());
                        }
                    }
                }
            }
        }catch (Exception ex){
            throw new IllegalStateException("Filter error");
        }

        return filtered;
    }
    @Override
    public void reset(){
        ResultSet rs = RequestDB.browseRequests();
        try{
            if(rs!=null){
                while(requests.size()>0){
                    requests.remove(0);
                }
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int apt_num = rs.getInt("apt_num");
                    String area=rs.getString("area");
                    String description = rs.getString("description");
                    Timestamp date_time=rs.getTimestamp("date_time");
                    String image = rs.getString("image");
                    boolean stat=rs.getBoolean("status");
                    String status;
                    if(stat){
                        status="Completed";
                    }else{
                        status="Pending";
                    }
                    Request request=new Request(id,apt_num,area,description,date_time,image,status);

                    requests.add(request);
                }

            }

        }catch (SQLException sqle){
            System.out.println("table not populated");
        }
    }
}
