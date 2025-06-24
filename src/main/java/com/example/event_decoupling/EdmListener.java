package com.example.event_decoupling;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;

// 站内信监听类：监听支付完成事件，进行站内信发送，已改为异步执行。
// 使用@Async("registerSuccessExecutor")指定自定义线程池，避免默认线程池频繁创建线程。
// @EventListener用于监听Spring事件。
// @Async实现异步处理，提升性能。
@Component
public class EdmListener {

    /**
     * 支付完成事件监听方法，异步执行站内信发送。
     * @param event 支付完成事件
     */
    @Async("registerSuccessExecutor")
    @EventListener
    public void onListenPayFinished(PayFinishedEvent event) {
        // 发送站内信
        System.out.println("执行发送站内信操作，线程：" + Thread.currentThread().getName());
    }
}