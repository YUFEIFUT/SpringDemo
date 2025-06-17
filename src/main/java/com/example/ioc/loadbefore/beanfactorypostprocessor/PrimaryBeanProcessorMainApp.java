package com.example.ioc.loadbefore.beanfactorypostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/*
  核心处理类：通过BeanFactoryPostProcessor提前初始化目标Bean

  容器启动流程：
    当AnnotationConfigApplicationContext启动时，首先会执行refresh()方法
    在invokeBeanFactoryPostProcessors()阶段会调用PrimaryBeanProcessor的postProcessBeanFactory方法
    在该方法中通过getBean(PrimaryBean.class)主动触发PrimaryBean的初始化（早于其他 Bean 的常规初始化流程）
  初始化顺序验证：
    PrimaryBean的构造方法会在BeanFactoryPostProcessor执行期间被调用
    其他普通 Bean（如NormalTestBean）会在后续的finishBeanFactoryInitialization()阶段正常初始化
 */
@Component
class PrimaryBeanProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 主动获取PrimaryBean实例，触发其初始化（早于其他Bean）
        PrimaryBean primaryBean = beanFactory.getBean(PrimaryBean.class);
        System.out.println("[BeanFactoryPostProcessor] 提前初始化的Bean：" + primaryBean);
    }
}

/**
 * 需要提前初始化的目标Bean
 */
@Component
class PrimaryBean {
    public PrimaryBean() {
        System.out.println("[PrimaryBean] 构造方法执行，我是提前初始化的Bean");
    }

    @Override
    public String toString() {
        return "PrimaryBean{status=initialized}";
    }
}

/**
 * 普通测试Bean（用于对比初始化顺序）
 */
@Component
class NormalTestBean {
    public NormalTestBean() {
        System.out.println("[NormalTestBean] 构造方法执行，我是普通Bean");
    }
}

// 配置类（用于扫描组件）
@Configuration
@ComponentScan("com.example.ioc.loadbefore.beanfactorypostprocessor")
class DemoConfig {
}

// 主启动类
public class PrimaryBeanProcessorMainApp {
    public static void main(String[] args) {
        // 启动Spring容器
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig.class)) {
            System.out.println("---------------------- Spring容器启动完成 ----------------------");
            
            // 验证Bean是否存在（触发自动装配）
            NormalTestBean testBean = context.getBean(NormalTestBean.class);
            System.out.println("获取普通Bean：" + testBean);
        }
    }
}