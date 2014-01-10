/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rottapeli.domain.superclasses;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.peli.EntityList;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class PositionedTest {
    private Positioned q;
    private EntityList list;
    private Deque<Double> xQueue;
    private Deque<Double> yQueue;
    private List<Double> usedX;
    private List<Double> usedY;
    
    @Before
    public void setUp() {
        list = new EntityList(null);
        xQueue = new ArrayDeque<>();
        yQueue = new ArrayDeque<>();
        usedX = new ArrayList<>();
        usedY = new ArrayList<>();
    }

    @Test
    public void getsNearbyPositions1()
    {
        Positioned p = new Positioned(64,64,1,1);
        list.addEntity(p);
        p.getNearbyPositions(xQueue, yQueue, usedX, usedY);
        assertTrue(xQueue.toString().equals("[66.0, 64.0, 62.0, 64.0]") &&
                yQueue.toString().equals("[64.0, 66.0, 64.0, 62.0]"));
    }
    @Test
    public void getsNearbyPositions2()
    {
        Positioned p = new Positioned(0,0,1,1);
        list.addEntity(p);
        p.getNearbyPositions(xQueue, yQueue, usedX, usedY);
        assertTrue(xQueue.toString().equals("[2.0, 0.0, -2.0, 0.0]") &&
                yQueue.toString().equals("[0.0, 2.0, 0.0, -2.0]"));
    }
    @Test
    public void getsNearbyPositions3()
    {
        Positioned p = new Positioned(127,95,1,1);
        list.addEntity(p);
        p.getNearbyPositions(xQueue, yQueue, usedX, usedY);
        assertTrue(xQueue.toString().equals("[129.0, 127.0, 125.0, 127.0]") &&
                yQueue.toString().equals("[95.0, 97.0, 95.0, 93.0]"));
    }
    @Test
    public void dontGetNearbyPositionsIfOutOfBounds1()
    {
        Positioned p = new Positioned(-1,64,1,1);
        list.addEntity(p);
        p.getNearbyPositions(xQueue, yQueue, usedX, usedY);
        assertTrue(xQueue.toString().equals("[]") &&
                yQueue.toString().equals("[]"));
    }
    @Test
    public void dontGetNearbyPositionsIfOutOfBounds2()
    {
        Positioned p = new Positioned(64,-1,1,1);
        list.addEntity(p);
        p.getNearbyPositions(xQueue, yQueue, usedX, usedY);
        assertTrue(xQueue.toString().equals("[]") &&
                yQueue.toString().equals("[]"));
    }
    @Test
    public void dontGetNearbyPositionsIfOutOfBounds3()
    {
        Positioned p = new Positioned(Const.width,64,1,1);
        list.addEntity(p);
        p.getNearbyPositions(xQueue, yQueue, usedX, usedY);
        assertTrue(xQueue.toString().equals("[]") &&
                yQueue.toString().equals("[]"));
    }
    @Test
    public void dontGetNearbyPositionsIfOutOfBounds4()
    {
        Positioned p = new Positioned(64,Const.height,1,1);
        list.addEntity(p);
        p.getNearbyPositions(xQueue, yQueue, usedX, usedY);
        assertTrue(xQueue.toString().equals("[]") &&
                yQueue.toString().equals("[]"));
    }
    @Test
    public void dontGetNearbyPositionIfAlreadyWasThere()
    {
        Positioned p = new Positioned(64,64,1,1);
        list.addEntity(p);
        usedX.add(1.0);
        usedX.add(2.0);
        usedX.add(64.0);
        usedX.add(3.0);
        usedY.add(71.0);
        usedY.add(37.0);
        usedY.add(64.0);
        usedY.add(43.0);
        p.getNearbyPositions(xQueue, yQueue, usedX, usedY);
        assertTrue(xQueue.toString().equals("[]") &&
                yQueue.toString().equals("[]"));
    }

    @Test
    public void ifFreeSpotJustPlaceIt()
    {
        Positioned p = new Positioned(64,64,1,1);
        p.findNearestFreeSpot();
        assertTrue(p.X() == 64 && p.Y() == 64);
    }
    @Test
    public void ifSpotNotFreePlaceNearBy()
    {
        Positioned p = new Positioned(64,64,1,1);
        list.addEntity(p);
        list.addEntity(new Positioned(64,64,1,1));
        p.findNearestFreeSpot();
        assertTrue( (p.X() == 66 && p.Y() == 64) ||
                    (p.X() == 64 && p.Y() == 66) ||
                    (p.X() == 62 && p.Y() == 64) ||
                    (p.X() == 64 && p.Y() == 62));
    }
    @Test
    public void findWhichNearbySpotIsFree()
    {
        Positioned p = new Positioned(64,64,1,1);
        list.addEntity(p);
        list.addEntity(new Positioned(48,64,128,128));
        p.findNearestFreeSpot();
        assertTrue(p.X() == 64 && p.Y() == 62);
    }
    @Test
    public void findWhichNearbySpotIsFree2()
    {
        Positioned p = new Positioned(64,64,1,1);
        list.addEntity(p);
        list.addEntity(new Positioned(48,48,32,32));
        p.findNearestFreeSpot();
        assertTrue( (p.X() == 82 && p.Y() == 64) ||
                    (p.X() == 64 && p.Y() == 82) ||
                    (p.X() == 46 && p.Y() == 64) ||
                    (p.X() == 64 && p.Y() == 46));
    }
    
    public void setupCollision()
    {
        q = new Positioned(0,0,1,1);
        list.addEntity(q);
    }
    @Test
    public void checksCollision()
    {
        setupCollision();
        Positioned p = new Positioned(0.5, 0.5, 1, 1);
        assertTrue(q.collidesWith(p, false));
    }
    @Test
    public void checksCollision2()
    {
        setupCollision();
        Positioned p = new Positioned(0, 0, 1, 1);
        assertTrue(q.collidesWith(p, false));
    }
    @Test
    public void checksCollision3()
    {
        setupCollision();
        Positioned p = new Positioned(1, 1, 1, 1);
        assertTrue(q.collidesWith(p, false));
    }
    @Test
    public void bogusCollision()
    {
        setupCollision();
        Positioned p = new Positioned(2, 0.5, 1, 1);
        assertTrue(!q.collidesWith(p, false));
    }
    @Test
    public void objectDoesntCollideWithItself()
    {
        setupCollision();
        assertTrue(!q.collidesWith(q, false));
    }

}
