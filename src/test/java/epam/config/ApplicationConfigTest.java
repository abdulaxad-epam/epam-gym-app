package epam.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ApplicationConfig applicationConfig;

    @BeforeEach
    void setUp() {
        reset(jdbcTemplate);
    }


    @Test
    void shouldHandleSqlExecutionException() {
        String sqlContent = "INSERT INTO training_types (name) VALUES ('Yoga');";
        mockSqlScript(sqlContent);

        doThrow(new RuntimeException("DB error")).when(jdbcTemplate).execute(anyString());

        applicationConfig.executeScript();

        verify(jdbcTemplate, times(1)).execute(anyString());
    }

    private void mockSqlScript(String content) {
        InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        applicationConfig.setSqlScriptStream(inputStream);
    }
}
