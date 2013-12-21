/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rottapeli.domain;

import rottapeli.domain.superclasses.Moveable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.interfaces.Eatable;
import rottapeli.peli.EntityList;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class RatTest {
    
    private Rat rat;
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
         rat.startMovingTo(Const.right);
         rat.update();
         assertTrue(rat.X() > 0.01 && approximates(rat.Y(), 0));
     }
     @Test
     public void ratMovesLeft()
     {
         rat.startMovingTo(Const.left);
         rat.update();
         assertTrue(rat.X() < -0.01 && approximates(rat.Y(), 0));
     }
     @Test
     public void ratMovesUp()
     {
         rat.startMovingTo(Const.up);
         rat.update();
         assertTrue(rat.Y() < -0.01 && approximates(rat.X(), 0));
     }
     @Test
     public void ratMovesDown()
     {
         rat.startMovingTo(Const.down);
         rat.update();
         assertTrue(rat.Y() > 0.01 && approximates(rat.X(), 0));
     }

     @Test
     public void ratEatsCheese()
     {
         Eatable cheese = new Cheese(0, 1.5);
         list.addEntity(rat);
         list.addEntity((Cheese)cheese);
         rat.eat(cheese);
         assertTrue(list.getList(Eatable.class).size() == 0);
     }
     @Test
     public void ratEatsCheese2()
     {
         Eatable cheese = new Cheese(0, 1.5);
         list.addEntity(rat);
         list.addEntity((Cheese)cheese);
         rat.startMovingTo(Const.down);
         boolean b1 = list.getList(Eatable.class).size() > 0;
         rat.update();
         boolean b2 = list.getList(Eatable.class).size() == 0;
         assertTrue(b1 && b2);
     }
     
     @Test
     public void ratstretchesTail()
     {
         list.addEntity(rat);
         rat.startMovingTo(Const.down);
         for (int i = 0; i < 100; i++)  rat.update();
         assertTrue(list.getList(Tail.class).size() > 5);
     }
     @Test
     public void ratDestroysTails()
     {
         list.addEntity(rat);
         rat.startMovingTo(Const.down);
         for (int i = 0; i < 100; i++)  rat.update();
         rat.removeTails();
         assertTrue(list.getList(Tail.class).size() == 0);
     }
}
