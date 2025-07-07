package com.example.ioc.dynamic_bean_register.registryppostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * 该类实现了BeanDefinitionRegistryPostProcessor接口，
 * 用于在Spring容器启动时，编程式地动态注册自定义Bean（如DynamicBean）。
 * <p>
 * 流程说明：
 * 1. Spring启动时会回调postProcessBeanDefinitionRegistry方法。
 * 2. 在该方法中通过BeanDefinitionBuilder构建DynamicBean的定义，并注册到容器。
 * 3. 这样后续可以像普通Bean一样通过context.getBean获取到DynamicBean。
 * <p>
 * 注意事项：
 * - 动态注册的Bean与普通@Component/@Bean注解注册的Bean等价。
 * - 本例中注册的Bean名为dynamicBean。
 * <p>
 * 执行时机说明：
 * - BeanDefinitionRegistryPostProcessor 会在 Spring 容器刷新（refresh）过程中，所有的 bean 定义加载完成后、所有的 bean 实例化之前被执行。
 * - 具体来说，先于普通的 BeanFactoryPostProcessor 执行，属于 Spring 容器启动早期阶段。
 */
@Configuration
public class DynamicBeanRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    /**
     * 在Spring容器的Bean定义阶段动态注册自定义Bean。
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 动态注册DynamicBean
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DynamicBean.class);
        registry.registerBeanDefinition("dynamicBean", builder.getBeanDefinition());
        System.out.println("[DynamicBeanRegistryPostProcessor] 已动态注册Bean: dynamicBean");
    }

    /**
     * 本例无需实现该方法。
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 无需实现
    }
} 