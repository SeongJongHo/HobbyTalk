package com.jongho.common.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/*
* sourceDataSource라는 Bean 이름을 가진 DataSource를 생성하는 설정 클래스
*/
@Configuration
@PropertySource("classpath:application-api-${spring.profiles.active:default}.properties")
public class DataSourceConfig {
    @Bean(name = "sourceDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.source")
    public DataSource sourceDataSource() {
        DataSource dataSource = DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();

        return dataSource;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(@Qualifier("sourceDataSource") DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDatabasePopulator(databasePopulator());
        initializer.setDataSource(dataSource);

        return initializer;
    }

    @Bean
    public ResourceDatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema.sql")); // 초기화 스키마 파일
        return populator;
    }
}