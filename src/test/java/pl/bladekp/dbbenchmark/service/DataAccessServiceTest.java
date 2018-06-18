package pl.bladekp.dbbenchmark.service;

import org.junit.Test;
import org.mockito.Mockito;
import pl.bladekp.dbbenchmark.dao.DaoJdbc;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


public class DataAccessServiceTest {

    private final DaoJdbc daoJdbc = mock(DaoJdbc.class);
    private final DataAccessService das = new DataAccessService(daoJdbc, null);

    @Test
    public void shouldExecuteAndReturnExecutionTime() {
        //Arrange
        Mockito.doNothing().when(daoJdbc).execute(null);

        //Act
        long time = das.executeBenchmark(null);

        //Assert
        assertTrue(time >= 0);
    }

}
