package com.ecommerce.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig {

    private static final String GET ="GET";
    private static final String POST ="POST";
    private static final String DELETE ="DELETE";
    private static final String PUT ="PUT";

    @Bean
    public WebMvcConfigurer coRS_config(){

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                WebMvcConfigurer.super.addCorsMappings(registry);
                registry.addMapping("/**"). allowedMethods(GET,PUT,POST,DELETE).maxAge(3600L)
                        .allowedHeaders("*")
                        .allowedOriginPatterns("*").exposedHeaders("Authorization")
                        .allowCredentials(true);

            }
        };
    }

    //WebMvcConfigurer.super.addCorsMappings(registry);
}
