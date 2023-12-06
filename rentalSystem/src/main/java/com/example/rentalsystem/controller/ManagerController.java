package com.example.rentalsystem.controller;

import com.example.rentalsystem.model.Tenant;
import com.example.rentalsystem.service.TenantService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@Controller
public class ManagerController {
    private final TenantService tenantService;
    public ManagerController(TenantService tenantService){
        this.tenantService=tenantService;
    }
    @RequestMapping("/home/manager")
    public String home(Model model){
        List<Tenant> tenants = tenantService.getTenants();
        model.addAttribute("tenants", tenants);
        return "managerHome";
    }
    @RequestMapping("/tenant/delete/{tenantId}")
    public String removeTenantView(@PathVariable int tenantId, Model model) {
        try {
            tenantService.deleteTenant(tenantId);
            List<Tenant> tenants = tenantService.getTenants();
            model.addAttribute("tenants", tenants);
        }catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "managerHome";
        }
        return "managerHome";
    }
    @GetMapping("/tenant/add")
    public String addTenantView(Model model) {
        return "addTenant";
    }
    @PostMapping("/tenant/add")
    public String addTenantSubmit(Model model, @RequestParam String tenantName, @RequestParam String tenantPhoneNum, @RequestParam String tenantEmail, @RequestParam String tenantCheckIn, @RequestParam String tenantCheckOut, @RequestParam String tenantAptNum) {
        try {
            tenantService.addTenant(tenantName, tenantPhoneNum, tenantEmail,tenantCheckIn,tenantCheckOut,Integer.parseInt(tenantAptNum));
        } catch (Exception ex) {

            model.addAttribute("errorMessage", ex.getMessage());
            return "addTenant";
        }

        model.addAttribute("successMessage", "The Tenant was successfully saved");
        return "addTenant";
    }
    @GetMapping("/tenant/move/{tenantId}")
    public String moveTenantView(@PathVariable int tenantId, Model model) {
        Tenant tenant = tenantService.getTenantById(tenantId);
        model.addAttribute("tenantAptNum", tenant.getAptNum());
        return "moveTenant";
    }
    @PostMapping("/tenant/move")
    public String moveTenantSubmit(Model model, @RequestParam Integer tenantId,  @RequestParam String tenantAptNum) {
        try {
            if(tenantId==null||tenantAptNum==null){
                throw new Exception();
            }
            tenantService.moveTenant(tenantId, Integer.parseInt(tenantAptNum));
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "moveTenant";
        }
        model.addAttribute("successMessage", "The Tenant was successfully saved");
        return "redirect:/home/manager";
    }
}
