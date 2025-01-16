package com.jongho.openChat.common.config;

import com.jongho.openChat.dao.step.MyBatisMessageItemWriter;
import com.jongho.openChat.dao.step.RedisMessageItemReader;
import com.jongho.openChat.domain.model.OpenChat;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager")
@RequiredArgsConstructor
public class ChatBatchConfig {
    private final RedisMessageItemReader redisMessageItemReader;
    private final MyBatisMessageItemWriter myBatisChatMessageItemWriter;

    @Bean
    public Job chatMessageJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("chatMessageJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step chatMessageStep(JobRepository jobRepository, @Qualifier("batchTransactionManager") PlatformTransactionManager batchTransactionManager) {
        return new StepBuilder("chatMessageStep", jobRepository)
                .<List<OpenChat>, List<OpenChat>>chunk(1, batchTransactionManager)
                .reader(redisMessageItemReader)
                .writer(myBatisChatMessageItemWriter)
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "batch.datasource")
    public DataSourceProperties batchDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "batchDataSource")
    public DataSource batchDataSource() {
        return batchDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "batchTransactionManager")
    public PlatformTransactionManager batchTransactionManager(@Qualifier("batchDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public boolean initBatchSchema(@Qualifier("batchDataSource") DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("org/springframework/batch/core/schema-h2.sql"));
        populator.execute(dataSource);
        return true;
    }
}