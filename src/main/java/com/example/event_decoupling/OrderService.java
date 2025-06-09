package com.example.event_decoupling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

// 订单服务类
@Component
public class OrderService {

    @Autowired
    private ApplicationEventPublisher publisher;

    public void payFinished() {
        PayFinishedEvent springEvent = new PayFinishedEvent();
        publisher.publishEvent(springEvent);
    }
}