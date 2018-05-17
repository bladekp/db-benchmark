package com.gft.dbbenchmark.service;

import com.gft.dbbenchmark.config.ClientDatabaseContextHolder;
import com.gft.dbbenchmark.dao.TownDao;
import com.gft.dbbenchmark.model.Town;
import org.springframework.beans.factory.annotation.Autowired;

public class TownService {

    private TownDao townDao;

    @Autowired
    public TownService(TownDao townDao){
        this.townDao = townDao;
    }

    public String getFirstTownName(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb){
        ClientDatabaseContextHolder.set(clientDb);
        Town town = this.townDao.getFirst();
        ClientDatabaseContextHolder.clear();
        return town.getName();
    }
}
