/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rottapeli.resource;

import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.domain.Ball;
import rottapeli.domain.superclasses.Entity;
import rottapeli.domain.superclasses.Moveable;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Bouncable;
import rottapeli.interfaces.Updatable;

/**
 *
 * @author Pavel
 */
public class ToolsTest {
    
    private Positioned p;
    private Positioned q;

    public ToolsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        p = new Positioned(0,0,1,1);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void pointIsInside()
    {
        assertTrue(Tools.pointInside(p, 0.5, 0.5));
    }
    @Test
    public void pointIsInside2()
    {
        assertTrue(Tools.pointInside(p, 0, 0));
    }
    @Test
    public void pointIsInside3()
    {
        assertTrue(Tools.pointInside(p, 1, 1));
    }
    @Test
    public void pointNotInside()
    {
        assertTrue(!Tools.pointInside(p, 0.5, 2));
    }

    @Test
    public void objectInsideAnother()
    {
        q = new Positioned(-0.5, -0.5, 1, 1);
        assertTrue(Tools.isInside(q, p));
    }
    @Test
    public void objectInsideAnother2()
    {
        q = new Positioned(0, 0, 1, 1);
        assertTrue(Tools.isInside(q, p));
    }
    @Test
    public void objectInsideAnother3()
    {
        q = new Positioned(-1, -1, 3, 3);
        assertTrue(Tools.isInside(q, p));
    }
}
