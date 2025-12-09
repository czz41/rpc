package com.zz.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalRegistry {
    private static final Map<String,Class<?>> registry=new ConcurrentHashMap<>();

    public static void registry(String name,Class<?> clazz)
    {
        registry.put(name, clazz);
    }

    public static Class<?> get(String name)
    {
        return registry.get(name);
    }

    public static void remove(String name)
    {
        registry.remove(name);
    }
}
