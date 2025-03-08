package epam;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("epam")
public class AppTest 
    extends TestCase
{
    public void testApp()
    {
        assertTrue( true );
    }
}
