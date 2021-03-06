/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rottapeli.resource;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.domain.superclasses.Positioned;

/**
 *
 * @author Pavel
 */
public class ToolsTest {
    
    private Positioned p;
    private Positioned q;
    
    @Before
    public void setUp() {
        p = new Positioned(0,0,1,1);
    }

    @Test
    public void pointIsInside()
    {
        assertTrue(Tools.pointInside(p, 0.5, 0.5, false));
    }
    @Test
    public void pointIsInside2()
    {
        assertTrue(Tools.pointInside(p, 0, 0, false));
    }
    @Test
    public void pointIsInside3()
    {
        assertTrue(Tools.pointInside(p, 1, 1, false));
    }
    @Test
    public void pointNotInside()
    {
        assertTrue(!Tools.pointInside(p, 0.5, 2, false));
    }
    @Test
    public void pointIsInsideNotOnTheEdge()
    {
        assertTrue(Tools.pointInside(p, 0.5, 0.5, true));
    }
    @Test
    public void ifPointIsOnTheEdgeDontCountWithSpecialParameter()
    {
        assertTrue(!Tools.pointInside(p, 0, 0, true));
    }
    @Test
    public void ifPointIsOnTheEdgeDontCountWithSpecialParameter2()
    {
        assertTrue(!Tools.pointInside(p, 1, 0.5, true));
    }

    @Test
    public void objectInsideAnother()
    {
        q = new Positioned(-0.5, -0.5, 1, 1);
        assertTrue(Tools.isInside(q, p, false));
    }
    @Test
    public void objectInsideAnother2()
    {
        q = new Positioned(0, 0, 1, 1);
        assertTrue(Tools.isInside(q, p, false));
    }
    @Test
    public void objectInsideAnother3()
    {
        q = new Positioned(-1, -1, 3, 3);
        assertTrue(Tools.isInside(q, p, false));
    }
    
    @Test
    public void objectInsideAnotherWithSpecialParameter()
    {
        q = new Positioned(-0.5, -0.5, 1, 1);
        assertTrue(Tools.isInside(q, p, true));
    }
    @Test
    public void objectInsideAnotherWithSpecialParameter2()
    {
        q = new Positioned(0, -0.5, 1, 1);
        assertTrue(Tools.isInside(q, p, true));
    }
    @Test
    public void objectInsideAnotherWithSpecialParameter3()
    {
        q = new Positioned(-0.5, 0, 1, 1);
        assertTrue(Tools.isInside(q, p, true));
    }
    @Test
    public void obectsOccupySameLocationWithSpecialParameter()
    {
        q = new Positioned(0, 0, 1, 1);
        assertTrue(Tools.isInside(q, p, true));
    }
    @Test
    public void touchingEdgeIsntEnoughWithSpecialParameter()
    {
        q = new Positioned(-1, -0.5, 1, 1);
        assertTrue(!Tools.isInside(q, p, true));
    }

    @Test
    public void roundsCorrectly1()
    {
        assertTrue(Tools.round(1.12345678) == 1.12);
    }
    @Test
    public void roundsCorrectly2()
    {
        assertTrue(Tools.round(1.129999345) == 1.13);
    }
    @Test
    public void roundsCorrectly3()
    {
        assertTrue(Tools.round(1.12) == 1.12);
    }
    @Test
    public void roundsCorrectly4()
    {
        assertTrue(Tools.round(1.1) == 1.1);
    }
    @Test
    public void roundsCorrectly5()
    {
        assertTrue(Tools.round(1) == 1);
    }
    @Test
    public void roundsCorrectly6()
    {
        assertTrue(Tools.round(2.9999999945) == 3);
    }

    @Test
    public void randomDiagonalDirectionIsRandom()
    {
        int[] count = {0,0,0,0};
        for (int i = 0; i < 1000; i++)
        {
            double dir = Tools.randomDiagonalDirection();
            if (dir == Const.leftdown)  count[0]++;
            if (dir == Const.rightdown)  count[1]++;
            if (dir == Const.leftup)  count[2]++;
            if (dir == Const.rightup)  count[3]++;
        }
        boolean equalDistribution = true;
        int sum = 0;
        for (int i = 0; i < 4; i++)
        {
            sum += count[i];
            if (count[i] < 200 || count[i] > 300)
                equalDistribution = false;
        }
        assertTrue(equalDistribution && sum == 1000);
    }
}
