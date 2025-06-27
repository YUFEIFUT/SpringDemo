package com.example.ioc.import_bean;

/**
 * UserService类 - 将被@Import注解导入到Spring容器中
 */
public class UserService {
    
    private String name = "UserService";
    
    public UserService() {
        System.out.println("UserService 构造函数被调用");
    }
    
    public void sayHello() {
        System.out.println("Hello from " + name);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
} 