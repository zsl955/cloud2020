package com.atguigu.springcloud.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private Integer code;
    private String messag;
    private T data;

    public CommonResult(Integer code, String messag) {
        this.code = code;
        this.messag = messag;
    }
}
