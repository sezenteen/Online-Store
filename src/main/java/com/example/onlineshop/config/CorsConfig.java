package com.example.onlineshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins(
//                        "http://localhost:64528/",  // Flutter running on localhost
//                        "http://10.0.2.2",         // Android emulator
//                        "http://192.168.1.7"       // Replace with your computer's IP for physical devices
//                )
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Adjust to match your endpoints
                        .allowedOrigins(
                        "http://localhost:64528/",  // Flutter running on localhost
                        "http://10.0.2.2",         // Android emulator
                       "http://192.168.1.7"       // Replace with your computer's IP for physical devices
               )
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}

