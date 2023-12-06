package com.example.rentalsystem.database;

import com.example.rentalsystem.model.Request;
import com.example.rentalsystem.repository.RequestRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
public class RequestDBLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final RequestRepository requestRepository;

    public RequestDBLoader(RequestRepository requestRepository){
        this.requestRepository=requestRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        RequestDB.connect();
        ResultSet rs = RequestDB.browseRequests();
        try{
            if(rs!=null){
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

                    requestRepository.addRequest(request);
                }

            }

        }catch (SQLException sqle){
            System.out.println("table not populated");
        }
    }
}
