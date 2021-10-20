package com.springcloud.controller;

import com.springcloud.entities.CommentResult;
import com.springcloud.entities.Payment;
import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    /**
     * 创建了8001和8002两个支付模块，在create返回的信息中加入端口号的信息，通过这个来看是否进行了负载均衡
     */
    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    private CommentResult<Integer> create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("*****插入结果: " + result);
        if (result > 0) {
            return new CommentResult<>(200, "插入数据成功"+serverPort, result);
        } else {
            return new CommentResult<>(444, "插入数据失败", null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    private CommentResult<Payment> create(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****查询结果: " + payment);
        if (payment != null) {
            return new CommentResult<>(200, "查询成功"+serverPort, payment);
        } else {
            return new CommentResult<>(444, "查询失败"+id, null);
        }
    }
}
