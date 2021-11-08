package com.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public String paymentSql(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(serverUrl+"/paymentSQL/"+id, String.class);
    }

    @RequestMapping("/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback") // value是资源名，保证唯一就可以，这里没有配置一些额外的参数
    @SentinelResource(value = "fallback", fallback = "handlerFallBack") // value是资源名，保证唯一就可以，这里没有配置一些额外的参数
    public String fallback(@PathVariable("id") Long id) {
        if (id >= 4 || id < 0) {
            throw new IllegalArgumentException("非法参数异常");
        }
        return restTemplate.getForObject(serverUrl+"/paymentSQL/"+id, String.class);
    }

    public String handlerFallBack(@PathVariable("id") Long id, Throwable e) {
        return "this is handlerFallBack";
    }
}
