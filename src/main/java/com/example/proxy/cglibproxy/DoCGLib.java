package com.example.proxy.cglibproxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


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

// Cglib代理类，实现MethodInterceptor接口
class CglibProxy implements MethodInterceptor {
    private final Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
        // 设置需要创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        // 通过字节码技术动态创建子类实例
        return enhancer.create();
    }

    // 实现MethodInterceptor接口方法
    @Override
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        System.out.println("前置代理");
        // 通过代理类调用父类中的方法
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("后置代理");
        return result;
    }

}

public class DoCGLib {
    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        // 通过生成子类的方式创建代理类
        UserServiceImpl proxyImp = (UserServiceImpl) proxy.getProxy(UserServiceImpl.class);
        proxyImp.add();
    }
}