package pl.bladekp.dbbenchmark.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.bladekp.dbbenchmark.dao.TownDaoJdbc;
import pl.bladekp.dbbenchmark.dao.TownDaoMongo;
import pl.bladekp.dbbenchmark.service.TownService;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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

        Stream
                .of(ClientDatabaseContextHolder.ClientDatabaseEnum.values())
                .filter(db -> db != ClientDatabaseContextHolder.ClientDatabaseEnum.MONGO && db.isEnabled())
                .forEach(db -> targetDataSources.put(db, dataSourceCreator.createDataSource(db.getDatasourceNamespace())));

        ClientDataSourceRouter clientRoutingDatasource = new ClientDataSourceRouter();
        clientRoutingDatasource.setTargetDataSources(targetDataSources);
        if (targetDataSources.isEmpty()){
            throw new RuntimeException("At least one datasource should be provided.");
        } else {
            clientRoutingDatasource.setDefaultTargetDataSource(targetDataSources.get(ClientDatabaseContextHolder.ClientDatabaseEnum.H2));
        }

        return clientRoutingDatasource;
    }

    @Bean(name = "mongoDataSource")
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(new MongoClient("localhost", 27017), "benchmark");
    }

    @Bean
    public TownService townService() {
        return new TownService(new TownDaoJdbc(clientDatasource()), new TownDaoMongo(mongoDbFactory()));
    }
}
