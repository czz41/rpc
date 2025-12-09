package com.zz.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.zz.common.model.User;
import com.zz.common.service.UserService;
import com.zz.model.RpcRequest;
import com.zz.model.RpcResponse;
import com.zz.serializer.JdkSerialize;
import com.zz.serializer.Serializer;

import java.io.IOException;

public class UserServiceProxy implements UserService {

    @Override
    public User getUser(User user) {
        //指定序列化器
        Serializer serializer=new JdkSerialize();
        //创建请求
        RpcRequest request=RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            //序列化请求
            byte[] bodyBytes=serializer.serialize(request);
            byte[] result;
            //这里用到hutool工具包
            //发送请求
            try(HttpResponse httpResponse= HttpRequest.post("http://localhost:8880")
                    .body(bodyBytes).
                    execute()){
                //接收响应
                result = httpResponse.bodyBytes();
            }
            //反序列化响应
            RpcResponse rpcResponse=serializer.deserialize(result,RpcResponse.class);
            //返回响应数据
            return (User) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
