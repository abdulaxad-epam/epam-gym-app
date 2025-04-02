package epam.configuration;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.DispatcherServlet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WebInitializerTest {

    private WebInitializer webInitializer;

    @Mock
    private ServletContext servletContext;

    @Mock
    private ServletRegistration.Dynamic dynamicRegistration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webInitializer = new WebInitializer();

        when(servletContext.addServlet(anyString(), any(DispatcherServlet.class))).thenReturn(dynamicRegistration);
    }

    @Test
    void testOnStartup() {
        webInitializer.onStartup(servletContext);

        verify(servletContext).addServlet(eq("dispatcher"), any(DispatcherServlet.class));

        verify(dynamicRegistration).setLoadOnStartup(1);
        verify(dynamicRegistration).addMapping("/");
    }
}
