package pl.bladekp.dbbenchmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    public long executeBenchmark(String query) throws SQLException {
        long startTime = System.currentTimeMillis();
        daoJdbc.execute(query);
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
