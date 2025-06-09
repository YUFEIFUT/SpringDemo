package com.example.transaction.requires_new.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 修改后的方法，直接接收 userId，避免在内层事务中查询外层未提交的数据
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addPoints(long userId, boolean throwException) {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] - PointService.addPoints: 开启新事务，为用户ID " + userId + " 增加积分。");

        jdbcTemplate.update("INSERT INTO points (user_id, amount) VALUES (?, ?)", userId, 100);
        System.out.println("[" + threadName + "] - PointService.addPoints: 积分已插入数据库。");

        if (throwException) {
            System.out.println("[" + threadName + "] - PointService.addPoints: 准备抛出异常，此事务将回滚！");
            throw new RuntimeException("积分服务异常，内层事务回滚！");
        }

        System.out.println("[" + threadName + "] - PointService.addPoints: 正常结束，此事务将提交。");
    }
}