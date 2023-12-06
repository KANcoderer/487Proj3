package com.example.rentalsystem.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class ImageConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        Path uploadDir= Paths.get("tenant-photos");
        String uploadPath=uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/tenant-photos/**").addResourceLocations("file:/"+uploadPath+"/");
    }
}
