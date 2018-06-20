package pl.bladekp.dbbenchmark.dao;

import java.sql.SQLException;

public interface Dao {
    void execute(String query) throws SQLException;
    void addToBatch(String query);
    void executeBatch();
}
