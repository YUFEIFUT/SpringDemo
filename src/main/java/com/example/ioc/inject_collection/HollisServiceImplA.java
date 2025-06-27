package com.example.ioc.inject_collection;

import org.springframework.stereotype.Component;

/**
 * HollisService 的实现类 A
 * <p>
 * 该类被 @Component 注解标记，Spring 会自动将其实例注册为 bean。
 * 可以通过配置类注册多个实例，演示 Set 注入时的去重效果。
 * <p>
 * 重写了 equals 和 hashCode，只要 getName() 相同就认为是同一个对象。
 */
@Component("hollisServiceA")
public class HollisServiceImplA implements HollisService {
    @Override
    public String getName() {
        return "服务A";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HollisService other = (HollisService) obj;
        // 只要服务名相同就认为是同一个对象
        return getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
} 