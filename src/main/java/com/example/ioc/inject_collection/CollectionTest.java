package com.example.ioc.inject_collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 集合类型注入测试类，基于注解方式
 * <p>
 * 本示例演示：
 * 1. 如何通过 @Autowired 注入 List、Set、Map、数组类型的接口集合。
 * 2. Spring 会自动将所有 HollisService 类型的 bean 注入集合。
 * 3. Set 注入时，依赖于 bean 的 equals/hashCode 方法实现去重。
 * 4. Map 注入时，key 是 bean 名称，value 是 bean 实例。
 */
public class CollectionTest {
    public static void main(String[] args) {
        // 使用注解方式创建 Spring 容器，自动扫描 com.example.ioc.inject_collection 包下的所有组件
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        // 获取集合 Bean
        CollectionBean collectionBean = context.getBean(CollectionBean.class);

        // 输出 List 注入内容
        System.out.println("List 注入: (所有 HollisService 类型的 bean，按顺序注入)");
        List<HollisService> list = collectionBean.getServices();
        list.forEach(s -> System.out.println("  " + s.getName() + " - " + s));

        // 输出 Set 注入内容
        System.out.println("\nSet 注入(去重): (所有 HollisService 类型的 bean，自动去重，依赖 equals/hashCode)");
        Set<HollisService> set = collectionBean.getServicesSet();
        set.forEach(s -> System.out.println("  " + s.getName() + " - " + s));

        // 输出 Map 注入内容
        System.out.println("\nMap 注入: (key 是 bean 名称，value 是 HollisService 实例)");
        Map<String, HollisService> map = collectionBean.getServicesMap();
        map.forEach((k, v) -> System.out.println("  key: " + k + ", value: " + v.getName() + " - " + v));

        // 输出数组注入内容
        System.out.println("\n数组注入: (所有 HollisService 类型的 bean，按顺序注入)");
        HollisService[] array = collectionBean.getServicesArray();
        Arrays.stream(array).forEach(s -> System.out.println("  " + s.getName() + " - " + s));
    }

    /**
     * Spring 配置类，启用组件扫描
     * 这样 Spring 会自动发现并注册本包下所有 @Component 注解的类
     */
    @Configuration
    @ComponentScan(basePackages = "com.example.ioc.inject_collection")
    public static class SpringConfig {
    }
} 