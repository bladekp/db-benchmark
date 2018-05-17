package com.gft.dbbenchmark.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "mySqlEntityManagerFactory",
        transactionManagerRef = "mySqlTransactionManager",
        basePackages = {"com.gft.dbbenchmark.repo"}
)
public class MySqlDbConfig {

    @Primary
    @Bean(name = "mySqlDataSource")
    public DataSource dataSource(Environment env) {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setJdbcUrl(env.getRequiredProperty("mysql.datasource.url"));
        dataSourceConfig.setUsername(env.getRequiredProperty("mysql.datasource.username"));
        dataSourceConfig.setPassword(env.getRequiredProperty("mysql.datasource.password"));
        return new HikariDataSource(dataSourceConfig);
    }

    @Primary
    @Bean(name = "mySqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("mySqlDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.gft.dbbenchmark.model")
                .persistenceUnit("mySql")
                .build();
    }

    @Primary
    @Bean(name = "mySqlTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("mySqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
