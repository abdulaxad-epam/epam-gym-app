package epam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("epam")
public class App 
{
    private final static Log log = LogFactory.getLog(App.class);

    public static void main( String[] args ) {
        new AnnotationConfigApplicationContext(App.class);
    }
}
