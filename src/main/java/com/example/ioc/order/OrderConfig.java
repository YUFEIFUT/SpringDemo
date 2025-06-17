// 配置类：com.example.ioc.order.OrderConfig
package com.example.ioc.order;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.ioc.order") // 扫描子包
public class OrderConfig {
}