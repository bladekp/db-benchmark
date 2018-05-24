package pl.bladekp.dbbenchmark.dao;

import pl.bladekp.dbbenchmark.model.Town;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TownDaoMongo implements TownDao {

    private final MongoTemplate mongoTemplate;

    public TownDaoMongo(MongoDbFactory mongoDbFactory) {
        this.mongoTemplate = new MongoTemplate(mongoDbFactory);
    }

    @Override
    public Town getOne(Long id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), Town.class);
    }

    @Override
    public void saveAll(final List<Town> townList) {
        mongoTemplate.insertAll(townList);
    }

    @Override
    public void clearAll() {
        mongoTemplate.dropCollection(Town.class);
    }

    @Override
    public void execute(String query) {
        return;
    }

}
