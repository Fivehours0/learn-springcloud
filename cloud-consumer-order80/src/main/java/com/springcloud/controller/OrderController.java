package com.springcloud.controller;

import com.springcloud.entities.CommentResult;
import com.springcloud.entities.Payment;
import com.springcloud.lb.LoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

//    private final String PAYMENT_URL = "http://localhost:8001";
    private final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalance loadBalance;
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public ResponseEntity<CommentResult> create(Payment payment) {
        // 调用服务的url地址     返回类型    参数
        // RestTemplate: spring提供的访问Rest服务的客户端模板工具集
        return restTemplate.postForEntity(PAYMENT_URL+"/payment/create", payment, CommentResult.class);
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommentResult getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id, CommentResult.class);
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommentResult getPayment2(@PathVariable("id") Long id) {
        // getForEntity返回对象为ResponseEntity，包含了响应中的一些重要信息，比如响应头，响应状态码等
        ResponseEntity<CommentResult> result =  restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id, CommentResult.class);
        // 判断状态码，2xx表示成功
        if (result.getStatusCode().is2xxSuccessful()) {
            return result.getBody();
        } else {
            return new CommentResult(444, "操作失败");
        }
    }

    @GetMapping("/consumer/payment/lb")
    public String getPaymentLb() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0) {
            return null;
        }
        ServiceInstance instance = loadBalance.instance(instances);
        URI uri = instance.getUri();
        return restTemplate.getForObject(uri+"/payment/lb", String.class);
    }

    @GetMapping(value = "/consumer/payment/zipkin")
    public String paymentZipkin() {
        return restTemplate.getForObject("http://localhost:8001"+"/payment/zipkin/", String.class);
    }
}
