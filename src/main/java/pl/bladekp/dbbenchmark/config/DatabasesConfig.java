package pl.bladekp.dbbenchmark.config;

import com.mongodb.MongoClient;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.bladekp.dbbenchmark.dao.DaoJdbc;
import pl.bladekp.dbbenchmark.dao.DaoMongo;
import pl.bladekp.dbbenchmark.service.DataAccessService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

@Configuration
@EnableTransactionManagement
public class DatabasesConfig {

    private final HikariDataSourceCreator dataSourceCreator;

    @Autowired
    public DatabasesConfig(HikariDataSourceCreator dataSourceCreator) {
        this.dataSourceCreator = dataSourceCreator;
    }

    @Bean(name = "jdbcDataSource")
    public DataSource clientDatasource() {
        Map<Object, Object> targetDataSources = new HashMap<>();

        Stream
                .of(ClientDatabaseContextHolder.ClientDatabaseEnum.values())
                .filter(db -> db != ClientDatabaseContextHolder.ClientDatabaseEnum.MONGO && db.isEnabled())
                .forEach(db -> targetDataSources.put(db, dataSourceCreator.createDataSource(db.getDatasourceNamespace())));

        ClientDataSourceRouter clientRoutingDatasource = new ClientDataSourceRouter();
        clientRoutingDatasource.setTargetDataSources(targetDataSources);
        if (targetDataSources.isEmpty()) {
            throw new RuntimeException("At least one datasource should be provided.");
        } else {
            clientRoutingDatasource.setDefaultTargetDataSource(targetDataSources.get(ClientDatabaseContextHolder.ClientDatabaseEnum.defaultDatabase()));
        }

        return clientRoutingDatasource;
    }

    @Bean(name = "mongoDataSource")
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(new MongoClient("localhost", 27017), "benchmark");
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }
}
