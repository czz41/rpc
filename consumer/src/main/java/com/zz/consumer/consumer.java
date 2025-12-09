package com.zz.consumer;

import com.zz.common.model.User;
import com.zz.common.service.UserService;

public class consumer {
    public static void main(String[] args) {
        User user=new User();
        user.setName("zz");
        //TTDO 需要改造成真正可以调用的userService
        UserService userService=null;
        User result=userService.getUser(user);
        if(result==null)
        {
            System.out.println("rpc调用失败");
        }
        else
        {
            System.out.println("rpc调用成功"+result.getName());
        }
    }
}
