package com.gft.dbbenchmark.service;

import com.gft.dbbenchmark.config.ClientDatabaseContextHolder;
import com.gft.dbbenchmark.dao.TownDaoJdbc;
import com.gft.dbbenchmark.dao.TownDaoMongo;
import com.gft.dbbenchmark.model.Town;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TownService {

    private final TownDaoJdbc townDaoJdbc;
    private final TownDaoMongo townDaoMongo;

    @Autowired
    public TownService(TownDaoJdbc townDaoJdbc, TownDaoMongo townDaoMongo){
        this.townDaoJdbc = townDaoJdbc;
        this.townDaoMongo = townDaoMongo;
    }

    public Town getOne(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb, Long id){
        if (clientDb != ClientDatabaseContextHolder.ClientDatabaseEnum.MONGO) {
            ClientDatabaseContextHolder.set(clientDb);
            Town town = townDaoJdbc.getOne(id);
            ClientDatabaseContextHolder.clear();
            return town;
        } else {
            return null;
        }
    }

    public void saveAll(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb, List<Town> townList){
        if (clientDb != ClientDatabaseContextHolder.ClientDatabaseEnum.MONGO) {
            ClientDatabaseContextHolder.set(clientDb);
            townDaoJdbc.saveAll(townList);
            ClientDatabaseContextHolder.clear();
        }
    }

    public void clearAll(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb){
        if (clientDb != ClientDatabaseContextHolder.ClientDatabaseEnum.MONGO) {
            ClientDatabaseContextHolder.set(clientDb);
            townDaoJdbc.clearAll();
            ClientDatabaseContextHolder.clear();
        }
    }

    public long executeBenchmark(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb, String query){
        if (clientDb != ClientDatabaseContextHolder.ClientDatabaseEnum.MONGO) {
            ClientDatabaseContextHolder.set(clientDb);
            long startTime = System.currentTimeMillis();
            townDaoJdbc.execute(query);
            long duration = System.currentTimeMillis() - startTime;
            ClientDatabaseContextHolder.clear();
            return duration;
        } else {
            return 0;
        }
    }
}
