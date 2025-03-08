package epam.config;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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
