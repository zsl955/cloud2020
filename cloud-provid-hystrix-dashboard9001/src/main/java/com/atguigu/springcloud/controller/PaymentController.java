package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverport;

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id){
       return paymentService.paymentInfo_ok(id);
    }


    @GetMapping(value = "/payment/hystrix/out/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        return paymentService.paymentInfo_TimeOut(id);
    }




    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String result = paymentService.paymentCircuitBreaker(id);
        log.info(result);
        return result;
    }











    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    private CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("*******插入结果****"+ result);
        if(result > 0){
            return new CommonResult(200,"插入成功,serverport:"+serverport,result);
        }else {
            return new CommonResult(400,"插入错误",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    private CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*******插入结果****"+ payment);
        if(payment != null){
            return new CommonResult(200,"查询成功,serverport:"+serverport,payment);
        }else {
            return new CommonResult(400,"没有对应记录，查询Id"+id,null);
        }
    }






    @GetMapping(value = "/payment/discovery")
    private Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("#####service:" + service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());

        }
        return this.discoveryClient;
    }

}
