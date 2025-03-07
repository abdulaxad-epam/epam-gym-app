package epam.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ApplicationConfig {

    private static final Log log = LogFactory.getLog(ApplicationConfig.class);
    private final EntityManager entityManager;

//    @PostConstruct
//    @DependsOn("entityManager")
//    public void trainingTypes() {
//        executeScript();
//    }
//
//    @Transactional
//    public void executeScript() {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
//                Objects.requireNonNull(getClass().getResourceAsStream("/database_initializer/training_types.sql")),
//                StandardCharsets.UTF_8))) {
//
//            StringBuilder sql = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sql.append(line).append("\n");
//            }
//
//            String[] queries = sql.toString().split(";");
//            for (String query : queries) {
//                String trimmedQuery = query.trim();
//                if (!trimmedQuery.isEmpty()) {
//                    entityManager.createNativeQuery(trimmedQuery).executeUpdate();
//                }
//            }
//
//        } catch (Exception e) {
//            log.error("Error executing script /database_initializer/training_types.sql: " + e.getMessage(), e);
//        }
//    }
}
