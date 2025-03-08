package epam.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageConfigurationTest {

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @Mock
    private EntityManager entityManager1;

    @Mock
    private EntityManager entityManager2;

    @InjectMocks
    private StorageConfiguration storageConfiguration;

    @BeforeEach
    void setUp() {
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager1, entityManager2);
    }

    @Test
    void shouldCreateNewEntityManagerEachTime() {
        EntityManager em1 = storageConfiguration.entityManager();
        EntityManager em2 = storageConfiguration.entityManager();

        assertThat(em1).isNotNull();
        assertThat(em2).isNotNull();
        assertThat(em1).isNotSameAs(em2);

        verify(entityManagerFactory, times(2)).createEntityManager();
    }
}
