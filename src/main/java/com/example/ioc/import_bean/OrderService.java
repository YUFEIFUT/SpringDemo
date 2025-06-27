package com.example.ioc.import_bean;

/**
 * OrderService类 - 将被@Import注解导入到Spring容器中
 */
public class OrderService {
    
    private String name = "OrderService";
    
    public OrderService() {
        System.out.println("OrderService 构造函数被调用");
    }
    
    public void processOrder() {
        System.out.println("Processing order from " + name);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
} 