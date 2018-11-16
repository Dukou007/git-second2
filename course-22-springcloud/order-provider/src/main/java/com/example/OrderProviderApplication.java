package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OrderProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderProviderApplication.class, args);
    }
}
