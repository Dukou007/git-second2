package com.example.springbootdeep;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SpringBootDeepApplication {



    public static void main(String[] args) {

        new SpringApplication(SpringBootDeepApplication.class).run(args);

    }
}
