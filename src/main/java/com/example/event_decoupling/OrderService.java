package com.example.event_decoupling;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

// 订单服务类：用于模拟支付完成后发布事件。
// 通过ApplicationEventPublisher发布PayFinishedEvent事件，触发监听器异步处理。
@Component
public class OrderService {

    private final ApplicationEventPublisher publisher;

    public OrderService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * 支付完成后发布事件，触发监听器。
     */
    public void payFinished() {
        System.out.println("主线程：" + Thread.currentThread().getName());
        PayFinishedEvent springEvent = new PayFinishedEvent();
        publisher.publishEvent(springEvent);
    }
}