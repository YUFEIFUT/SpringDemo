package com.example.lifecycle.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/*
●BeanNameAware:通过这个接口，Bean可以获取到自己在Spring容器中的名字。这对于需要根据Bean的名称进行某些操作的场景很有用。
●BeanClassLoaderAware: 这个接口使Bean能够访问加载它的类加载器。这在需要进行类加载操作时特别有用，例如动态加载类。
●BeanFactoryAware: 通过这个接口可以获取对BeanFactory的引用，获得对BeanFactory的访问权限
 */
public class LifecycleDemoBean implements 
        BeanNameAware, BeanClassLoaderAware, BeanFactoryAware,
        InitializingBean, DisposableBean {
    
    public LifecycleDemoBean() {
        System.out.println("1. 实例化 Bean - 构造函数被调用");
    }
    
    @Override
    public void setBeanName(String name) {
        System.out.println("3. 检查 Aware 接口 - BeanNameAware.setBeanName() 被调用, beanName: " + name);
    }
    
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("3. 检查 Aware 接口 - BeanClassLoaderAware.setBeanClassLoader() 被调用");
    }
    
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("3. 检查 Aware 接口 - BeanFactoryAware.setBeanFactory() 被调用");
    }
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("5. @PostConstruct 方法被调用");
    }
    
    @Override
    public void afterPropertiesSet() {
        System.out.println("6. InitializingBean.afterPropertiesSet() 被调用");
    }

    public void customInit() {
        System.out.println("7. 自定义 init-method 被调用");
    }
    
    @PreDestroy
    public void preDestroy() {
        System.out.println("10. @PreDestroy 方法被调用");
    }
    
    @Override
    public void destroy() {
        System.out.println("11. DisposableBean.destroy() 被调用");
    }
    
    public void customDestroy() {
        System.out.println("12. 自定义 destroy-method 被调用");
    }
}