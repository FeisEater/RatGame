
package rottapeli.peli;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pavel
 */
public class RottaPeliTest {
    private RottaPeli rp = new RottaPeli();
    public RottaPeliTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        rp.start();
    }
    
    @After
    public void tearDown() {
        rp.stop();
    }

    @Test
    public void timerIsCreatedAndItWorks()
    {
        assertTrue(rp.isRunning());
    }
    @Test
    public void timerCreatesActionEvents()
    {
        assertTrue(rp.getActionListeners().length > 0);
    }
}
