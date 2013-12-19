/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rottapeli.peli;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.domain.Ball;
import rottapeli.interfaces.Bouncable;
import rottapeli.domain.Rat;

/**
 *
 * @author Pavel
 */
public class EntityListTest {
    
    EntityList list;
    List controlList;
    public EntityListTest() {
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
        controlList = new ArrayList();
    }
    
    @After
    public void tearDown() {
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


}
