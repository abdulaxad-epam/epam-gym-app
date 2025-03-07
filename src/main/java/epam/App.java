package epam;

import epam.app.ConsoleApplication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("epam")
public class App 
{
    private final static Log log = LogFactory.getLog(App.class);

    public static void main( String[] args ) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(App.class);

        Object app = applicationContext.getBean("consoleApplication");
        if (app instanceof ConsoleApplication) {
             ((ConsoleApplication) app).run();
        }
    }
}
