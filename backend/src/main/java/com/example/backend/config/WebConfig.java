package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;  // Add this import
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {  // Add @NonNull here
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173") // Adjust origin as needed
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
