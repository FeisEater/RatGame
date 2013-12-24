
package rottapeli.peli;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.domain.Ball;
import rottapeli.domain.superclasses.Entity;
import rottapeli.domain.superclasses.Moveable;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class GameTimerTest {
    private EntityList list;
    private GameTimer gt;
    public GameTimerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        list = new EntityList();
        gt = new GameTimer(list, null, null);
        gt.start();
    }
    
    @After
    public void tearDown() {
        gt.stop();
    }

    @Test
    public void timerIsCreatedAndItWorks()
    {
        assertTrue(gt.isRunning());
    }
    @Test
    public void timerCreatesActionEvents()
    {
        assertTrue(gt.getActionListeners().length > 0);
    }
    
    @Test
    public void gameUpdatesEntities() throws Exception
    {
        Moveable e = new Ball(0,0,Const.right);
        list.addEntity(e);
        Thread.sleep(100);
        assertTrue(e.X() > 0.1);
    }
}
