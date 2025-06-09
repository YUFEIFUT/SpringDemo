//package com.example.transaction.requires_new;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//@Configuration
//@ComponentScan("com.example.transaction.requires_new.service")
//@EnableTransactionManagement // 启用注解驱动的事务管理
//@EnableAspectJAutoProxy // 启用AOP代理
//public class AppConfig {
//
//    /**
//     * 配置一个内存数据库 H2 的数据源
//     */
//    @Bean
//    public DataSource dataSource() {
//        // 使用内存数据库，每次启动都是全新的
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("classpath:schema.sql") // 启动时执行的建表语句
//                .build();
//    }
//
//    /**
//     * 配置 Spring 的 JdbcTemplate，方便我们操作数据库
//     */
//    @Bean
//    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    /**
//     * 配置事务管理器
//     */
//    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//}

package com.example.transaction.requires_new;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.example.transaction.requires_new.service")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class AppConfig {

    /**
     * 配置一个基于 HikariCP 的、真正支持连接池的数据源。
     * 这是工业级的标准做法。
     */
    @Bean(destroyMethod = "close") // HikariDataSource 的销毁方法是 close()
    public DataSource dataSource() {
        // 1. 创建 HikariCP 的配置对象
        HikariConfig config = new HikariConfig();

        // 2. 设置连接 H2 数据库的基本信息
        config.setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:schema.sql'");
        config.setUsername("sa");
        config.setPassword("");
        config.setDriverClassName("org.h2.Driver"); // 最好显式指定驱动

        // 3. 设置连接池参数
        config.setMaximumPoolSize(5); // 即使是内存数据库，也设置一个像样的池大小
        config.setPoolName("MyHikariPool");

        // 4. 根据配置创建 HikariDataSource
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}