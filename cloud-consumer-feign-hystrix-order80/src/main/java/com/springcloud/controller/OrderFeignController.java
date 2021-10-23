package com.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.springcloud.entities.CommentResult;
import com.springcloud.entities.Payment;
import com.springcloud.feign.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "paymentGlobalFallbackMethod")
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfoOk(@PathVariable("id") Integer id) {
        return paymentFeignService.paymentInfoOk(id);
    }
//     该方法是之前测试feign的
//    @GetMapping("/payment/hystrix/timeout/{id}")
//    public String paymentInfoTimeOut(@PathVariable("id") Integer id) {
//        return paymentFeignService.paymentInfoTimeOut(id);
//    }

    // 该方法是测试hystrix的服务降级的
//    @HystrixCommand(fallbackMethod = "paymentInfoTimeOutHandler", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
//    })
    @HystrixCommand
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfoTimeOut(@PathVariable("id")Integer id) {
        // 报错跳至fallback方法
//        int a = 10/0;
        return paymentFeignService.paymentInfoTimeOut(id);
    }

    public String paymentInfoTimeOutHandler(Integer id) {
        return "我是消费者80，线程池："+ Thread.currentThread().getName() + " paymentInfoTimeOutFallBack, id:" + id;
    }

    // 下面是全局的fallback
    public String paymentGlobalFallbackMethod() {
        return "Global异常处理信息，请稍后再试";
    }
}
