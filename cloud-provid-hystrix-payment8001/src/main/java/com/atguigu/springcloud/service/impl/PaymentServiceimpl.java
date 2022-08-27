package com.atguigu.springcloud.service.impl;


import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.dao.PaymentDao;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceimpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }


    public String paymentInfo_ok(Integer id){
        return "线程池   "+ Thread.currentThread().getName() + "   paymentInfo_ok,id:   "+ id + "\t"+"米好啊哈哈";

    }


    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOut_Handle",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id){
        int timeNumber = 2;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "线程池   "+ Thread.currentThread().getName() + "   paymentInfo_TimeOut,id:   "+ id + "\t"+"哭/(ㄒoㄒ)/~~" + "耗时"+timeNumber+"秒钟";

    }

    public String paymentInfo_TimeOut_Handle(Integer id){
        return "线程池   "+ Thread.currentThread().getName() + "   paymentInfo_TimeOut_Handle,id:   "+ id + "\t"+"哭/(ㄒoㄒ)/~~" + "嘿嘿嘿";

    }



    @HystrixCommand(fallbackMethod = "paymentCircuitbreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id < 0){
            throw new RuntimeException("###### id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：  " + serialNumber;

    }

    public String paymentCircuitbreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数，请稍后重试， /(ㄒoㄒ)/~~  id:  " +
                id;
    }



}
