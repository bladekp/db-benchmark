package pl.bladekp.dbbenchmark.config;

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
        String url = env.getRequiredProperty(configNamespace + ".url");
        String username = env.getProperty(configNamespace + ".username");
        String password = env.getProperty(configNamespace + ".password");
        String driver = env.getProperty(configNamespace + ".driver-class-name");
        dataSourceConfig.setJdbcUrl(url);
        if (username != null) {
            dataSourceConfig.setUsername(username);
        }
        if (password != null) {
            dataSourceConfig.setPassword(password);
        }
        if (driver != null) {
            dataSourceConfig.setDriverClassName(driver);
        }
        return new HikariDataSource(dataSourceConfig);
    }
}
