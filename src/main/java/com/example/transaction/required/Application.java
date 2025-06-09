package com.example.transaction.required;

import com.example.transaction.required.config.TransactionConfig;
import com.example.transaction.required.dao.UserDao;
import com.example.transaction.required.entity.User;
import com.example.transaction.required.service.ParentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

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