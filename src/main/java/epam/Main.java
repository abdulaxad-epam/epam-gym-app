package epam;


import epam.config.WebConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.File;

@Slf4j
@ComponentScan("epam")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.setBaseDir(new File("embedded-tomcat").getAbsolutePath());
        tomcat.getConnector();

        String webAppDir = new File(".").getAbsolutePath();
        tomcat.addWebapp("", webAppDir);

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            log.info("Problem occurred with tomcat: " + e.getMessage());
        }

        tomcat.getServer().await();
    }
}
