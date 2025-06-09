package com.example.transaction.requires_new;

import com.example.transaction.requires_new.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class MainApplication {

    public static void main(String[] args) {
        // --- 场景一：内外事务都正常 ---
        runScenario("场景一：内外事务都正常", "user1", false, false);

        // --- 场景二：内层事务异常，外层正常 ---
        runScenario("场景二：内层事务异常，外层正常", "user2", false, true);
        
        // --- 场景三：外层事务异常，内层正常 ---
        runScenario("场景三：外层事务异常，内层正常", "user3", true, false);
        
        // --- 场景四：内外事务都异常 ---
        runScenario("场景四：内外事务都异常", "user4", true, true);
    }

    private static void runScenario(String scenarioName, String username, boolean throwUserException, boolean throwPointException) {
        System.out.println("=======================================================================");
        System.out.println("||                 " + scenarioName + "                 ||");
        System.out.println("=======================================================================");

        // 每次场景都创建一个全新的Spring容器和数据库，保证环境纯净
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        
        try {
            userService.createUser(username, throwUserException, throwPointException);
        } catch (Exception e) {
            System.err.println("MainApplication: 最外层捕获到异常 - " + e.getMessage());
        }

        // 验证数据库最终结果
        System.out.println("\n----------- " + scenarioName + " 最终数据库状态 -----------");
        List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM users WHERE username = ?", username);
        List<Map<String, Object>> points = jdbcTemplate.queryForList(
            "SELECT p.* FROM points p JOIN users u ON p.user_id = u.id WHERE u.username = ?", username
        );

        System.out.println("用户表 (" + username + "): " + (users.isEmpty() ? "未找到" : users));
        System.out.println("积分表 (" + username + "): " + (points.isEmpty() ? "未找到" : points));
        System.out.println("-----------------------------------------------------------\n");

        context.close();
    }
}