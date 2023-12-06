package com.example.rentalsystem.controller;

import com.example.rentalsystem.model.Tenant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class IndexController {

    public IndexController() {

    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/home")
    public String home(Model model, @RequestParam String user){
        switch (user){
            case "Tenant"->{return "redirect:/home/tenant";}
            case "Staff"->{return "redirect:/home/staff";}
            case "Manager"->{
                return "redirect:/home/manager";
            }
            default -> {return "index";}
        }
    }


}

