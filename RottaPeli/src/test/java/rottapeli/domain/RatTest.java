/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class RatTest {
    
    private Moveable rat;
    private EntityList list;
    public RatTest() {
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
        rat = new Rat(0, 0);
    }
    
    @After
    public void tearDown() {
    }

     @Test
     public void ratDoesntMoveWhenSpawned()
     {
        rat.update();
        assertTrue(rat.X() == 0 && rat.Y() == 0);
     }

     @Test
     public void ratDoesntMoveWhenSpawnedWithMultipleUpdates()
     {
        for (int i = 0; i < 1000; i++)  rat.update();
        assertTrue(rat.X() == 0 && rat.Y() == 0);
     }

     public boolean approximates(double d, double exactValue)
     {
         return d > exactValue - 0.01 && d < exactValue + 0.01;
     }
     
     @Test
     public void ratMovesRight()
     {
         rat.setDirection(Const.right);
         rat.update();
         assertTrue(rat.X() > 0.01 && approximates(rat.Y(), 0));
     }
     @Test
     public void ratMovesLeft()
     {
         rat.setDirection(Const.left);
         rat.update();
         assertTrue(rat.X() < -0.01 && approximates(rat.Y(), 0));
     }
     @Test
     public void ratMovesUp()
     {
         rat.setDirection(Const.up);
         rat.update();
         assertTrue(rat.Y() < -0.01 && approximates(rat.X(), 0));
     }
     @Test
     public void ratMovesDown()
     {
         rat.setDirection(Const.down);
         rat.update();
         assertTrue(rat.Y() > 0.01 && approximates(rat.X(), 0));
     }

}
