package com.zz.consumer;

import com.zz.Proxy.ServiceProxyFactory;
import com.zz.common.model.User;
import com.zz.common.service.UserService;

public class consumer {
    public static void main(String[] args) {
        User user=new User();
        user.setName("zz");
        //TTDO 需要改造成真正可以调用的userService
        //这里现在使用的是静态代理
        UserService userService= ServiceProxyFactory.getProxy(UserService.class);
        User result=userService.getUser(user);
        if(result==null)
        {
            System.out.println("rpc调用失败");
        }
        else
        {
            System.out.println("调用了在Provider中的userService的实现类："+userService.getMessage());
            System.out.println("rpc调用成功"+result.getName());
        }
    }
}
