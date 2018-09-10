package com.example.springbootdeep.configuration.com.example.springbootdeep.configuration;

import com.example.springbootdeep.configuration.com.example.springbootdeep.annotation.EnableHelloWorldSelector;
import com.example.springbootdeep.configuration.com.other.HelloWorldConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class HelloWorldConfigurationSeletor implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        Map<String, Object> attrs = annotationMetadata.getAnnotationAttributes(EnableHelloWorldSelector.class.getName());
        System.out.println("------------------?model-->" + attrs.get("model"));

        return new String[]{
                HelloWorldConfiguration.class.getName()
        };
    }
}
