package pl.bladekp.dbbenchmark.dao;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DaoMongo implements Dao {

    private MongoTemplate mongoTemplate;

    public DaoMongo(MongoDbFactory mongoDbFactory) {
        this.mongoTemplate = new MongoTemplate(mongoDbFactory);
    }

    @Override
    public void execute(String query) {

    }
}
