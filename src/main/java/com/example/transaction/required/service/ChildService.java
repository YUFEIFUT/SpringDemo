package com.example.transaction.required.service;

import com.example.transaction.required.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChildService {
    private final UserDao userDao;

    public ChildService(UserDao userDao) {
        this.userDao = userDao;
    }

    // 不指定传播级别（默认 REQUIRED）
    @Transactional(rollbackFor = Exception.class)
    public void childMethod() {
        // 插入子数据
        userDao.insertUser("ChildUser");
    }
}