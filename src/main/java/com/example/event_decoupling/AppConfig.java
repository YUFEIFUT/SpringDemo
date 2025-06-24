package com.example.event_decoupling;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.*;

// =============================
// 事件异步监听改造说明
//
// 1. @EnableAsync 开启Spring异步支持。
// 2. 自定义线程池registerSuccessExecutor，避免@Async默认线程池频繁创建线程带来的性能问题。
// 3. 监听器方法加@Async("registerSuccessExecutor")和@EventListener，实现事件异步处理。
// 4. 验证方法：
//    a) 在监听器方法中打印线程名，确认为自定义线程池线程。
//    b) 在事件发布处和监听器中分别打印线程名，确认主线程和监听线程不同。
//    c) 监听器中加sleep，主线程能立即继续执行，说明异步。
//
// 这样可以实现事件监听的高效异步处理，且线程资源可控。
// =============================

// 配置类
@Configuration
@EnableAsync
@ComponentScan("com.example.event_decoupling")
public class AppConfig {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        OrderService orderService = context.getBean(OrderService.class);
        orderService.payFinished();
        context.close();
    }

    /**
     * 自定义异步线程池 registerSuccessExecutor
     * <p>
     * 1. 通过@Bean注解将线程池注册为Spring Bean，名称为"registerSuccessExecutor"。
     * 2. 使用Guava的ThreadFactoryBuilder自定义线程工厂，设置线程名称格式为"registerSuccessExecutor-%d"，方便排查和监控线程。
     * 3. 创建ThreadPoolTaskExecutor：
     *    - corePoolSize：核心线程数100，适合高并发场景，保证有足够线程处理任务。
     *    - maxPoolSize：最大线程数200，任务激增时可临时扩容线程数。
     *    - queueCapacity：队列容量1024，缓冲任务，防止短时间任务激增导致线程频繁创建销毁。
     *    - threadFactory：使用自定义线程工厂，便于线程命名和管理。
     *    - rejectedExecutionHandler：拒绝策略为AbortPolicy，队列满且线程数达到最大时抛出异常，防止任务丢失。
     * 4. executor.initialize()：初始化线程池参数。
     * 5. 返回ThreadPoolTaskExecutor对象，供@Async("registerSuccessExecutor")使用，实现事件监听异步处理。
     */
    @Bean("registerSuccessExecutor")
    public Executor registerSuccessExecutor() {
        // 创建自定义线程工厂，设置线程名称格式，便于排查和监控
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("registerSuccessExecutor-%d").build();
        // 创建Spring的线程池任务执行器
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100); // 核心线程数
        executor.setMaxPoolSize(200);  // 最大线程数
        executor.setQueueCapacity(1024); // 队列容量
        executor.setThreadFactory(namedThreadFactory); // 设置自定义线程工厂
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy()); // 拒绝策略
        executor.initialize(); // 初始化线程池
        return executor;
    }
}