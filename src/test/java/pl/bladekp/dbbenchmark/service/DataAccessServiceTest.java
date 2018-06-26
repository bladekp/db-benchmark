package pl.bladekp.dbbenchmark.service;

import org.junit.Test;
import org.mockito.Mockito;
import pl.bladekp.dbbenchmark.config.ClientDatabaseContextHolder;
import pl.bladekp.dbbenchmark.dao.DaoJdbc;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


public class DataAccessServiceTest {

    private final DaoJdbc daoJdbc = mock(DaoJdbc.class);
    private final DataAccessService das = new DataAccessService(daoJdbc, null);

    @Test
    public void shouldExecuteAndReturnExecutionTime() {
        long time = -1;
        try {
            //Arrange
            Mockito.doNothing().when(daoJdbc).execute(null);

            //Act
            time = das.executeBenchmark(null, null, ClientDatabaseContextHolder.ClientDatabaseEnum.H2);
        } catch (SQLException e){
            e.printStackTrace();
        }

        //Assert
        assertTrue(time >= 0);
    }

}
