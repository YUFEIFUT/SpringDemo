package com.example.ioc.inject_collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultiAConfig {
    @Bean
    public HollisService hollisServiceA1() {
        return new HollisServiceImplA();
    }
    @Bean
    public HollisService hollisServiceA2() {
        return new HollisServiceImplA();
    }
}