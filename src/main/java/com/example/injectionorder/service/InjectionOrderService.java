package com.example.injectionorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InjectionOrderService {

    // 字段注入
    @Autowired
    private DependencyA fieldInjectedDependency;

    // Setter注入
    private DependencyB setterInjectedDependency;

    // 构造器注入
    private final DependencyC constructorInjectedDependency;

    @Autowired
    public InjectionOrderService(DependencyC constructorInjectedDependency) {
        this.constructorInjectedDependency = constructorInjectedDependency;
        System.out.println("1. 构造器注入完成 - 在对象创建时执行");
    }

    @Autowired
    public void setSetterInjectedDependency(DependencyB setterInjectedDependency) {
        this.setterInjectedDependency = setterInjectedDependency;
        System.out.println("2. Setter注入完成 - 在属性填充阶段执行");
    }

    @PostConstruct
    public void init() {
        System.out.println("3. @PostConstruct 方法执行 - 验证所有注入已完成");
        System.out.println("字段注入是否完成: " + (fieldInjectedDependency != null));
        System.out.println("Setter注入是否完成: " + (setterInjectedDependency != null));
        System.out.println("构造器注入是否完成: " + (constructorInjectedDependency != null));
    }

    // 显式声明字段注入的setter方法（仅用于演示，实际不需要）
    @Autowired
    public void setFieldInjectedDependency(DependencyA fieldInjectedDependency) {
        this.fieldInjectedDependency = fieldInjectedDependency;
        System.out.println("2. 字段注入完成 - 在属性填充阶段执行（通过反射直接设置字段）");
    }
}