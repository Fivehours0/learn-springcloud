package com.springcloud.controller;

import com.springcloud.entities.CommentResult;
import com.springcloud.entities.Payment;
import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    // 讲eureka中的服务发现的时候用到的知识点
    @Resource
    private DiscoveryClient discoveryClient;

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

    @GetMapping(value = "/payment/discovery")
    public Object discover() {
        List<String> services = discoveryClient.getServices();
        for (String service: services) {
            log.info(service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance: instances) {
            log.info(instance.getServiceId()+"\t"+
                    instance.getHost()+"\t"+
                    instance.getPort()+"\t"+
                    instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLb() {
        return serverPort;
    }
}
