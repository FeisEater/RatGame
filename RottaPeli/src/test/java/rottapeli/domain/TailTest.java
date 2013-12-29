package rottapeli.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.peli.EntityList;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class TailTest {
    
    public TailTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void onDeathKillOwner()
    {
        Rat rat = new Rat(64, 64);
        EntityList list = new EntityList(null);
        list.addEntity(rat);
        Tail tail = new Tail(0,0,1,1,rat);
        tail.die();
        assertTrue(list.getList(Tail.class).isEmpty());
    }
}
