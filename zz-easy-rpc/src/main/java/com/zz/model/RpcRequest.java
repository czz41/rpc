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
public class RpcRequest implements Serializable {
    //服务名称
    private String serviceName;
    //方法名称
    private String methodName;
    //参数类型
    private Class<?>[] parameterTypes;
    //参数对象
    private Object[] args;
}
