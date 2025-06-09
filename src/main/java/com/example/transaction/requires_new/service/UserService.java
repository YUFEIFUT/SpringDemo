package com.example.transaction.requires_new.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PointService pointService;

    @Transactional
    public void createUser(String username, boolean throwUserException, boolean throwPointException) {
        String threadName = Thread.currentThread().getName();
        System.out.println("\n[" + threadName + "] - UserService.createUser: 开启外层事务，准备创建用户 " + username);

        // 使用 KeyHolder 来获取插入后生成的自增主键
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO users (username) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS // 指定返回生成的键
            );
            ps.setString(1, username);
            return ps;
        }, keyHolder);

        // 从 KeyHolder 中获取用户ID
        long userId = keyHolder.getKey().longValue();
        System.out.println("[" + threadName + "] - UserService.createUser: 用户已插入数据库，新用户ID为 " + userId);

        try {
            // 调用 PointService，传入 userId
            pointService.addPoints(userId, throwPointException);
        } catch (Exception e) {
            System.err.println("[" + threadName + "] - UserService.createUser: 捕获到内层事务异常 - " + e.getMessage());
        }

        if (throwUserException) {
            System.out.println("[" + threadName + "] - UserService.createUser: 准备抛出异常，外层事务将回滚！");
            throw new RuntimeException("用户服务异常，外层事务回滚！");
        }

        System.out.println("[" + threadName + "] - UserService.createUser: 正常结束，外层事务将提交。");
    }
}