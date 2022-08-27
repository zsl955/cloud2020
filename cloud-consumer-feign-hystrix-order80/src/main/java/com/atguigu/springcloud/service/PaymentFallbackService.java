package com.atguigu.springcloud.service;


import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentFeignService {
    @Override
    public String paymentInfo_ok(Integer id) {
        return "----PaymentFallbackService fall back +++paymentInfo_ok";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "----PaymentFallbackService fall back +++paymentInfo_TimeOut";
    }
}
