// 包结构：com.example.ioc.order.bean
package com.example.ioc.order;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

// 定义公共接口
interface Bean {
    default String getType() { return "Bean"; }
}

// 实现类B（顺序2）
@Order(Ordered.HIGHEST_PRECEDENCE) // 显式最低优先级
@Component
class BeanB implements Bean {
    public BeanB() {
        System.out.println("BeanB initialized"); // 构造方法打印初始化日志
    }
}

// 实现类A（顺序1）
@Order(Ordered.LOWEST_PRECEDENCE) // 显式最高优先级
@Component
class BeanA implements Bean {
    public BeanA() {
        System.out.println("BeanA initialized"); // 构造方法打印初始化日志
    }
}

interface Service {

}

// 不同类型的ServiceA（顺序1）
@Order(1)
@Component
class ServiceA implements Service {
    public ServiceA() {
        System.out.println("ServiceA initialized"); // 构造方法打印初始化日志
    }
}

// 不同类型的ServiceB（顺序2）
@Order(2)
@Component
class ServiceB implements Service {
    public ServiceB() {
        System.out.println("ServiceB initialized"); // 构造方法打印初始化日志
    }
}

// 容器类，注入同一类型集合
@Component
class Container {
    // 只会影响到这里的顺序，而不会影响到构造方法的执行的顺序
    private final List<Bean> beanList;

    private final List<Service> serviceList;

    public Container(List<Bean> beanList, List<Service> serviceList) {
        this.beanList = beanList;
        this.serviceList = serviceList;
        // 打印集合顺序
        System.out.println("Bean list order:");
        beanList.forEach(bean -> System.out.println("- " + bean.getClass().getSimpleName()));

        System.out.println("\nService list order:");
        serviceList.forEach(bean -> System.out.println("- " + bean.getClass().getSimpleName()));
    }
}