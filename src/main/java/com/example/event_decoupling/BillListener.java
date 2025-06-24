package com.example.event_decoupling;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;

// 账单监听类：监听支付完成事件，进行记账操作，已改为异步执行。
// 使用@Async("registerSuccessExecutor")指定自定义线程池，避免默认线程池频繁创建线程。
// @EventListener用于监听Spring事件。
// @Async实现异步处理，提升性能。
@Component
public class BillListener {

    /**
     * 支付完成事件监听方法，异步执行记账操作。
     * @param event 支付完成事件
     */
    @Async("registerSuccessExecutor")
    @EventListener
    public void onListenPayFinished(PayFinishedEvent event) {
        // 在监听方法中打印当前线程名，观察是否为自定义线程池的线程（如 registerSuccessExecutor-1 这种格式）。
        System.out.println("执行记账操作，线程：" + Thread.currentThread().getName());
        // 记账
        System.out.println("执行记账操作");
    }
}