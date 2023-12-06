package com.example.rentalsystem.repository.impl;

import com.example.rentalsystem.database.TenantDB;
import com.example.rentalsystem.model.Tenant;
import com.example.rentalsystem.repository.TenantRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class TenantRepositoryImpl implements TenantRepository {
    private List<Tenant> tenants;

    public TenantRepositoryImpl(){tenants=new ArrayList<>();}
    @Override
    public List<Tenant> getTenants(){return tenants;}
    @Override
    public void addTenant(Tenant tenant){
        tenants.add(tenant);
    }
    @Override
    public Tenant getTenantById(int id){
        var filtered = tenants.stream().filter(t -> Objects.equals(t.getTenantId(), id)).collect(Collectors.toList());
        if (filtered.size() > 0) {
            return filtered.get(0);
        }
        throw new IllegalStateException("tenant not found with ID " + id);
    }
    @Override
    public void moveTenant(int id, int aptNum){

        try{
            for (Tenant value : tenants) {
                if (value.getTenantId() != id && value.getAptNum() == aptNum) {
                    throw new IllegalStateException("Apartment already in use");
                }
            }
            Tenant tenant=getTenantById(id);
            tenant.setAptNum(aptNum);
            TenantDB.moveTenant(id,aptNum);
        }catch (SQLException sqle){
            throw new IllegalStateException("Tenant not moved");
        }

    }
    @Override
    public void removeTenant(int id){
        try {
            tenants.remove(getTenantById(id));
            TenantDB.removeTenant(id);
        }catch (SQLException sqle){
            throw new IllegalStateException("Tenant not deleted");
        }
    }

}
