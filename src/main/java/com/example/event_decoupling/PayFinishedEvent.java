package com.example.event_decoupling;

import org.springframework.context.ApplicationEvent;

// 自定义的事件类
public class PayFinishedEvent extends ApplicationEvent {

    public PayFinishedEvent() {
        super(new Object());
    }
}