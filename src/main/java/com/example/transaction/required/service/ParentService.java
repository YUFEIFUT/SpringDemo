package com.example.transaction.required.service;

import com.example.transaction.required.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParentService {
    private final UserDao userDao;
    private final ChildService childService;

    public ParentService(UserDao userDao, ChildService childService) {
        this.userDao = userDao;
        this.childService = childService;
    }

    // 默认传播级别为 REQUIRED
    @Transactional(rollbackFor = Exception.class)
    public void parentMethod() {
        // 插入父数据
        userDao.insertUser("ParentUser");

        // 在这里抛出异常和下边抛出异常都会导致全部回滚
//        int a = 1;
//        if (a==1) {
//            throw new RuntimeException("Forced rollback");
//        }
        // 调用子方法，子方法默认也是 REQUIRED
        childService.childMethod();
        // 故意抛出异常，模拟业务错误
        throw new RuntimeException("Forced rollback");
    }
}