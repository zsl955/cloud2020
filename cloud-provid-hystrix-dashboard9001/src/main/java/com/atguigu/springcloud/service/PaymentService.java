package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);

    public String paymentInfo_ok(Integer id);

    public String paymentInfo_TimeOut(Integer id);

    public String paymentInfo_TimeOut_Handle(Integer id);


    public String paymentCircuitBreaker(@PathVariable("id") Integer id);


    public String paymentCircuitbreaker_fallback(@PathVariable("id") Integer id);



}
