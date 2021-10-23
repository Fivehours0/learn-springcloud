package com.springcloud.service;


public interface PaymentService {
    String paymentInfoOk(Integer id);
    String paymentInfoTimeOut(Integer id);
    String paymentCircuitBreaker(Integer id);
}
