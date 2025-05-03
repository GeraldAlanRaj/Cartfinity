package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // CORS Configuration
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173") // Adjust origin as needed
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }

            @Override
            public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
                // Serve static images from /static/images/ or any custom path
                registry.addResourceHandler("/images/**")
                        .addResourceLocations("classpath:/static/images/");
                
            }
        };
    }
}
