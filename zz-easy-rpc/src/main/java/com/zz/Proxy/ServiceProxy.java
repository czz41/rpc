package com.zz.Proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.zz.model.RpcRequest;
import com.zz.model.RpcResponse;
import com.zz.serializer.JdkSerialize;
import com.zz.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Serializer serializer=new JdkSerialize();
        RpcRequest request= RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try{
            byte[] bodyBytes=serializer.serialize(request);
            byte[] result;
            try(HttpResponse httpResponse= HttpRequest.post("http://localhost:8880")
                    .body(bodyBytes).
                    execute()){
                result = httpResponse.bodyBytes();
                RpcResponse rpcResponse=serializer.deserialize(result,RpcResponse.class);
                return rpcResponse.getData();
            }
        }catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("动态代理内部调用rpc失败");
        }
        return null;
    }
}
