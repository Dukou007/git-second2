package com.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
//@FeignClient(name = "ORDER", fallback = OrderClientFallback.class)
@FeignClient(name = "ORDER", fallbackFactory = OrderClientFallbackFactory.class)
public interface OrderClient {

    @GetMapping("/order/{id}")
    String findOrderById(@PathVariable("id") Long id);
}
