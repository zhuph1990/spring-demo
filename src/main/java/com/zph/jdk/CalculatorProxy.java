package com.zph.jdk;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CalculatorProxy {

    public static Object getProxy(Object Object) {

        //获取类加载器
        ClassLoader classLoader = Object.getClass().getClassLoader();
        //获取要实现的接口
        Class<?>[] interfaces = Object.getClass().getInterfaces();

        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {

                Object result = null;
                try {
                    result = method.invoke(Object, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        };
        Object obj = Proxy.newProxyInstance(classLoader, interfaces, handler);
        return obj;
    }
}
