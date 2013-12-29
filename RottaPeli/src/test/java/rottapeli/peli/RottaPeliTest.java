/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rottapeli.peli;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.domain.Ball;
import rottapeli.domain.Border;
import rottapeli.domain.Cheese;
import rottapeli.domain.PlacementBlocker;
import rottapeli.domain.Rat;
import rottapeli.domain.superclasses.Entity;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class RottaPeliTest {
    private RottaPeli rp;
    
    private class TestRat extends Rat
    {
        @Override
        public int playerID()   {return 2;}
    }
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
    public void gameIsResetted()
    {
        rp.resetGame();
        assertTrue(rp.getEntities().getList(Rat.class).size() == 1 &&
                rp.getEntities().getList(Ball.class).size() == Const.ballAmountInFirstLevel &&
                rp.getEntities().getList(Cheese.class).size() == Const.cheeseamount &&
                rp.getEntities().getList(Border.class).size() == 4 &&
                rp.getEntities().getList(PlacementBlocker.class).size() == 4);
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
    
    @Test
    public void positionEntitiesToFreeSpots()
    {
        for (int i = 0; i < 25; i++)
            rp.createAndPositionToFreeSpot(new Positioned(Const.width / 2, Const.height / 2, 1, 1));
        boolean[] b = {false, false, false, false};
        List<Positioned> list = rp.getEntities().getList(Positioned.class);
        for (Positioned p : list)
        {
            if (p.X() == Const.width / 2 - 6 && p.Y() == Const.height / 2)    b[0] = true;
            if (p.X() == Const.width / 2 + 6 && p.Y() == Const.height / 2)    b[1] = true;
            if (p.X() == Const.width / 2 && p.Y() == Const.height / 2 - 6)    b[2] = true;
            if (p.X() == Const.width / 2 && p.Y() == Const.height / 2 + 6)    b[3] = true;
        }
        assertTrue(b[0] && b[1] && b[2] && b[3]);
    }

    @Test
    public void resetBalls()
    {
        rp.resetGame();
        List<Ball> list = rp.getEntities().getList(Ball.class);
        double x = list.get(0).X();
        double y = list.get(1).Y();
        rp.resetPosition(Ball.class, false);
        assertTrue(x != list.get(0).X() && y != list.get(1).Y());
    }
    @Test
    public void resetRatAndPositionToFreeSpot()
    {
        rp.createBorders();
        Rat rat = new Rat(0,0);
        rp.getEntities().addEntity(rat);
        rp.resetPosition(Rat.class, true);
        assertTrue(rat.X() == Math.round(Const.width / 2) && 
                rat.Y() == Const.placementBlockerThickness + rat.getHeight() * 2);
    }
    
    @Test
    public void onDeathLoseALife()
    {
        rp.resetGame();
        rp.playerDied(1);
        assertTrue(rp.getScore().getLives(1) == Const.initialLifeAmount - 1);
    }
    @Test
    public void whenSinglePlayerResetBallsOnDeath()
    {
        rp.resetGame();
        List<Ball> list = rp.getEntities().getList(Ball.class);
        double x = list.get(0).X();
        double y = list.get(1).Y();
        rp.playerDied(1);
        assertTrue(x != list.get(0).X() && y != list.get(1).Y());
    }
    @Test
    public void whenMultiPlayerDontResetBallsOnDeath()
    {
        rp.resetGame();
        rp.getEntities().addEntity(new TestRat());
        List<Ball> list = rp.getEntities().getList(Ball.class);
        double x = list.get(0).X();
        double y = list.get(1).Y();
        rp.playerDied(1);
        assertTrue(x == list.get(0).X() && y == list.get(1).Y());
    }
    @Test
    public void whenPlayerLosesAllLivesRemoveRat()
    {
        rp.resetGame();
        for (int i = 0; i < Const.initialLifeAmount; i++)
            rp.playerDied(1);
        assertTrue(rp.getEntities().getList(Rat.class).isEmpty());
    }
    
    @Test
    public void onePlayerInGame()
    {
        rp.resetGame();
        assertTrue(rp.getPlayers().size() == 1);
    }
    @Test
    public void twoPlayersInGame()
    {
        rp.resetGame();
        rp.getEntities().addEntity(new TestRat());
        assertTrue(rp.getPlayers().size() == 2);
    }
    @Test
    public void noPlayersInGame()
    {
        assertTrue(rp.getPlayers().size() == 0);
    }
    
    @Test
    public void whenAllCheeseIsEatenResetRat()
    {
        rp.resetGame();
        List<Positioned> rats = rp.getEntities().getList(Rat.class);
        rats.get(0).setPos(64, 64);
        rp.getEntities().removeAll(Cheese.class);
        rp.playerAteCheese(1);
        assertTrue(rats.get(0).X() == Math.round(Const.width / 2) &&
                rats.get(0).Y() == 0);
    }
    @Test
    public void whenAllCheeseIsEatenResetBalls()
    {
        rp.resetGame();
        List<Positioned> balls = rp.getEntities().getList(Ball.class);
        double x = balls.get(0).X();
        double y = balls.get(0).Y();
        rp.getEntities().removeAll(Cheese.class);
        rp.playerAteCheese(1);
        assertTrue(x != balls.get(0).X() && y != balls.get(1).Y());
    }
    @Test
    public void whenAllCheeseIsEatenResetCheese()
    {
        rp.resetGame();
        List<Positioned> cheese = rp.getEntities().getList(Cheese.class);
        double x = cheese.get(0).X();
        double y = cheese.get(0).Y();
        rp.getEntities().removeAll(Cheese.class);
        rp.playerAteCheese(1);
        cheese = rp.getEntities().getList(Cheese.class);
        assertTrue(cheese.size() > 0 && x != cheese.get(0).X() && y != cheese.get(1).Y());
    }
    @Test
    public void whenAllCheeseIsEatenAddBall()
    {
        rp.resetGame();
        rp.getEntities().removeAll(Cheese.class);
        rp.playerAteCheese(1);
        assertTrue(rp.getEntities().getList(Ball.class).size() == Const.ballAmountInFirstLevel + 1);
    }
    @Test
    public void whenAllCheeseIsEatenGiveTimeBonus()
    {
        rp.resetGame();
        rp.getEntities().addEntity(new TestRat());
        rp.getScore().resetScore(rp.getPlayers());
        rp.getEntities().removeAll(Cheese.class);
        rp.playerAteCheese(1);
        assertTrue(rp.getScore().getPoints(1) >= Const.initialBonus &&
                rp.getScore().getPoints(2) >= Const.initialBonus);
    }
}
