package com.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/payment/nacos/{id}")
    public String getPaymentLb(@PathVariable("id") Integer id) {
        return "nacos registry, serverPort: " + serverPort + "\t id:" + id;
    }

    public static HashMap<Long, String> map = new HashMap<>();
    static {
        map.put(1L, "dsdsdsdsdsdsd1111111");
        map.put(2L, "sgsdgsdgsgdgsd2222222");
        map.put(3L, "afherhaefdsdsd3333333");
    }

    @GetMapping(value = "/paymentSQL/{id}")
    public String paymentSql(@PathVariable("id") Long id) {
        return map.get(id) + "\t" + serverPort;
    }
}
