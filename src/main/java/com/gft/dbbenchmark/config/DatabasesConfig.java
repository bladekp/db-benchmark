package com.gft.dbbenchmark.config;

import com.gft.dbbenchmark.dao.TownDao;
import com.gft.dbbenchmark.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class DatabasesConfig {

    private final HikariDataSourceCreator dataSourceCreator;

    @Autowired
    public DatabasesConfig(HikariDataSourceCreator dataSourceCreator) {
        this.dataSourceCreator = dataSourceCreator;
    }

    @Bean(name = "dataSource")
    public DataSource clientDatasource() {
        Map<Object, Object> targetDataSources = new HashMap<>();

        DataSource mySqlDataSource = dataSourceCreator.createDataSource("mysql.datasource");
        DataSource h2DataSource = dataSourceCreator.createDataSource("h2.datasource");

        targetDataSources.put(ClientDatabaseContextHolder.ClientDatabaseEnum.MYSQL, mySqlDataSource);
        targetDataSources.put(ClientDatabaseContextHolder.ClientDatabaseEnum.H2, h2DataSource);

        ClientDataSourceRouter clientRoutingDatasource = new ClientDataSourceRouter();
        clientRoutingDatasource.setTargetDataSources(targetDataSources);
        clientRoutingDatasource.setDefaultTargetDataSource(h2DataSource);
        return clientRoutingDatasource;
    }

    @Bean
    public TownService townService() {
        return new TownService(new TownDao(clientDatasource()));
    }
}
