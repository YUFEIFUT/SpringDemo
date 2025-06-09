package com.example.event_decoupling;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// 站内信监听类
@Component
public class EdmListener {

    @EventListener
    public void onListenPayFinished(PayFinishedEvent event) {
        // 发送站内信
        System.out.println("执行发送站内信操作");
    }
}