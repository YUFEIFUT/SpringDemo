package com.example.transaction.programmatic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {
        "com.example.transaction.programmatic.common.dao",
        "com.example.transaction.programmatic.common.service",
        "com.example.transaction.programmatic.h2" // Scan H2 specific demo packages
})
public class H2Config {

    @Bean
    public DataSource h2DataSource() {
        // Using EmbeddedDatabaseBuilder for simplicity and to auto-run schema.sql
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("h2testdb;DB_CLOSE_DELAY=-1;MODE=MySQL") // MODE=MySQL for broader compatibility if needed
                .addScript("classpath:schema-h2.sql") // This loads and runs the H2 schema
                .build();
    }

    /*
    // Alternative HikariCP configuration for H2 (if not using EmbeddedDatabaseBuilder's schema exec)
    @Bean(destroyMethod = "close")
    public DataSource h2DataSourceHikari() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2:mem:h2testdb;DB_CLOSE_DELAY=-1;MODE=MySQL"); // In-memory H2 database
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    // If using HikariCP directly, DataSourceInitializer would be needed like this:
    @Bean
    public DataSourceInitializer h2DataSourceInitializer(DataSource h2DataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(h2DataSource);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema-h2.sql"));
        initializer.setDatabasePopulator(populator);
        initializer.setEnabled(true); // Ensure it runs
        return initializer;
    }
    */

    @Bean
    public JdbcTemplate h2JdbcTemplate(DataSource h2DataSource) {
        return new JdbcTemplate(h2DataSource);
    }

    @Bean
    public PlatformTransactionManager h2TransactionManager(DataSource h2DataSource) {
        return new DataSourceTransactionManager(h2DataSource);
    }

    @Bean
    public TransactionTemplate h2TransactionTemplate(PlatformTransactionManager h2TransactionManager) {
        return new TransactionTemplate(h2TransactionManager);
    }
}
