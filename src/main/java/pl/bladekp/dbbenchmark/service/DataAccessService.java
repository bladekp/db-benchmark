package pl.bladekp.dbbenchmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bladekp.dbbenchmark.config.ClientDatabaseContextHolder;
import pl.bladekp.dbbenchmark.dao.DaoJdbc;
import pl.bladekp.dbbenchmark.dao.DaoMongo;

import java.sql.SQLException;

@Component
public class DataAccessService {

    private final DaoJdbc daoJdbc;
    private final DaoMongo daoMongo;

    @Autowired
    public DataAccessService(DaoJdbc daoJdbc, DaoMongo daoMongo) {
        this.daoJdbc = daoJdbc;
        this.daoMongo = daoMongo;
    }

    public long executeBenchmark(String sql, String mql, ClientDatabaseContextHolder.ClientDatabaseEnum db) throws SQLException {
        long startTime = System.currentTimeMillis();
        if (db == ClientDatabaseContextHolder.ClientDatabaseEnum.MONGO){
            daoMongo.execute(mql);
        } else {
            daoJdbc.execute(sql);
        }
        return System.currentTimeMillis() - startTime;
    }

    public void execute(String query) throws SQLException {
        daoJdbc.execute(query);
    }

    public void addToBatch(String query) {
        daoJdbc.addToBatch(query);
    }

    public void executeBatch() {
        daoJdbc.executeBatch();
    }
}
