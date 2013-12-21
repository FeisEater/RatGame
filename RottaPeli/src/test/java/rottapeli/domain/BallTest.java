/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rottapeli.domain;

import rottapeli.domain.superclasses.Moveable;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Bouncable;
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
public class BallTest {
    
    private Moveable ball;
    private Ball b;
    private EntityList list;
    private Positioned cheese;
    public BallTest() {
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
    }
    
    @After
    public void tearDown() {
    }

    public boolean setupBall(double ang)
    {
        boolean positivex = (Math.cos(ang) > 0);
        boolean positivey = (Math.sin(ang) > 0);
        ball = new Ball(0, 0, ang);
        ball.update();
        boolean b1 = (positivex && ball.X() > 0.01) || (!positivex && ball.X() < -0.01);
        boolean b2 = (positivey && ball.Y() > 0.01) || (!positivey && ball.Y() < -0.01);
        return b1 && b2;
    }
    @Test
     public void ballMovesDownRight()
     {
         assertTrue(setupBall(Const.rightdown));
     }
    @Test
     public void ballMovesDownLeft()
     {
         assertTrue(setupBall(Const.leftdown));
     }
    @Test
     public void ballMovesUpLeft()
     {
         assertTrue(setupBall(Const.leftup));
     }
    @Test
     public void ballMovesUpRight()
     {
         assertTrue(setupBall(Const.rightup));
     }

//Next tests assume that ball's and cheese's width and height = 1.0
//TODO: generalize
    public void setupCollisionTest()
    {
        cheese = new Cheese(0,0);
        list.addEntity(cheese);
    }
    public void setupCorrectionTest()
    {
        b = new Ball(-0.5, -0.5, Const.rightdown);
    }
    /*@Test
    public void correctsXposition()
    {
        setupCollisionTest();
        setupCorrectionTest();
        b.correctXposition((Bouncable)cheese, true);
        assertTrue(b.X() == -1.5 && b.Y() == -0.5);
    }
    @Test
    public void correctsXposition2()
    {
        setupCollisionTest();
        setupCorrectionTest();
        b.correctXposition((Bouncable)cheese, false);
        assertTrue(b.X() == 2.5 && b.Y() == -0.5);
    }
    @Test
    public void correctsYposition()
    {
        setupCollisionTest();
        setupCorrectionTest();
        b.correctYposition((Bouncable)cheese, true);
        assertTrue(b.X() == -0.5 && b.Y() == -1.5);
    }
    @Test
    public void correctsYposition2()
    {
        setupCollisionTest();
        setupCorrectionTest();
        b.correctYposition((Bouncable)cheese, false);
        assertTrue(b.X() == -0.5 && b.Y() == 2.5);
    }
*/
    @Test
    public void bounce1()
    {
        setupCollisionTest();
        ball = new Ball(-1, -2, Const.rightdown);
        list.addEntity(ball);
        ball.update();
        ball.update();
        assertTrue(ball.xSpeed() > 0.1 && ball.ySpeed() < -0.1);
    }
    @Test
    public void bounce2()
    {
        setupCollisionTest();
        ball = new Ball(-2, -2, Const.rightdown);
        list.addEntity(ball);
        ball.update();
        ball.update();
        assertTrue(ball.xSpeed() < -0.1 && ball.ySpeed() < -0.1);
    }
    @Test
    public void bounce3()
    {
        setupCollisionTest();
        ball = new Ball(-2, -1, Const.rightdown);
        list.addEntity(ball);
        ball.update();
        ball.update();
        assertTrue(ball.xSpeed() < -0.1 && ball.ySpeed() > 0.1);
    }


}
