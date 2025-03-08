package epam.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class ApplicationConfig {

    private static final Log log = LogFactory.getLog(ApplicationConfig.class);
    private final JdbcTemplate jdbcTemplate;
    private InputStream sqlScriptStream;

    @PostConstruct
    public void trainingTypes() {
        executeScript();
    }

    public void executeScript() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getSqlScriptStream(), StandardCharsets.UTF_8))) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }

            String[] queries = sql.toString().split(";");
            for (String query : queries) {
                String trimmedQuery = query.trim();
                if (!trimmedQuery.isEmpty()) {
                    jdbcTemplate.execute(trimmedQuery);
                }
            }

        } catch (Exception e) {
            log.error("Error executing script /database_initializer/training_types.sql: " + e.getMessage(), e);
        }
    }

    public InputStream getSqlScriptStream() {
        if (sqlScriptStream != null) {
            return sqlScriptStream;
        }
        return Objects.requireNonNull(getClass().getResourceAsStream("/database_initializer/training_types.sql"));
    }


    public void setSqlScriptStream(InputStream sqlScriptStream) {
        this.sqlScriptStream = sqlScriptStream;
    }
}