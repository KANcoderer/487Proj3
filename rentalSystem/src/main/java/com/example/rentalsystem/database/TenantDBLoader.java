package com.example.rentalsystem.database;

import com.example.rentalsystem.model.Tenant;
import com.example.rentalsystem.repository.TenantRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class TenantDBLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final TenantRepository tenantRepository;

    public TenantDBLoader(TenantRepository tenantRepository){
        this.tenantRepository=tenantRepository;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        TenantDB.connect();
        ResultSet rs= TenantDB.browseTenants();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            if(rs!=null) {
                while (rs.next()) {
                    int id = rs.getInt("id");

                    String name = rs.getString("name");

                    String phoneNum = rs.getString("phone_number");

                    String email = rs.getString("email");

                    String checkIn = rs.getString("check_in");

                    String checkOut = rs.getString("check_out");
                    int aptNum = rs.getInt("apt_num");
                    Tenant tenant = new Tenant(id, name, phoneNum, email, checkIn, checkOut, aptNum);

                    tenantRepository.addTenant(tenant);
                }
            }
        }catch (SQLException sqle){
            System.out.println("table not populated");
        }
    }
}
