package com.example.proxy.jdkdynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 假设这是UserService接口的定义
interface UserService {
    void add();
}

// UserServiceImpl实现了UserService接口
class UserServiceImpl implements UserService {
    @Override
    public void add() {
        System.out.println("--------------------add----------------------");
    }
}

// 实现InvocationHandler接口来创建代理逻辑
class MyInvocationHandler implements InvocationHandler {
    private final Object target;

    public MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 这里假设 performanceMonitor 类已经实现，用于性能监控
//        performanceMonitor.begin(target.getClass().getName() + "." + method.getName());
        System.out.println("-----------------begin "+method.getName()+"-----------------");
        Object result = method.invoke(target, args);
        System.out.println("-----------------end "+method.getName()+"-----------------");
//        performanceMonitor.end();
        return result;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(), this);
    }
}

// 用于性能监控的类，目前只是空实现
class performanceMonitor {
    public static void begin(String method) {
        // 这里可以添加开始性能监控的逻辑
        System.out.println("begin: " + method);
    }

    public static void end() {
        // 这里可以添加结束性能监控的逻辑
    }
}

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        MyInvocationHandler handler = new MyInvocationHandler(service);
        UserService proxy = (UserService) handler.getProxy();
        proxy.add();
    }
}