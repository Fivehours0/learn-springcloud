package com.springcloud.dao;

import com.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentDao {
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
