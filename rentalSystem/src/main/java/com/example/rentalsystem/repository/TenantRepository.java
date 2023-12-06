package com.example.rentalsystem.repository;

import com.example.rentalsystem.model.Tenant;

import java.util.List;

public interface TenantRepository {
    List<Tenant> getTenants();
    void addTenant(Tenant tenant);
    Tenant getTenantById(int id);
    void moveTenant(int id, int aptNum);
    void removeTenant(int id);
}
