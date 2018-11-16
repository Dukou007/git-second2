package com.example;

import org.springframework.stereotype.Component;

@Component
public class OrderClientFallback implements OrderClient{

    @Override
    public String findOrderById(Long id) {
        return "this is fallback msg!!";
    }

}
