/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rottapeli.peli;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.domain.Ball;
import rottapeli.interfaces.Bouncable;
import rottapeli.domain.Rat;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class EntityListTest {
    
    EntityList list;
    List controlList;
    
    @Before
    public void setUp() {
        list = new EntityList(null);
        controlList = new ArrayList();
    }

     @Test
     public void getListWithoutParameter()
     {
        Ball b1 = new Ball(0,0,0);
        Ball b2 = new Ball(1,0,0);
        Rat r = new Rat(2,0);
        controlList.add(b1);
        controlList.add(b2);
        controlList.add(r);
        list.addEntity(b1);
        list.addEntity(b2);
        list.addEntity(r);
        assertTrue(list.getList(null).equals(controlList));
     }

     @Test
     public void getListOfBouncables()
     {
        Ball b1 = new Ball(0,0,0);
        Ball b2 = new Ball(1,0,0);
        Rat r = new Rat(2,0);
        controlList.add(b1);
        controlList.add(b2);
        list.addEntity(b1);
        list.addEntity(b2);
        list.addEntity(r);
        assertTrue(list.getList(Bouncable.class).equals(controlList));
     }

     @Test
     public void removesItemsByClass()
     {
        Ball b1 = new Ball(0,0,0);
        Ball b2 = new Ball(1,0,0);
        Rat r = new Rat(2,0);
        controlList.add(r);
        list.addEntity(b1);
        list.addEntity(b2);
        list.addEntity(r);
        list.removeAll(Bouncable.class);
        assertTrue(list.getList(null).equals(controlList));
     }

     @Test
     public void addedItemKnowEntityList()
     {
         Ball b1 = new Ball(0,0,0);
         list.addEntity(b1);
         assertTrue(b1.getEntities() == list);
     }
     @Test
     public void removedItemForgetsEntityList()
     {
         Ball b1 = new Ball(0,0,0);
         list.addEntity(b1);
         boolean q1 = b1.getEntities() == list;
         list.removeEntity(b1);
         boolean q2 = b1.getEntities() == null;
         assertTrue(q1 && q2);
     }
     @Test
     public void removedItemForgetsEntityList2()
     {
         Ball b1 = new Ball(0,0,0);
         controlList.add(b1);
         list.addEntity(b1);
         boolean q1 = b1.getEntities() == list;
         list.removeAll(controlList);
         boolean q2 = b1.getEntities() == null;
         assertTrue(q1 && q2);
     }

     @Test
     public void updatesEntities()
     {
         Ball ball = new Ball(0,0,Const.right);
         list.addEntity(ball);
         list.update();
         assertTrue(ball.X() > 0.1);
     }
}
