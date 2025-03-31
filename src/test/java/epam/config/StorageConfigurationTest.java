package epam.config;

import epam.configuration.StorageConfiguration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StorageConfigurationTest {

    @InjectMocks
    private StorageConfiguration storageConfiguration;

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @Mock
    private EntityManager entityManager;

    @Mock
    private DataSource dataSource;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        setField(storageConfiguration, "driver", "org.h2.Driver");
        setField(storageConfiguration, "url", "jdbc:h2:mem:testdb");
        setField(storageConfiguration, "username", "sa");
        setField(storageConfiguration, "password", "");

        System.setProperty("datasource.driver", "org.h2.Driver");
        System.setProperty("datasource.url", "jdbc:h2:mem:testdb");
        System.setProperty("datasource.username", "sa");
        System.setProperty("datasource.password", "");
    }

    private void setField(Object target, String fieldName, String value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void dataSource_ShouldReturnConfiguredDataSource() throws Exception {
        DataSource dataSource = storageConfiguration.dataSource();

        assertThat(dataSource).isNotNull();
        assertThat(dataSource).isInstanceOf(DriverManagerDataSource.class);

        DriverManagerDataSource driverManagerDataSource = (DriverManagerDataSource) dataSource;
        assertThat(driverManagerDataSource.getUrl()).isEqualTo("jdbc:h2:mem:testdb");
        assertThat(driverManagerDataSource.getUsername()).isEqualTo("sa");
        assertThat(driverManagerDataSource.getPassword()).isEqualTo("");

        assertThat(driverManagerDataSource.getConnection().getMetaData().getDriverName()).contains("H2");
    }


    @Test
    void entityManagerFactory_ShouldReturnNotNull() {
        EntityManagerFactory factory = storageConfiguration.entityManagerFactory();
        assertThat(factory).isNotNull();
    }

    @Test
    void entityManager_ShouldReturnEntityManagerInstance() {
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        EntityManager em = storageConfiguration.entityManager(entityManagerFactory);
        assertThat(em).isNotNull();
        verify(entityManagerFactory, times(1)).createEntityManager();
    }

    @Test
    void jdbcTemplate_ShouldReturnJdbcTemplateInstance() {
        JdbcTemplate jdbcTemplate = storageConfiguration.jdbcTemplate(dataSource);
        assertThat(jdbcTemplate).isNotNull();
        verifyNoInteractions(entityManagerFactory);
    }
}
