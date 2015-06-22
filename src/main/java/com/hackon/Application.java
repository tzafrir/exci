package com.hackon;

import org.springframework.boot.SpringApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
// import org.springframework.boot.ResourceHandlerRegistry;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableWebMvc
public class Application extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry
        .addResourceHandler("/**")
        .addResourceLocations("/resources/","classpath:/other-resources/");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
