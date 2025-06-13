package com.example.aop.limitations;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

    // 在DemoService中添加
    public void callPrivateMethod() {
        this.privateMethod();
    }

    // 1. 公共方法 - AOP应该正常工作
    public void publicMethod() {
        System.out.println("执行publicMethod");
    }

    // 2. 私有方法 - AOP不会生效
    private void privateMethod() {
        System.out.println("执行privateMethod");
    }

    // 3. 静态方法 - AOP不会生效
    public static void staticMethod() {
        System.out.println("执行staticMethod");
    }

    // 4. final方法 - AOP不会生效
    public final void finalMethod() {
        System.out.println("执行finalMethod");
    }

    // 5. 内部类方法 - AOP不会生效
    public void callInnerClassMethod() {
        new InnerClass().innerMethod();
    }

    // 6. 类内部自调用 - AOP不会生效
    public void selfInvocation() {
        System.out.println("执行selfInvocation，将调用publicMethod");
        this.publicMethod(); // 自调用，AOP不会生效
    }

    // 内部类
    class InnerClass {
        public void innerMethod() {
            System.out.println("执行innerMethod");
        }
    }
}