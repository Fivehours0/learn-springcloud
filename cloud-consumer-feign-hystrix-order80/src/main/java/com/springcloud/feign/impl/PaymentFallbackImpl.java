package com.springcloud.feign.impl;

import com.springcloud.feign.PaymentFeignService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackImpl implements PaymentFeignService {
    @Override
    public String paymentInfoOk(Integer id) {
        return "paymentInfoOk  ---------- fallback";
    }

    @Override
    public String paymentInfoTimeOut(Integer id) {
        return "paymentInfoTimeOut   ---------------- fallback";
    }
}
