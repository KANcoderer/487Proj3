package com.example.rentalsystem.controller;

import com.example.rentalsystem.model.Request;
import com.example.rentalsystem.model.Tenant;
import com.example.rentalsystem.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
public class StaffController {
    private final RequestService requestService;

    public StaffController(RequestService requestService) {
        this.requestService = requestService;
    }

    @RequestMapping("/home/staff")
    public String home(Model model){
        List<Request> requests = requestService.filterRequests("","","","","Pending");
        model.addAttribute("requests", requests);
        return "staffHome";
    }
    @GetMapping("/request/complete/{requestId}")
    public String completeRequest(@PathVariable int requestId, Model model) {
        requestService.completeRequest(requestId);
        model.addAttribute("successMessage", "The request was successfully completed");
        return "redirect:/home/staff";
    }
    @RequestMapping("/requests/filter")
    public String filter(Model model, @RequestParam String reqAptNum, @RequestParam String reqArea,
                         @RequestParam String reqStartDate, @RequestParam String reqEndDate, @RequestParam String reqStatus) {
        try {
            List<Request> requests = requestService.filterRequests(reqAptNum, reqArea, reqStartDate, reqEndDate, reqStatus);
            model.addAttribute("requests", requests);

        }catch (Exception ex){

            model.addAttribute("errorMessage", ex.getMessage());
            List<Request> requests = requestService.filterRequests("","","","","Pending");;
            model.addAttribute("requests", requests);
        }

        return "staffHome";
    }
    @RequestMapping("/requests/reset")
    public String reset(Model model) {
        requestService.reset();
        return "redirect:/home/staff";
    }
}
