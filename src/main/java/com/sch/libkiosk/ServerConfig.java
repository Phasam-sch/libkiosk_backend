package com.sch.libkiosk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ServerConfig implements WebMvcConfigurer {

    @Value("${connect.dir}")
    private String connectPath;
    @Value("${userPics.dir}")
    private  String userPicsPath;


   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
       registry.addResourceHandler(connectPath)
               .addResourceLocations(userPicsPath);
   }

}
