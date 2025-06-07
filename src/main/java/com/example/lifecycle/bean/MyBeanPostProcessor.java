package com.example.lifecycle.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof LifecycleDemoBean) {
            System.out.println("4. BeanPostProcessor.postProcessBeforeInitialization() 被调用, bean: " + beanName);
        }
        return bean;
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof LifecycleDemoBean) {
            System.out.println("8. BeanPostProcessor.postProcessAfterInitialization() 被调用, bean: " + beanName);
            System.out.println("9. Bean 准备就绪");
        }
        return bean;
    }
}