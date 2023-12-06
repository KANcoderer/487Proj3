package com.example.rentalsystem.controller;

import com.example.rentalsystem.model.Tenant;
import com.example.rentalsystem.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class TenantController {
    private final RequestService requestService;
    public TenantController(RequestService requestService){this.requestService=requestService;}
    @RequestMapping("/home/tenant")
    public String home(Model model){
        return "addRequest";
    }
    @PostMapping("/request/add")
    public String addRequestSubmit(Model model, @RequestParam String requestAptNum, @RequestParam String requestArea, @RequestParam String requestDescription,@RequestParam MultipartFile requestImage) {

        try {

            if(!requestImage.isEmpty()){
                InputStream inputStream=requestImage.getInputStream();
                String imageName= StringUtils.cleanPath(requestImage.getOriginalFilename());
                Timestamp ts=new Timestamp(System.currentTimeMillis());
                requestService.addRequest(Integer.parseInt(requestAptNum),requestArea,requestDescription,ts,imageName);
                int id= requestService.getMaxId();
                if(id==-1){
                    throw new Exception();
                }
                String uploadDir="tenant-photos/"+id;
                Path uploadPath= Paths.get(uploadDir);
                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }
                Path filePath=uploadPath.resolve(imageName);
                Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);

            }else{

                Timestamp ts=new Timestamp(System.currentTimeMillis());
                requestService.addRequest(Integer.parseInt(requestAptNum),requestArea,requestDescription,ts,null);
            }


        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            ex.printStackTrace();
            return "addRequest";
        }

        model.addAttribute("successMessage", "The Request was successfully saved");
        return "addRequest";
    }
}
