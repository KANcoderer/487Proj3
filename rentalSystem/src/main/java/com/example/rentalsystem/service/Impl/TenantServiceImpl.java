package com.example.rentalsystem.service.Impl;

import com.example.rentalsystem.database.TenantDB;
import com.example.rentalsystem.model.Tenant;
import com.example.rentalsystem.repository.TenantRepository;
import com.example.rentalsystem.service.TenantService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TenantServiceImpl implements TenantService {
    private final TenantRepository tenantRepository;
    public TenantServiceImpl(TenantRepository tenantRepository){this.tenantRepository=tenantRepository;}
    @Override
    public List<Tenant> getTenants(){
        return tenantRepository.getTenants();
    }
    @Override
    public Tenant getTenantById(int id){
        if(id<=0){
            throw new IllegalArgumentException("tenant id is required");
        }
        return tenantRepository.getTenantById(id);
    }
    @Override
    public void addTenant(String name, String phoneNum, String email, String checkIn, String checkOut, int aptNum){
        if (! StringUtils.hasText(name)) {
            throw new IllegalArgumentException("tenant name is required");
        }

        Pattern pattern = Pattern.compile("\\b[0-9]{3}[-][0-9]{3}[-][0-9]{4}\\b");
        Matcher matcher = pattern.matcher(phoneNum);
        if (!matcher.find()) {
            throw new IllegalArgumentException("correctly formatted tenant phone number is required");
        }
        if (! StringUtils.hasText(email)) {
            throw new IllegalArgumentException("tenant email is required");
        }

        if(checkIn.equals("")){

            throw new IllegalArgumentException("check in date is required");
        }
        if(checkOut.equals("")){
            throw new IllegalArgumentException("check out date is required");
        }
        Date sDate=null;
        Date eDate=null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sDate =  dateFormat.parse(checkIn);

        }catch (Exception e){

            System.err.println("Check In Date not parsed");
        }
        try {
            eDate =  dateFormat.parse(checkOut);

        }catch (Exception e){

            System.err.println("Check Out Date not parsed");
        }
        assert eDate != null;
        if(eDate.before(sDate)){
            throw new IllegalArgumentException("check out date is before check in date");
        }
        try {
            TenantDB.addTenant(name, phoneNum, email, checkIn, checkOut, aptNum);
        }catch (SQLException sqle){
            throw new IllegalArgumentException("Tenant not added");
        }
        ResultSet rs;
        try {
            rs= TenantDB.GetMaxID();
            if(rs!=null) {
                if(rs.next()){
                    Tenant tenant = new Tenant(rs.getInt("max(id)"), name,phoneNum,email,checkIn,checkOut,aptNum);
                    tenantRepository.addTenant(tenant);
                }
            }else{
                throw new SQLException();
            }
        }catch (SQLException sqle){
            System.err.println("No max id");
        }
    }
    @Override
    public void moveTenant(int id, int aptNum){
        tenantRepository.moveTenant(id,aptNum);
    }
    @Override
    public void deleteTenant(int id){
        try {
            tenantRepository.removeTenant(id);
        }catch (Exception ex){
            throw new IllegalStateException("Tenant not added");
        }
    }
}
