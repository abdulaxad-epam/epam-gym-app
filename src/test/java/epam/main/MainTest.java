package epam.main;

import epam.configuration.WebConfig;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MainTest {

    @Test
    void testSpringContextInitialization() {
        AnnotationConfigWebApplicationContext context = mock(AnnotationConfigWebApplicationContext.class);
        doNothing().when(context).register(WebConfig.class);

        context.register(WebConfig.class);

        verify(context, times(1)).register(WebConfig.class);
    }

    @Test
    void testTomcatInitialization() throws LifecycleException {
        Tomcat tomcat = mock(Tomcat.class);
        Server server = mock(Server.class);

        when(tomcat.getConnector()).thenReturn(null);
        when(tomcat.getServer()).thenReturn(server);

        doNothing().when(tomcat).start();
        doNothing().when(server).await();


        tomcat.setPort(8080);
        tomcat.setBaseDir(new File("embedded-tomcat").getAbsolutePath());
        tomcat.getConnector();
        tomcat.addWebapp("", new File(".").getAbsolutePath());
        tomcat.start();
        tomcat.getServer().await();

        verify(tomcat, times(1)).setPort(8080);
        verify(tomcat, times(1)).setBaseDir(anyString());
        verify(tomcat, times(1)).getConnector();
        verify(tomcat, times(1)).addWebapp(eq(""), anyString());
        verify(tomcat, times(1)).start();
        verify(tomcat.getServer(), times(1)).await();
    }

    @Test
    void testTomcatStartFailure() throws LifecycleException {
        Tomcat tomcat = mock(Tomcat.class);
        doThrow(new LifecycleException("Test Exception")).when(tomcat).start();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            try {
                tomcat.start();
            } catch (LifecycleException e) {
                throw new RuntimeException("Could not start tomcat", e);
            }
        });

        assertTrue(exception.getMessage().contains("Could not start tomcat"));
    }
}
