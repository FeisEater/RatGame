
package rottapeli.peli;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.domain.Ball;
import rottapeli.domain.Rat;
import rottapeli.domain.superclasses.Entity;
import rottapeli.domain.superclasses.Moveable;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class GameTimerTest {
    private RottaPeli rp;
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
        rp = new RottaPeli(false);
        rp.getEntities().removeAll(Entity.class);
        gt = rp.getTimer();
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
        rp.getEntities().addEntity(e);
        Thread.sleep(100);
        assertTrue(e.X() > 0.1);
    }
    
    @Test
    public void gameIsPaused() throws Exception
    {
        gt.togglePause();
        Moveable e = new Ball(0,0,Const.right);
        rp.getEntities().addEntity(e);
        Thread.sleep(100);
        assertTrue(e.X() == 0.0);
    }

    @Test
    public void gameIsUnpaused() throws Exception
    {
        gt.togglePause();
        Moveable e = new Ball(0,0,Const.right);
        rp.getEntities().addEntity(e);
        Thread.sleep(100);
        boolean b1 = (e.X() == 0.0);
        gt.togglePause();
        Thread.sleep(100);
        boolean b2 = (e.X() > 0.1);
        assertTrue(b1 && b2);
    }
    @Test
    public void gameUpdatesScore() throws Exception
    {
        Rat plr = new Rat(0,0);
        rp.getEntities().addEntity(plr);
        Thread.sleep(100);
        rp.playerAteCheese(1);
        assertTrue(rp.getScore().getPoints(1) < Const.initialBonus);
    }
    @Test
    public void gameDoesntUpdatesScoreWhenPaused() throws Exception
    {
        gt.togglePause();
        Rat plr = new Rat(0,0);
        rp.getEntities().addEntity(plr);
        Thread.sleep(100);
        rp.playerAteCheese(1);
        assertTrue(rp.getScore().getPoints(1) >= Const.initialBonus);
    }
}
