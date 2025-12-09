package com.zz.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalRegistry {
    private static final Map<String,Object> registry=new ConcurrentHashMap<>();

    public static void registry(String name,Object obj)
    {
        registry.put(name, obj);
    }

    public static Object get(String name)
    {
        return registry.get(name);
    }

    public static void remove(String name)
    {
        registry.remove(name);
    }
}
