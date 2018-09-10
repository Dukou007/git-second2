package com.example.springbootdeep.configuration.com.example.springbootdeep.annotation;


import com.example.springbootdeep.configuration.com.example.springbootdeep.configuration.OnSystemCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional({OnSystemCondition.class})
public @interface ConditionalOnSystem {

    String system();

}
