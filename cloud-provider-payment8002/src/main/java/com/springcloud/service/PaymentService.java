package com.springcloud.service;

import com.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    /**
     * 插入一个payment
     */
    public int create(Payment payment);

    /**
     * 根据id查询payment
     * @param id 支付订单的id
     * @return 查询到的支付订单记录
     */
    public Payment getPaymentById(@Param("id") Long id);
}
