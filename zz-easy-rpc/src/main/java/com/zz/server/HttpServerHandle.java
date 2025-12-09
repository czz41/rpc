package com.zz.server;

import cn.hutool.http.HttpResponse;
import com.zz.model.RpcRequest;
import com.zz.model.RpcResponse;
import com.zz.registry.LocalRegistry;
import com.zz.serializer.JdkSerialize;
import com.zz.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import javax.imageio.IIOException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLOutput;

public class HttpServerHandle implements Handler<HttpServerRequest> {
    @Override
    public void handle(HttpServerRequest request) {
        //指定序列化器
        Serializer serializer=new JdkSerialize();
        //接收请求
        System.out.println("接收请求"+request.method()+request.uri());
        request.bodyHandler(body->{
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                //返序列化请求
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 构造响应结果对象
            RpcResponse rpcResponse = new RpcResponse();
            // 如果请求为 null，直接返回
            if (rpcRequest == null) {
                rpcResponse.setMessage("rpcRequest is null");
                doResponse(request, rpcResponse, serializer);
                return;
            }
            try{
                //获得类对象
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Object impl = implClass.newInstance();
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                // 封装返回结果
                rpcResponse.setData(method.invoke(impl, rpcRequest.getArgs()));
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            }catch(Exception e){
                e.printStackTrace();
                //如果出现异常封装异常还有消息
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            // 响应
            doResponse(request, rpcResponse, serializer);
        });
    }


    void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response()
                .putHeader("content-type", "application/json");
        try {
            //序列化
            byte[] serialized = serializer.serialize(rpcResponse);
            //返回
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}
