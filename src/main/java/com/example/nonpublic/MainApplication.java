package com.example.nonpublic;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 在 RootBeanDefinition 的父类 AbstractBeanDefinition 中，明确设置了 private boolean nonPublicAccessAllowed = true; 所以不会异常的
public class MainApplication {

    public static void main(String[] args) {
        System.out.println("Attempting to start Spring context...");
        try {
            // 使用 AnnotationConfigApplicationContext 加载我们的配置类
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

            // 如果能成功启动（实际上不会），尝试获取Bean【实际上会成功的，因为 private boolean nonPublicAccessAllowed = true; 】
            System.out.println("Spring context started successfully.");
            NonPublicBean bean = context.getBean(NonPublicBean.class);
            bean.sayHello();

            context.close();
        } catch (Exception e) {
            System.err.println("\nAn exception occurred during Spring context initialization!");
            // 打印完整的异常堆栈信息，以便我们看到 BeanCreationException
            e.printStackTrace();
        }
    }
}