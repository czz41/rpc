package com.zz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RpcResponse implements Serializable {
    //响应数据
    private Object Data;
    //响应类型
    private Class<?> dataType;
    //响应信息
    private String message;
    //响应异常(Throwable是它的父类，为所有异常，这个是可处理异常)
    private Exception exception;
}
