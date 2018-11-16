package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class ZuulGatewayApplication {

    @Bean
    AccessFilter accessFilter() {
        return new AccessFilter();
    }

    @Bean
    RateLimiterFilter rateLimiterFilter() {
        return new RateLimiterFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);
    }
}
