package epam.config;

import epam.configuration.AopConfiguration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class AopConfigurationTest {

    @Test
    void contextLoads() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(TestConfig.class);

        assertNotNull(context, "Application context should not be null");
    }

    @Configuration
    static class TestConfig extends AopConfiguration {
        @Bean
        public HttpServletRequest httpServletRequest() {
            return Mockito.mock(HttpServletRequest.class);
        }

        @Bean
        public ServletContext servletContext() {
            return Mockito.mock(ServletContext.class);
        }
    }
}
