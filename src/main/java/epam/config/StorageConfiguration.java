package epam.config;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class StorageConfiguration {

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    @Scope("prototype")
    public EntityManager entityManager(){
        return entityManagerFactory.createEntityManager();
    }

}
