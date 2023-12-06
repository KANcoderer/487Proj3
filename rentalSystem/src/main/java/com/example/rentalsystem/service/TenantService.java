package com.example.rentalsystem.service;

import com.example.rentalsystem.model.Tenant;


import java.util.List;

public interface TenantService {
    List<Tenant> getTenants();
    void addTenant(String name, String phoneNum, String email, String checkIn, String checkOut, int aptNum);
    void moveTenant(int id, int aptNum);
    void deleteTenant(int id);
    Tenant getTenantById(int id);
}
