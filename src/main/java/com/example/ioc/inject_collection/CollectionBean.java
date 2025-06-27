package com.example.ioc.inject_collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 演示集合类型注入的 Bean，注入 HollisService 类型
 * <p>
 * 通过 @Autowired 注解，可以让 Spring 自动注入所有 HollisService 类型的 bean 到集合中。
 * List、Set、数组会注入所有实现了 HollisService 接口的 bean 实例。
 * Map 会以 bean 名称为 key，bean 实例为 value 注入。
 * Set 的去重依赖于 bean 的 equals/hashCode 方法。
 */
@Component
public class CollectionBean {
    /**
     * 注入所有 HollisService 类型的 Bean，按顺序放入 List
     * 顺序通常与 Spring 容器中 bean 的注册顺序一致
     */
    @Autowired
    private List<HollisService> services;

    /**
     * 注入所有 HollisService 类型的 Bean，Bean 名称为 key，实例为 value
     * Map 的 key 是 bean 的名称，value 是 bean 实例
     */
    @Autowired
    private Map<String, HollisService> servicesMap;

    /**
     * 注入所有 HollisService 类型的 Bean，去重后放入 Set
     * Set 的去重依赖于 bean 的 equals/hashCode 方法
     */
    @Autowired
    private Set<HollisService> servicesSet;

    /**
     * 注入所有 HollisService 类型的 Bean，放入数组
     * 数组的顺序与 List 一致
     */
    @Autowired
    private HollisService[] servicesArray;

    public List<HollisService> getServices() {
        return services;
    }

    public Map<String, HollisService> getServicesMap() {
        return servicesMap;
    }

    public Set<HollisService> getServicesSet() {
        return servicesSet;
    }

    public HollisService[] getServicesArray() {
        return servicesArray;
    }

    /**
     * 展示 Set 去重效果
     * 只要 getName() 相同且 equals/hashCode 实现一致，Set 只保留一个
     */
    public String showSetDeduplication() {
        // 通过对象的 hashCode 和 equals 判断去重
        return servicesSet.stream().map(HollisService::getName).collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        return "CollectionBean{" +
                "\n  List=" + services.stream().map(HollisService::getName).collect(Collectors.joining(", ")) +
                ",\n  Set=" + showSetDeduplication() +
                ",\n  Map=" + servicesMap.keySet() +
                ",\n  Array=" + java.util.Arrays.stream(servicesArray).map(HollisService::getName).collect(Collectors.joining(", ")) +
                '}';
    }
}
