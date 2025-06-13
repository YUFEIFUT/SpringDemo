package com.example.aop.limitations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
===== 1. 测试公共方法 =====
AOP拦截: DemoService.publicMethod()
执行publicMethod

===== 2. 测试私有方法 =====
AOP拦截: DemoService.callPrivateMethod()
执行privateMethod

===== 3. 测试静态方法 =====
执行staticMethod

===== 4. 测试final方法 =====
执行finalMethod

===== 5. 测试内部类方法 =====
AOP拦截: DemoService.callInnerClassMethod()
执行innerMethod

===== 6. 测试类内部自调用 =====
AOP拦截: DemoService.selfInvocation()
执行selfInvocation，将调用publicMethod
执行publicMethod

对于 static 标识的是很明显的，没什么好说的，因为直接是类的方法，不是代理对象的，甚至都不是 DemoService 对象本身的方法，所以没得说

内部调用方法值得一提：
public void callPrivateMethod() {
    this.privateMethod();
}

对于这个，我的分析是：切面是环绕的callPrivateMethod方法，对于public修饰的，非final，static的方法，是会执行切面的环绕逻辑的，但是执行 this.privateMethod(); 这一句的时候，可以debug看一下，主要是因为调用的主体对象变了，this指的就是 DemoService 本身，而不是代理对象，所以此时在内部调用肯定是不会走切面了

final的话，似乎如果使用 jdk 来代理的话，final是在技术上是可以代理到的，但是为什么不行，我问了以下 AI，说是 spring 的设计上限制了，就是说 spring 主动限制了不代理 final 的方法

调用内部类的方法的话，我写的demo是在一个public方法上自己new了一个内部类，所以这个内部类根本就不会被代理到，就算有它的代理对象，也不是我自己new出来的这个【这个似乎没有很好地证明啊，哈哈哈哈】
 */
public class AopLimitationsDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AopLimitationsConfig.class);

        DemoService demoService = context.getBean(DemoService.class);

        System.out.println("===== 1. 测试公共方法 =====");
        demoService.publicMethod();

        System.out.println("\n===== 2. 测试私有方法 =====");
        demoService.callPrivateMethod();  // 改为通过公共方法调用私有方法

        System.out.println("\n===== 3. 测试静态方法 =====");
        DemoService.staticMethod();

        System.out.println("\n===== 4. 测试final方法 =====");
        demoService.finalMethod();

        System.out.println("\n===== 5. 测试内部类方法 =====");
        demoService.callInnerClassMethod();

        System.out.println("\n===== 6. 测试类内部自调用 =====");
        demoService.selfInvocation();

        context.close();
    }
}