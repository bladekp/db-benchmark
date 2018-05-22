package com.gft.dbbenchmark.config;

import com.gft.dbbenchmark.dao.TownDaoJdbc;
import com.gft.dbbenchmark.dao.TownDaoMongo;
import com.gft.dbbenchmark.service.TownService;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
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

    @Bean(name = "mongoDataSource")
    public MongoDbFactory mongoDbFactory() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        return new SimpleMongoDbFactory(mongoClient, "benchmark");
    }

    @Bean
    public TownService townService() {
        return new TownService(new TownDaoJdbc(clientDatasource()), new TownDaoMongo(mongoDbFactory()));
    }
}
