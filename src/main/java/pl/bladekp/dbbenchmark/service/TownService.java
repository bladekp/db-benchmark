package pl.bladekp.dbbenchmark.service;

import pl.bladekp.dbbenchmark.dao.TownDao;
import pl.bladekp.dbbenchmark.dao.TownDaoJdbc;
import pl.bladekp.dbbenchmark.dao.TownDaoMongo;
import pl.bladekp.dbbenchmark.model.Town;
import pl.bladekp.dbbenchmark.config.ClientDatabaseContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TownService {

    private final TownDaoJdbc townDaoJdbc;
    private final TownDaoMongo townDaoMongo;

    @Autowired
    public TownService(TownDaoJdbc townDaoJdbc, TownDaoMongo townDaoMongo) {
        this.townDaoJdbc = townDaoJdbc;
        this.townDaoMongo = townDaoMongo;
    }

    public Town getOne(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb, Long id) {
        TownDao dao = initSource(clientDb);
        Town town = dao.getOne(id);
        clearSource();
        return town;
    }

    public void saveAll(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb, List<Town> townList) {
        TownDao dao = initSource(clientDb);
        dao.saveAll(townList);
        clearSource();
    }

    public void clearAll(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb) {
        TownDao dao = initSource(clientDb);
        dao.clearAll();
        clearSource();
    }

    public long executeBenchmark(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb, String query) {
        TownDao dao = initSource(clientDb);
        long startTime = System.currentTimeMillis();
        dao.execute(query);
        long duration = System.currentTimeMillis() - startTime;
        clearSource();
        return duration;
    }

    private TownDao initSource(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb) {
        if (clientDb != ClientDatabaseContextHolder.ClientDatabaseEnum.MONGO) {
            ClientDatabaseContextHolder.set(clientDb);
            return townDaoJdbc;
        } else {
            return townDaoMongo;
        }
    }

    private void clearSource(){
        ClientDatabaseContextHolder.clear();
    }
}