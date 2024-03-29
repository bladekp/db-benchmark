package pl.bladekp.dbbenchmark.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ScriptOperations;
import org.springframework.data.mongodb.core.script.ExecutableMongoScript;
import org.springframework.stereotype.Repository;

@Repository
public class DaoMongo implements Dao {

    private MongoTemplate mongoTemplate;

    @Autowired
    public DaoMongo(MongoDbFactory mongoDbFactory) {
        this.mongoTemplate = new MongoTemplate(mongoDbFactory);
    }

    @Override
    public void addToBatch(String query) {

    }

    @Override
    public void executeBatch() {

    }

    @Override
    public void execute(String query) {
        ScriptOperations scriptOps = mongoTemplate.scriptOps();
        ExecutableMongoScript echoScript = new ExecutableMongoScript(query);
        scriptOps.execute(echoScript);
    }
}
