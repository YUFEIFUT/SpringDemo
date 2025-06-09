package com.example.nonpublic;

import org.springframework.stereotype.Component;

@Component // 加上这个注解
class NonPublicBean {

    public NonPublicBean() {
        System.out.println("NonPublicBean's constructor is called by Spring scanner.");
    }

    public void sayHello() {
        System.out.println("Hello from a non-public component bean!");
    }
}