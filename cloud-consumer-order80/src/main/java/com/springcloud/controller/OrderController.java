package com.springcloud.controller;

import com.springcloud.entities.CommentResult;
import com.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Controller
@Slf4j
public class OrderController {

//    private final String PAYMENT_URL = "http://localhost:8001";
    private final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public ResponseEntity<CommentResult> create(Payment payment) {
        // 调用服务的url地址     返回类型    参数
        // RestTemplate: spring提供的访问Rest服务的客户端模板工具集
        return restTemplate.postForEntity(PAYMENT_URL+"/payment/create", payment, CommentResult.class);
    }
    @GetMapping("consumer/payment/get/{id}")
    public ResponseEntity<CommentResult> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id, CommentResult.class);
    }
}
