package com.atm.frontendservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced // 👈 importante: para usar serviceId en vez de IP o host directo
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}