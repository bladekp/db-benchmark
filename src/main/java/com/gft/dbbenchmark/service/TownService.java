package com.gft.dbbenchmark.service;

import com.gft.dbbenchmark.config.ClientDatabaseContextHolder;
import com.gft.dbbenchmark.dao.TownDao;
import com.gft.dbbenchmark.model.Town;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TownService {

    private TownDao townDao;

    @Autowired
    public TownService(TownDao townDao){
        this.townDao = townDao;
    }

    public Town getOne(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb, Long id){
        ClientDatabaseContextHolder.set(clientDb);
        Town town = townDao.getOne(id);
        ClientDatabaseContextHolder.clear();
        return town;
    }

    public void saveAll(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb, List<Town> townList){
        ClientDatabaseContextHolder.set(clientDb);
        townDao.saveAll(townList);
        ClientDatabaseContextHolder.clear();
    }

    public void clearAll(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb){
        ClientDatabaseContextHolder.set(clientDb);
        townDao.clearAll();
        ClientDatabaseContextHolder.clear();
    }
}
