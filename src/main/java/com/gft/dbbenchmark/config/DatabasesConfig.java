package com.gft.dbbenchmark.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.gft.dbbenchmark.repo"})
public class DatabasesConfig {

    private final Environment env;

    @Autowired
    public DatabasesConfig(Environment env){
        this.env = env;
    }

    private DataSource mySqlDataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setJdbcUrl(env.getRequiredProperty("mysql.datasource.url"));
        dataSourceConfig.setUsername(env.getRequiredProperty("mysql.datasource.username"));
        dataSourceConfig.setPassword(env.getRequiredProperty("mysql.datasource.password"));
        return new HikariDataSource(dataSourceConfig);
    }

    private DataSource h2DataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setJdbcUrl(env.getRequiredProperty("h2.datasource.url"));
        dataSourceConfig.setUsername(env.getRequiredProperty("h2.datasource.username"));
        dataSourceConfig.setPassword(env.getRequiredProperty("h2.datasource.password"));
        return new HikariDataSource(dataSourceConfig);
    }

    @Bean(name = "dataSource")
    public DataSource clientDatasource() {
        Map<Object, Object> targetDataSources = new HashMap<>();

        DataSource mySqlDataSource = mySqlDataSource();
        DataSource h2DataSource = h2DataSource();

        targetDataSources.put(ClientDatabaseContextHolder.ClientDatabaseEnum.MYSQL, mySqlDataSource);
        targetDataSources.put(ClientDatabaseContextHolder.ClientDatabaseEnum.H2, h2DataSource);

        ClientDataSourceRouter clientRoutingDatasource = new ClientDataSourceRouter();
        clientRoutingDatasource.setTargetDataSources(targetDataSources);
        clientRoutingDatasource.setDefaultTargetDataSource(h2DataSource);
        return clientRoutingDatasource;
    }
}