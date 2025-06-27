package com.example.ioc.inject_collection;

/**
 * 服务接口，演示集合注入的核心接口
 * <p>
 * 所有实现该接口的类都会被 Spring 识别为 HollisService 类型的 bean，
 * 可用于集合注入（List、Set、Map、数组）。
 */
public interface HollisService {
    /**
     * 获取服务名称
     * @return 服务名称字符串
     */
    String getName();
} 