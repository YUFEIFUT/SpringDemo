package com.example.event_decoupling;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// 账单监听类
@Component
public class BillListener {

    @EventListener
    public void onListenPayFinished(PayFinishedEvent event) {
        // 记账
        System.out.println("执行记账操作");
    }
}