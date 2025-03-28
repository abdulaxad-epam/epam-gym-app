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

import javax.sql.DataSource;

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
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
