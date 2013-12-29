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
import rottapeli.domain.superclasses.Entity;
import rottapeli.interfaces.Eatable;
import rottapeli.interfaces.Hidable;
import rottapeli.peli.EntityList;
import rottapeli.peli.RottaPeli;
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
        list = new EntityList(null);
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
         rat.moveTo(Const.right);
         rat.update();
         assertTrue(rat.X() > 0.01 && approximates(rat.Y(), 0));
     }
     @Test
     public void ratMovesLeft()
     {
         rat.moveTo(Const.left);
         rat.update();
         assertTrue(rat.X() < -0.01 && approximates(rat.Y(), 0));
     }
     @Test
     public void ratMovesUp()
     {
         rat.moveTo(Const.up);
         rat.update();
         assertTrue(rat.Y() < -0.01 && approximates(rat.X(), 0));
     }
     @Test
     public void ratMovesDown()
     {
         rat.moveTo(Const.down);
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
         rat.moveTo(Const.down);
         boolean b1 = list.getList(Eatable.class).size() > 0;
         rat.update();
         boolean b2 = list.getList(Eatable.class).size() == 0;
         assertTrue(b1 && b2);
     }
     @Test
     public void ratDoesntEatCheeseIfItMerelyTouchesSide()
     {
         Eatable cheese = new Cheese(1, 1.5);
         list.addEntity((Cheese)cheese);
         rat.moveTo(Const.down);
         rat.update();
         rat.update();
         assertTrue(list.getList(Eatable.class).size() == 1);
     }
     
     @Test
     public void ratDoesntCreateTailIfOneIsTouchingIt()
     {
         rat.moveTo(Const.down);
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
         rat.moveTo(Const.down);
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
         rat.moveTo(Const.down);
         for (int i = 0; i < 100; i++)  rat.update();
         assertTrue(list.getList(Tail.class).size() > 5);
     }
     @Test
     public void ratDestroysTails()
     {
         rat.moveTo(Const.down);
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
         rat.moveTo(Const.down);
         for (int i = 0; i < 100; i++)  rat.update();
         rat.removeTails();
         assertTrue(list.getList(Tail.class).size() == 1);
     }
     
     public void setupHidingTest()
     {
         Border hole = new Border(0, 5, 1, 1);
         list.addEntity(hole);
         rat.moveTo(Const.down);
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
    public void ratFacesLeftWhenHidingToTheRight()
    {
        Border hole = new Border(5, 0, 1, 1);
        list.addEntity(hole);
        rat.moveTo(Const.right);
        for (int i = 0; i < 10; i++)  rat.update();
        assertTrue(rat.getDirection() == Const.left);
    }
    @Test
    public void ratFacesRightWhenHidingToTheLeft()
    {
        Border hole = new Border(-5, 0, 1, 1);
        list.addEntity(hole);
        rat.moveTo(Const.left);
        for (int i = 0; i < 10; i++)  rat.update();
        assertTrue(rat.getDirection() == Const.right);
    }
    @Test
    public void ratFacesDownWhenHidingUp()
    {
        Border hole = new Border(0, -5, 1, 1);
        list.addEntity(hole);
        rat.moveTo(Const.up);
        for (int i = 0; i < 10; i++)  rat.update();
        assertTrue(rat.getDirection() == Const.down);
    }
    @Test
     public void ratStaysFacingAway()
     {
         setupHidingTest();
         rat.moveTo(Const.down);
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
         rat.moveTo(Const.down);
         rat.update();
        assertTrue(approximates(rat.Y(), 0.5));
    }
    
    @Test
    public void onDeathDestroyTails()
    {
        Tail tail = new Tail(64,64,1,1,rat);
        list.addEntity(tail);
        rat.die();
        assertTrue(list.getList(Tail.class).isEmpty());
    }
    @Test
    public void onDeathPositionToDefaultPosition()
    {
        rat.die();
        assertTrue(rat.X() == Math.round(Const.width / 2) && rat.Y() == 0);
    }
    @Test
    public void onDeathFaceDown()
    {
        rat.moveTo(Const.right);
        rat.die();
        assertTrue(rat.getDirection() == Const.down);
    }
    @Test
    public void onDeathStopMoving()
    {
        rat.moveTo(Const.down);
        for (int i = 0; i < 100; i++)   rat.update();
        rat.die();
        for (int i = 0; i < 100; i++)   rat.update();
        assertTrue(rat.X() == Math.round(Const.width / 2) && rat.Y() == 0);
    }
    @Test
    public void onDeathStopCreatingTails()
    {
        rat.moveTo(Const.down);
        for (int i = 0; i < 100; i++)   rat.update();
        rat.die();
        for (int i = 0; i < 100; i++)   rat.update();
        assertTrue(list.getList(Tail.class).isEmpty());
    }
    @Test
    public void whenAllLivesLostDisappear()
    {
        RottaPeli rp = new RottaPeli(false);
        rp.getEntities().removeAll(Entity.class);
        rp.getEntities().addEntity(rat);
        for (int i = 0; i < Const.initialLifeAmount; i++)   rat.die();
        assertTrue(rp.getEntities().getList(Rat.class).isEmpty());
    }
    
     @Test
     public void dieIfHitOwnTail()
     {
         Tail tail = new Tail(0, 2.5, 1, 1, rat);
         list.addEntity(tail);
         rat.moveTo(Const.down);
         rat.update();
         rat.update();
        assertTrue(list.getList(Tail.class).isEmpty());
     }
     @Test
     public void ratDoesntDieIfItMerelyTouchesTailsSide()
     {
         Tail tail = new Tail(1, 2.5, 1, 1, rat);
         list.addEntity(tail);
         rat.moveTo(Const.down);
         rat.update();
         rat.update();
         rat.update();
        assertTrue(!list.getList(Tail.class).isEmpty());
     }
     //test tail creation coordinates
}
