package pl.bladekp.dbbenchmark.config;

import org.junit.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class DatabasesConfigTest {

    private final HikariDataSourceCreator dataSourceCreator = mock(HikariDataSourceCreator.class);
    private final DatabasesConfig databasesConfig = new DatabasesConfig(dataSourceCreator);

    @Test
    public void databaseInitializedNoDatasourceTest() {
        //Arrange
        Mockito.doReturn(null).when(dataSourceCreator).createDataSource("test");

        //Act
        DataSource ds = databasesConfig.clientDatasource();

        //Assert
        assertNotNull(ds);
        assertEquals(Mockito.mockingDetails(dataSourceCreator).getInvocations().size(), 4);
    }
}
