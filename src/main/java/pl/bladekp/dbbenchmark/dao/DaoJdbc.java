package pl.bladekp.dbbenchmark.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class DaoJdbc implements Dao {

    private final DataSource dataSource;
    private Statement statement;

    @Autowired
    public DaoJdbc(@Qualifier("jdbcDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addToBatch(String query) {
        try {
            if (statement == null) {
                statement = dataSource.getConnection().createStatement();
            }
            statement.addBatch(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeBatch() {
        try {
            statement.executeBatch();
            statement = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(String query) {
        try {
            dataSource.getConnection().createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
