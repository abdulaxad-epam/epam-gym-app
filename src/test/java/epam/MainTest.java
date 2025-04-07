package epam;

import junit.framework.TestCase;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectPackages("epam")
@SpringBootTest(classes = Main.class)
public class MainTest
        extends TestCase {
    public void testApp() {
        assertTrue(true);
    }
}
