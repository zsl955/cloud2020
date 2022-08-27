package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author lixiaolong
 * @date 2020/12/19 14:12
 * @description 注册中心
 */
@SpringBootApplication
@EnableEurekaClient
public class ConfigCenter3355 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenter3355.class, args);
        System.out.println("启动成功");
    }
}
