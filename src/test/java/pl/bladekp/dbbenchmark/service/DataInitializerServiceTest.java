package pl.bladekp.dbbenchmark.service;

import org.junit.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class DataInitializerServiceTest {

    private final DataAccessService das = mock(DataAccessService.class);
    private final DataSource dataSource = mock(DataSource.class);
    private final DataInitializerService dis = new DataInitializerService(dataSource, das);

    @Test
    public void databaseInitializedNoDatasourceTest() {
        //Arrange
        Mockito.doNothing().when(das).executeBatch();
        dis.setTargetType(null);

        //Act
        dis.initialize();

        //Assert
        assertTrue(Mockito.mockingDetails(das).getInvocations().size() > 0);
        assertTrue(Mockito.mockingDetails(dataSource).getInvocations().size() == 0);
    }
}
