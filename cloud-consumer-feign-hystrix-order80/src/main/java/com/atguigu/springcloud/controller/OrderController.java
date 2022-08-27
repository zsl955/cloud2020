package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Slf4j
public class OrderController {


    @Autowired
    private PaymentFeignService paymentFeignService;



    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id){
        return paymentFeignService.paymentInfo_ok(id);
    }


    @GetMapping(value = "/consumer/payment/hystrix/out/{id}")
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOut_metho",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        return paymentFeignService.paymentInfo_TimeOut(id);
    }


    public String paymentInfo_TimeOut_metho(Integer id){
        return "线程池   "+ Thread.currentThread().getName() + " 80d  paymentInfo_TimeOut_Handle,id:   "+ id + "\t"+"哭/(ㄒoㄒ)/~~" + "zzz";
    }







}

