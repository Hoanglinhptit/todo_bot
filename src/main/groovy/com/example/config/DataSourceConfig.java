package com.example.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource(Environment env) {
        return DataSourceBuilder.create()
                .url(env.getProperty("spring.datasource.mysql.url"))
                .username(env.getProperty("spring.datasource.mysql.username"))
                .password(env.getProperty("spring.datasource.mysql.password"))
                .driverClassName(env.getProperty("spring.datasource.mysql.driver-class-name"))
                .build();
    }

    @Bean(name = "oracleDataSource")
    @Primary
    public DataSource oracleDataSource(Environment env) {
        return DataSourceBuilder.create()
                .url(env.getProperty("spring.datasource.oracle.url"))
                .username(env.getProperty("spring.datasource.oracle.username"))
                .password(env.getProperty("spring.datasource.oracle.password"))
                .driverClassName(env.getProperty("spring.datasource.oracle.driver-class-name"))
                .build();
    }

    @Bean
    public DataSource dataSource(@Qualifier("mysqlDataSource") DataSource mysqlDataSource,
                                 @Qualifier("oracleDataSource") DataSource oracleDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("MYSQL", mysqlDataSource);
        targetDataSources.put("ORACLE", oracleDataSource);

        AbstractRoutingDataSource routingDataSource = new MultiRoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(oracleDataSource);
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(dataSource())
//                .packages("com.example.entity") // package chứa các entity
//                .persistenceUnit("default")
//                .build();
//    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

