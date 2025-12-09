package com.zz.service;

import com.zz.common.service.UserService;
import com.zz.registry.LocalRegistry;
import com.zz.server.HttpServer;
import com.zz.server.VertxHttpServer;

public class provider {
    public static void main(String[] args){
        //把方法注册到本地注册中心
        LocalRegistry.registry(UserService.class.getName(), UserServiceImpl.class);
        //开启web服务器，持续监听
        HttpServer server=new VertxHttpServer();
        server.start(8880);
    }
}
