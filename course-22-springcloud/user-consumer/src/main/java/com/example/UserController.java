package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/uapi")
public class UserController {

    /**
     * 在application中配置注入bean了
     */
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient client;

    @Autowired
    OrderClient orderClient;

    /**
     * 第一种方式，直接使用固定ip
     * @return
     */
    @GetMapping("/test")
    public Object test() {
        RestTemplate template = new RestTemplate();
        String order = template.getForObject("http://localhost:8080/order/1", String.class);
        return order;
    }

    /**
     * 第二种，利用LoadBalancerClient从注册中心获取url和host
     * @return
     */
    @GetMapping("/test2")
    public Object test2() {
        RestTemplate template = new RestTemplate();
        ServiceInstance serviceInstance = client.choose("ORDER");
        String url = String.format("http://%s:%s/order/2", serviceInstance.getHost(), serviceInstance.getPort());
        String order = template.getForObject(url, String.class);
        return order;
    }

    /**
     * 第三种，使用注解@LoadBalanced
     * @return
     */
    @HystrixCommand(fallbackMethod = "test3fallback")
    @GetMapping("/test3")
    public Object test3() {
        int i = 1 / 0;
        String order = restTemplate.getForObject("http://ORDER/order/3", String.class);
        return order;
    }

    /**
     * 第四种，使用feignclient
     * @return
     */
    @GetMapping("/test4")
    public Object test4() {
        String order = orderClient.findOrderById(4L);
        return order;
    }

    public Object test3fallback(Throwable e) {
        e.printStackTrace();
        return "this is test3fallback fallback msg" + e.getMessage();
    }

}
