package com.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderNacosController {
    // 之前是使用这种方式配的
//    private final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    // 也可以使用如下的方式
    @Value("${service-url.nacos-user-service}")
    private String serverUrl;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/payment/nacos/{id}")
    public String getPaymentLb(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(serverUrl+"/payment/nacos/"+id, String.class);
    }
}
