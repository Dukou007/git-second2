package com.example;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderClientFallbackFactory implements FallbackFactory<OrderClient> {
    @Override
    public OrderClient create(Throwable throwable) {

        return new OrderClient() {

            @Override
            public String findOrderById(Long id) {

                System.out.println(throwable.toString());
                return "this is fallbackFackory msg~~";
            }

        };
    }
}
