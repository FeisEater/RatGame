/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rottapeli.peli;

import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class ScoreKeeperTest {
    private ScoreKeeper score;
    private RottaPeli rp;
    public ScoreKeeperTest() {
    }
        
    @Before
    public void setUp() {
        rp = new RottaPeli(false);
        rp.resetGame();
        rp.getTimer().setPaused(true);
        score = new ScoreKeeper(rp);
        Set<Integer> players = new HashSet<>();
        players.add(1);
        players.add(2);
        score.resetScore(players);
    }

    @Test
    public void scoreIsReset()
    {
        assertTrue(score.getPoints(1) == 0 && score.getPoints(2) == 0 &&
                score.getLives(1) == Const.initialLifeAmount &&
                score.getLives(2) == Const.initialLifeAmount &&
                score.getCombo(1) == Const.cheesePoints &&
                score.getCombo(2) == Const.cheesePoints &&
                score.getBonus() == Const.initialBonus);

    }
    @Test
    public void scoreIsAdded()
    {
        score.addScore(1, 100);
        assertTrue(score.getPoints(1) == 100 && score.getPoints(2) == 0);
    }
    @Test
    public void morePointsGiveExtraLife()
    {
        score.addScore(1, Const.extraLifeReward);
        assertTrue(score.getLives(1) == Const.initialLifeAmount + 1 && 
                score.getLives(2) == Const.initialLifeAmount);
    }
    @Test
    public void aLotOfPointsGiveSeveralExtraLives()
    {
        int n = 100;
        score.addScore(1, Const.extraLifeReward * n);
        assertTrue(score.getLives(1) == Const.initialLifeAmount + n && 
                score.getLives(2) == Const.initialLifeAmount);
    }
    
    @Test
    public void eatingCheeseIncreasesCombo()
    {
        score.pointsForEatingCheese(1);
        score.pointsForEatingCheese(1);
        score.pointsForEatingCheese(1);
        assertTrue(score.getPoints(1) == Const.cheesePoints * 7 && 
                score.getPoints(2) == 0);
    }
    @Test
    public void interruptCombo()
    {
        score.pointsForEatingCheese(1);
        score.pointsForEatingCheese(1);
        score.pointsForEatingCheese(1);
        score.resetCombo(1);
        score.pointsForEatingCheese(1);
        assertTrue(score.getPoints(1) == Const.cheesePoints * 8 && 
                score.getPoints(2) == 0);
    }
    
    @Test
    public void decreaseLives()
    {
        score.loseALife(1);
        assertTrue(score.getLives(1) == Const.initialLifeAmount - 1 && 
                score.getLives(2) == Const.initialLifeAmount);
    }
    
    @Test
    public void updateDecreasesTimeBonus()
    {
        score.update();
        assertTrue(score.getBonus() == Const.initialBonus - Const.bonusDecreasingRate);
    }
    @Test
    public void timeBonusCantBeNegative()
    {
        for (int i = 0; i < (Const.initialBonus / Const.bonusDecreasingRate) + 100; i++)
            score.update();
        assertTrue(score.getBonus() == 0);
    }

    @Test
    public void timeBonusIsGiven()
    {
        score.update();
        score.pointsForFinishingStage(1);
        assertTrue(score.getPoints(1) == Const.initialBonus - Const.bonusDecreasingRate &&
            score.getPoints(2) == Const.initialBonus - Const.bonusDecreasingRate);
    }
    @Test
    public void ifTimeBonusIsGivenResetTimeBonus()
    {
        for (int i = 0; i < 100; i++)   score.update();
        score.pointsForFinishingStage(1);
        assertTrue(score.getBonus() == Const.initialBonus);
    }
    @Test
    public void amountOfAttendedPlayersIsCorrect()
    {
        assertTrue(score.allAttendedPlayers().size() == 2);
    }
    @Test
    public void amountOfAttendedPlayersIsCorrect2()
    {
        Set<Integer> plrs = new HashSet<>();
        plrs.add(1);
        plrs.add(1);
        score.resetScore(plrs);
        assertTrue(score.allAttendedPlayers().size() == 1);
    }
    @Test
    public void amountOfAttendedPlayersIsCorrect3()
    {
        Set<Integer> plrs = new HashSet<>();
        score.resetScore(plrs);
        assertTrue(score.allAttendedPlayers().isEmpty());
    }
}
