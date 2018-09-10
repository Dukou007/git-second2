package com.example.springbootdeep.configuration.com.example.springbootdeep.annotation;

import com.example.springbootdeep.configuration.com.example.springbootdeep.configuration.HelloWorldConfigurationSeletor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({HelloWorldConfigurationSeletor.class})
public @interface EnableHelloWorldSelector {

    String model() default "first";



}
