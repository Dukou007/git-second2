package com.other;

import com.example.springbootdeep.configuration.com.example.springbootdeep.annotation.ConditionalOnSystem;
import com.example.springbootdeep.configuration.com.example.springbootdeep.configuration.SayHelloWorld;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HelloWorldConfiguration {

    @Bean
    @ConditionalOnSystem(system = "linux")
    SayHelloWorld sayHelloWorld() {

        System.out.println("---------------》加载sayhelloworld");

        return new SayHelloWorld();
    }

}
