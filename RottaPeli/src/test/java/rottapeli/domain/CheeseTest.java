/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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

/**
 *
 * @author Pavel
 */
public class CheeseTest {
    
    private Cheese cheese;
    private EntityList list;
    public CheeseTest() {
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
        cheese = new Cheese(0,0);
        list.addEntity(cheese);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void cheeseGetsEaten()
    {
        cheese.getEaten();
        assertTrue(!list.getList(null).contains(cheese));
    }
}
