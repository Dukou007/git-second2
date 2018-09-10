package com.example.springbootdeep.configuration.com.example.springbootdeep.annotation;

import com.example.springbootdeep.configuration.com.other.HelloWorldConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({HelloWorldConfiguration.class})
public @interface EnableHelloWorld {
}
