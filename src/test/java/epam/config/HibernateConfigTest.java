package epam.config;

import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HibernateConfigTest {

    @Mock
    private Server h2TcpServer;

    @Mock
    private Server h2WebServer;

    @InjectMocks
    private HibernateConfig hibernateConfig;

    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = hibernateConfig.dataSource();
    }

    @Test
    void shouldCreateH2TcpServer() throws SQLException {
        Server server = hibernateConfig.h2TcpServer();
        assertThat(server).isNotNull();
    }

    @Test
    void shouldCreateH2WebServer() throws SQLException {
        Server server = hibernateConfig.h2WebServer();
        assertThat(server).isNotNull();
    }

    @Test
    void shouldCreateDataSource() {
        assertThat(dataSource).isNotNull();
        assertThat(dataSource instanceof DriverManagerDataSource).isTrue();

        DriverManagerDataSource ds = (DriverManagerDataSource) dataSource;
        assertThat(ds.getUrl()).isEqualTo("jdbc:h2:tcp://localhost:9092/~/gym;AUTO_SERVER=TRUE");
        assertThat(ds.getUsername()).isEqualTo("abdulaxad");
        assertThat(ds.getPassword()).isEqualTo("root");
    }

    @Test
    void shouldCreateJdbcTemplate() {
        JdbcTemplate jdbcTemplate = hibernateConfig.jdbcTemplate(dataSource);
        assertThat(jdbcTemplate).isNotNull();
    }

    @Test
    void shouldCreateSessionFactory() {
        LocalSessionFactoryBean sessionFactory = hibernateConfig.sessionFactory();
        assertThat(sessionFactory).isNotNull();
    }

    @Test
    void shouldCreateTransactionManager() {
        HibernateTransactionManager transactionManager = hibernateConfig.transactionManager();
        assertThat(transactionManager).isNotNull();
    }
}
