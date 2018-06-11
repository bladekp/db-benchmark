package pl.bladekp.dbbenchmark.service;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pl.bladekp.dbbenchmark.config.ClientDatabaseContextHolder;
import pl.bladekp.dbbenchmark.dao.Dao;
import pl.bladekp.dbbenchmark.dao.DaoJdbc;
import pl.bladekp.dbbenchmark.dao.DaoMongo;

@Service
public class DataAccessService {

    private final DaoJdbc daoJdbc;
    private final DaoMongo daoMongo;

    @Autowired
    public DataAccessService(DaoJdbc daoJdbc, DaoMongo daoMongo) {
        this.daoJdbc = daoJdbc;
        this.daoMongo = daoMongo;
    }

    public long executeBenchmark(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb, String query) {
        Dao dao = initSource(clientDb);
        long startTime = System.currentTimeMillis();
        ClientDatabaseContextHolder.execute(() -> dao.execute(query), clientDb);
        return System.currentTimeMillis() - startTime;
    }

    public void execute(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb, String query) {
        Dao dao = initSource(clientDb);
        ClientDatabaseContextHolder.execute(() -> dao.execute(query), clientDb);
    }

    private Dao initSource(ClientDatabaseContextHolder.ClientDatabaseEnum clientDb) {
        if (clientDb != ClientDatabaseContextHolder.ClientDatabaseEnum.MONGO) {
            return daoJdbc;
        } else {
            return daoMongo;
        }
    }
}
