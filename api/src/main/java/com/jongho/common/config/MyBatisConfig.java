package com.jongho.common.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


/*
*
* DataSource의 설정을 참고하여 MySQL과 연동하는 MyBatis 설정을 추가합니다.
* transactionManager() 메서드는 트랜잭션 관리자를 생성합니다.
* sqlSessionTemplate() 메서드는 connection 관리 및 SQL excaption을 runtime exception으로 변환하는 등의 기능을 제공하는 sqlSessionTemplate을 생성한다.
*
*/
@MapperScan("com.jongho.**.mapper")
@Configuration
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(
            DataSource dataSource) throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}