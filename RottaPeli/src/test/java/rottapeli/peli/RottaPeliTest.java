/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rottapeli.peli;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.domain.Rat;
import rottapeli.domain.superclasses.Entity;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class RottaPeliTest {
    private RottaPeli rp;
    public RottaPeliTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        rp = new RottaPeli(false);
        rp.getEntities().removeAll(Entity.class);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void inputControlsRat()
    {
        Rat rat = new Rat(0,0);
        rp.getEntities().addEntity(rat);
        rp.playerGo(1, Const.down);
        rat.update();
        assertTrue(rat.Y() > 0.1);
    }

    @Test
    public void inputDoesntControlWrongThings()
    {
        Rat rat = new Rat(0,0);
        rp.getEntities().addEntity(rat);
        rp.playerGo(2, Const.down);
        rat.update();
        assertTrue(rat.X() == 0 && rat.Y() == 0);
    }
    
    //test createAndPositionToFreeSpot()
    //test resetPosition()
    //test playerDied()
    //test playerLostAllLives()
    //test playerAteCheese()
    //test getPlayers()
    //test resetGame()
}
