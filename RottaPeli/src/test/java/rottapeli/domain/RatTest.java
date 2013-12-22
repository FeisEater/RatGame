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
import rottapeli.interfaces.Hidable;
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
        list.addEntity(rat);
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
         list.addEntity((Cheese)cheese);
         rat.eat(cheese);
         assertTrue(list.getList(Eatable.class).size() == 0);
     }
     @Test
     public void ratEatsCheese2()
     {
         Eatable cheese = new Cheese(0, 1.5);
         list.addEntity((Cheese)cheese);
         rat.startMovingTo(Const.down);
         boolean b1 = list.getList(Eatable.class).size() > 0;
         rat.update();
         boolean b2 = list.getList(Eatable.class).size() == 0;
         assertTrue(b1 && b2);
     }
     
     @Test
     public void ratDoesntCreateTailIfOneIsTouchingIt()
     {
         rat.startMovingTo(Const.down);
         Tail tail = new Tail(0,0.5,1,1,rat);
         list.addEntity(tail);
         rat.update();
         boolean b1 = list.getList(Tail.class).size() == 1;
         rat.update();
         boolean b2 = list.getList(Tail.class).size() == 2;
         assertTrue(b1 && b2);
     }
     @Test
     public void dontGiveARatsAssWhenItsSomeoneElsesTail()
     {
         rat.startMovingTo(Const.down);
         Rat someoneElse = new Rat(10,0);
         list.addEntity(someoneElse);
         Tail tail = new Tail(0,0.5,1,1,someoneElse);
         list.addEntity(tail);
         rat.update();
         assertTrue(list.getList(Tail.class).size() == 2);
     }
     @Test
     public void ratstretchesTail()
     {
         rat.startMovingTo(Const.down);
         for (int i = 0; i < 100; i++)  rat.update();
         assertTrue(list.getList(Tail.class).size() > 5);
     }
     @Test
     public void ratDestroysTails()
     {
         rat.startMovingTo(Const.down);
         for (int i = 0; i < 100; i++)  rat.update();
         rat.removeTails();
         assertTrue(list.getList(Tail.class).size() == 0);
     }
     @Test
     public void dontDestroyTailsThatDontBelongToYou()
     {
         Rat someoneElse = new Rat(10,0);
         Tail someoneElsesTail = new Tail(0,0,0,0, someoneElse);
         list.addEntity(rat);
         list.addEntity(someoneElsesTail);
         rat.startMovingTo(Const.down);
         for (int i = 0; i < 100; i++)  rat.update();
         rat.removeTails();
         assertTrue(list.getList(Tail.class).size() == 1);
     }
     
     public void setupHidingTest()
     {
         Border hole = new Border(0, 5, 1, 1);
         list.addEntity(hole);
         rat.startMovingTo(Const.down);
         for (int i = 0; i < 10; i++)  rat.update();
     }
     @Test
     public void ratDestroysTailsWhenItHides()
     {
         setupHidingTest();
         assertTrue(list.getList(Tail.class).size() == 0);
     }
     @Test
     public void ratfacesAwayWhenItHides()
     {
         setupHidingTest();
         assertTrue(approximates(rat.getDirection(), Const.up));
     }
     @Test
     public void ratStaysFacingAway()
     {
         setupHidingTest();
         rat.startMovingTo(Const.down);
         rat.update();
         assertTrue(approximates(rat.getDirection(), Const.up));
     }
    @Test
    public void ratStaysStillWhenItHides()
    {
        setupHidingTest();
        for (int i = 0; i < 1000; i++)  rat.update();
        assertTrue(approximates(rat.X(), 0) && approximates(rat.Y(), 4.0));
    }
    @Test
    public void ratCorrectsPositionWhenHides()
    {
         Border hole = new Border(0, 1.5, 1, 1);
         list.addEntity(hole);
         rat.startMovingTo(Const.down);
         rat.update();
        assertTrue(approximates(rat.Y(), 0.5));
    }
}
