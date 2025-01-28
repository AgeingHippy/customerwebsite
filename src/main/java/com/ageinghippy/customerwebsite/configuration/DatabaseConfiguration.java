package com.ageinghippy.customerwebsite.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    // @Value assigns the property value at the time of bean creation
    @Value("${main.datasource.driver-class-name}")
    private String mainDatasourceDriver;
    @Value("${main.datasource.url}")
    private String mainDatasourceUrl;
    @Value("${main.datasource.username}")
    private String mainDatasourceUsername;
    @Value("${main.datasource.password}")
    private String mainDatasourcePassword;

    @Value("${batch.datasource.driver-class-name}")
    private String batchDatasourceDriver;
    @Value("${batch.datasource.url}")
    private String batchDatasourceUrl;
    @Value("${batch.datasource.username}")
    private String batchDatasourceUsername;
    @Value("${batch.datasource.password}")
    private String batchDatasourcePassword;

    @Bean
    @Primary
    public DataSource mainDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(mainDatasourceDriver);
        config.setJdbcUrl(mainDatasourceUrl);
        config.setUsername(mainDatasourceUsername);
        config.setPassword(mainDatasourcePassword);
        return new HikariDataSource(config);
    }

    @Bean
    @BatchDataSource
    public DataSource batchDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(batchDatasourceDriver);
        config.setJdbcUrl(batchDatasourceUrl);
        config.setUsername(batchDatasourceUsername);
        config.setPassword(batchDatasourcePassword);
        return new HikariDataSource(config);
    }

}
