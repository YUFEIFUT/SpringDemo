package com.example.transaction.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {
        "com.example.transaction.common.dao",
        "com.example.transaction.common.service",
        "com.example.transaction.programmatic.mysql" // Scan MySQL specific demo packages
})
@PropertySource("classpath:mysql.properties")
public class MySQLConfig {

    @Value("${mysql.url}")
    private String mysqlUrl;

    @Value("${mysql.username}")
    private String mysqlUsername;

    @Value("${mysql.password}")
    private String mysqlPassword;

    @Bean(destroyMethod = "close")
    public DataSource mysqlDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Standard MySQL 8+ driver
        dataSource.setJdbcUrl(mysqlUrl);
        dataSource.setUsername(mysqlUsername);
        dataSource.setPassword(mysqlPassword);
        // HikariCP specific settings (optional, defaults are generally good)
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(5);
        dataSource.setConnectionTimeout(30000); // 30 seconds
        dataSource.setIdleTimeout(600000); // 10 minutes
        dataSource.setMaxLifetime(1800000); // 30 minutes
        return dataSource;
    }

    @Bean
    public DataSourceInitializer mysqlDataSourceInitializer(DataSource mysqlDataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(mysqlDataSource);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema-mysql.sql"));
        // For MySQL, it might be safer to disable this in production if schema is managed externally
        // For demo purposes, we enable it.
        initializer.setEnabled(true);
        System.out.println("Attempting to initialize MySQL database with schema-mysql.sql. Ensure the database specified in mysql.properties exists.");
        return initializer;
    }

    @Bean
    public JdbcTemplate mysqlJdbcTemplate(DataSource mysqlDataSource) {
        return new JdbcTemplate(mysqlDataSource);
    }

    @Bean
    public PlatformTransactionManager mysqlTransactionManager(DataSource mysqlDataSource) {
        return new DataSourceTransactionManager(mysqlDataSource);
    }

    @Bean
    public TransactionTemplate mysqlTransactionTemplate(PlatformTransactionManager mysqlTransactionManager) {
        return new TransactionTemplate(mysqlTransactionManager);
    }
}
