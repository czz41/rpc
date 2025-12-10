package com.zz.Proxy;

import java.lang.reflect.Proxy;

public class ServiceProxyFactory {
    public static <T> T getProxy(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                new ServiceProxy());
    }
}
