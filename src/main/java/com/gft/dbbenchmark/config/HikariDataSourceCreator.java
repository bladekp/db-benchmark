package com.gft.dbbenchmark.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class HikariDataSourceCreator {

    private final Environment env;

    @Autowired
    public HikariDataSourceCreator(Environment env) {
        this.env = env;
    }

    DataSource createDataSource(String configNamespace) {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setJdbcUrl(env.getRequiredProperty(configNamespace + ".url"));
        dataSourceConfig.setUsername(env.getRequiredProperty(configNamespace + ".username"));
        dataSourceConfig.setPassword(env.getRequiredProperty(configNamespace + ".password"));
        return new HikariDataSource(dataSourceConfig);
    }
}
