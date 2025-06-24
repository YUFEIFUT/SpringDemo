package com.example.event_decoupling;

import org.springframework.context.ApplicationEvent;

// 支付完成事件类：用于在订单服务中发布，监听器监听此事件进行后续处理。
public class PayFinishedEvent extends ApplicationEvent {

    public PayFinishedEvent() {
        super(new Object());
    }
}