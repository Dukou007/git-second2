package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OrderFeign {

    @GetMapping("/order/{id}")
    public String findOrderById(@PathVariable Long id, HttpServletRequest request) {
//        if(1==1) throw new RuntimeException("我自定义报错了！！");
        return "this is order-" + id + "-- 端口--" + request.getServerPort();
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

}
