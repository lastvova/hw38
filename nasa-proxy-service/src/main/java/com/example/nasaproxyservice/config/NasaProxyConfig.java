package com.example.nasaproxyservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class NasaProxyConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
