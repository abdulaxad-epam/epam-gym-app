package epam.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerConfiguration.class);

    @PostConstruct
    public void init() {
        logger.info("SwaggerConfig initialized.");
    }
    @Bean
    public OpenAPI customOpenAPI() {
        System.out.println("✅ OpenAPI bean is being created!");
        return new OpenAPI()
                .info(new Info().title("API Documentation")
                        .version("1.0")
                        .description("Spring MVC API Documentation"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }

}
