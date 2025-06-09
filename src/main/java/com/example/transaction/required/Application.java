package com.example.transaction.required;

import com.example.transaction.required.config.TransactionConfig;
import com.example.transaction.required.dao.UserDao;
import com.example.transaction.required.entity.User;
import com.example.transaction.required.service.ParentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/*
官方解释：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。

如果A、B两个方法都加了@Transactional注解，默认是REQUIRED传播行为。那么如果A方法调用B方法，它们会共用一个事务，因为默认会使用同一条连接，相当于一个事务里执行。
 */
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(TransactionConfig.class);
        ParentService parentService = context.getBean(ParentService.class);
        UserDao userDao = context.getBean(UserDao.class);

        try {
            parentService.parentMethod();
        } catch (Exception e) {
            System.out.println("捕获到异常: " + e.getMessage());
        }

        // 验证数据是否回滚
        List<User> parentUsers = userDao.findUserByName("ParentUser");
        List<User> childUsers = userDao.findUserByName("ChildUser");

        System.out.println("ParentUser是否存在: " + !parentUsers.isEmpty()); // 存在时为 true，回滚后为 false
        System.out.println("ChildUser是否存在: " + !childUsers.isEmpty()); // 存在时为 true，回滚后为 false
    }
}