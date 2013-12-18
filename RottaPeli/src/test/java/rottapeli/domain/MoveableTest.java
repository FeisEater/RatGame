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
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class MoveableTest {
    
    private Moveable m;
    public MoveableTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        m = new Moveable(0,0,1,1,Const.right,1);
    }
    
    @After
    public void tearDown() {
    }

    public boolean approximates(double d, double exactValue)
    {
        return d > exactValue - 0.01 && d < exactValue + 0.01;
    }

    @Test
    public void speedsAreCorrect()
    {
        assertTrue(approximates(m.xSpeed(), 1) && approximates(m.ySpeed(), 0));
    }

    @Test
    public void movesCorrectly()
    {
        m.move();
        assertTrue(approximates(m.X(), 1) && approximates(m.Y(), 0));
    }

    @Test
    public void directionChangesCorrectly()
    {
        m.setDirection(Const.down);
        assertTrue(approximates(m.xSpeed(), 0) && approximates(m.ySpeed(), 1));
    }

    @Test
    public void directionChangesCorrectlyV2()
    {
        m.setDirection(0,1);
        assertTrue(approximates(m.xSpeed(), 0) && approximates(m.ySpeed(), 1));
    }

    @Test
    public void dontBotherRecalculatingDirectionIfSpeedsAreSame()
    {
        double x = m.xSpeed();
        double y = m.ySpeed();
        m.setDirection(x,y);
        assertTrue(m.xSpeed() == x && m.ySpeed() == y);
    }

    @Test
    public void checksCollision()
    {
        Positioned p = new Positioned(0.5, 0.5, 1, 1);
        assertTrue(m.collidesWith(p));
    }
    @Test
    public void checksCollision2()
    {
        Positioned p = new Positioned(0, 0, 1, 1);
        assertTrue(m.collidesWith(p));
    }
    @Test
    public void checksCollision3()
    {
        Positioned p = new Positioned(1, 1, 1, 1);
        assertTrue(m.collidesWith(p));
    }
    @Test
    public void bogusCollision()
    {
        Positioned p = new Positioned(2, 0.5, 1, 1);
        assertTrue(!m.collidesWith(p));
    }

    @Test
    public void checksCollisionViaMovement()
    {
        Positioned p = new Positioned(2, 0, 1, 1);
        boolean b1 = !m.collidesWith(p);
        m.move();
        boolean b2 = m.collidesWith(p);
        assertTrue(b1 && b2);
    }
    
    @Test
    public void objectDoesntCollideWithItself()
    {
        assertTrue(!m.collidesWith(m));
    }
}
