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
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class PlayerInputTest {
    
    public PlayerInputTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

/*    @Test
    public void inputControlsRat()
    {
        Rat rat = new Rat(0,0);
        EntityList list = new EntityList();
        list.addEntity(rat);
        PlayerInput pi = new PlayerInput(list);
        pi.playerGo(1, Const.down);
        rat.update();
        assertTrue(rat.Y() > 0.1);
    }

    @Test
    public void inputDoesntControlWrongThings()
    {
        Rat rat = new Rat(0,0);
        EntityList list = new EntityList();
        list.addEntity(rat);
        PlayerInput pi = new PlayerInput(list);
        pi.playerGo(2, Const.down);
        rat.update();
        assertTrue(rat.X() == 0 && rat.Y() == 0);
    }*/

}
