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
public class BallTest {
    
    private Moveable ball;
    private EntityList list;
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
        ball = new Ball(0, 0, ang, list);
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

}
