package com.example.transaction.required.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement // 启用事务管理
@ComponentScan("com.example.transaction.required") // 扫描服务和DAO
public class TransactionConfig {

    // 配置H2内存数据库
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        // 添加 INIT 参数，创建 USERS 表（ID 自增主键，NAME 字符串）
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=CREATE TABLE IF NOT EXISTS USERS (ID BIGINT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(50))");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    // 新增：配置 JdbcTemplate Bean（依赖 DataSource）
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    // 配置事务管理器
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}