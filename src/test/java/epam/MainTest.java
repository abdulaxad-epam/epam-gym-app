package epam;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static org.junit.Assert.assertTrue;

@Suite
@SelectPackages("epam")
public class MainTest {
    public void testApp() {
        assertTrue(true);
    }
}
