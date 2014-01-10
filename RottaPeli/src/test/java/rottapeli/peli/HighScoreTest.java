/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rottapeli.peli;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pavel
 */
public class HighScoreTest {
    private HighScore high;
    public HighScoreTest() {
    }
        
    @Before
    public void setUp() {
        high = new HighScore();
        high.clearHighScore();
    }
    
    @Test
    public void scoreIsRetrievedFromFileCorrectly()
    {
        high.retrieveScore("100 pavel");
        assertTrue(high.getNameByRank(0).equals("pavel") && 
                high.getScoreByRank(0) == 100);
    }
    @Test
    public void canRetrieveScoreWithWhitespaces()
    {
        high.retrieveScore("100 here are some w h i t e s pa c e s");
        assertTrue(high.getNameByRank(0).equals("here are some w h i t e s pa c e s") && 
                high.getScoreByRank(0) == 100);
    }
    @Test
    public void retrieveScoreWithoutName()
    {
        high.retrieveScore("100");
        assertTrue(high.getNameByRank(0).isEmpty() && 
                high.getScoreByRank(0) == 100);
    }
    @Test
    public void retrieveScoreWithoutName2()
    {
        high.retrieveScore("100 ");
        assertTrue(high.getNameByRank(0).isEmpty() && 
                high.getScoreByRank(0) == 100);
    }
    @Test
    public void emptyScoreIfStringIsEmpty()
    {
        high.retrieveScore("");
        assertTrue(high.getNameByRank(0).isEmpty() &&
                high.getScoreByRank(0) == 0);
    }
    @Test
    public void dontRetrieveScoreIfFirstPartIsntInteger()
    {
        high.retrieveScore("pavel 100");
        assertTrue(high.getNameByRank(0).equals("100") &&
                high.getScoreByRank(0) == 0);
    }
    @Test
    public void dontRetrieveScoreIfFirstPartIsntInteger2()
    {
        high.retrieveScore("pavel100");
        assertTrue(high.getNameByRank(0).equals("") &&
                high.getScoreByRank(0) == 0);
    }
    @Test
    public void dontRetrieveScoreIfFirstPartIsntInteger3()
    {
        high.retrieveScore("pavel");
        assertTrue(high.getNameByRank(0).equals("") &&
                high.getScoreByRank(0) == 0);
    }
    @Test
    public void ifFirstPartEmptyPutZeroAsScore()
    {
        high.retrieveScore(" pavel");
        assertTrue(high.getNameByRank(0).equals("pavel") &&
                high.getScoreByRank(0) == 0);
    }
    
    @Test
    public void insertsScore()
    {
        high.insertScore("pavel", 100, false);
        assertTrue(high.getNameByRank(0).equals("pavel") && 
                high.getScoreByRank(0) == 100);
    }
    @Test
    public void insertsScoreWithEmptyName()
    {
        high.insertScore("", 100, false);
        assertTrue(high.getNameByRank(0).isEmpty() && 
                high.getScoreByRank(0) == 100);
    }
    @Test
    public void insertingScoresMaintainOrder()
    {
        Random r = new Random();
        for (int i = 0; i < 100; i++)
            high.insertScore("randomScore", r.nextInt(1000), false);
        int prev = Integer.MAX_VALUE;
        boolean isinorder = true;
        for (int i = 0; i < 100; i++)
        {
            if (prev < high.getScoreByRank(i))  isinorder = false;
        }
        assertTrue(isinorder);
    }

    public void setupHighScoreTest()
    {
        high.insertScore("matti", 1000, false);
        high.insertScore("teppo", 500, false);
        high.insertScore("jonne", 10, false);
    }
    @Test
    public void nameAndScoreGettersAreOK()
    {
        setupHighScoreTest();
        assertTrue(high.getNameByRank(0).equals("matti") &&
                high.getScoreByRank(0) == 1000 &&
                high.getNameByRank(1).equals("teppo") &&
                high.getScoreByRank(1) == 500 &&
                high.getNameByRank(2).equals("jonne") &&
                high.getScoreByRank(2) == 10);
    }
    @Test
    public void emptyResultsIfGettersOutOfBounds()
    {
        setupHighScoreTest();
        assertTrue(high.getNameByRank(-1).equals("") &&
                high.getScoreByRank(-1) == 0 &&
                high.getNameByRank(3).equals("") &&
                high.getScoreByRank(3) == 0);
    }
    @Test
    public void correctRankForScore()
    {
        setupHighScoreTest();
        assertTrue(high.getRankByScore(1100) == 0 &&
                high.getRankByScore(750) == 1 &&
                high.getRankByScore(250) == 2 &&
                high.getRankByScore(0) == 3);
    }
    @Test
    public void ifScoreIsSameGiveLowerRank()
    {
        setupHighScoreTest();
        assertTrue(high.getRankByScore(1000) == 1 &&
                high.getRankByScore(500) == 2 &&
                high.getRankByScore(10) == 3);
    }
    @Test
    public void ifScoreIsSameGiveLowerRank2()
    {
        for (int i = 0; i < 100; i++)
            high.insertScore("sameScore", 2000, false);
        high.insertScore("pavel", 2000, false);
        assertTrue(high.getNameByRank(100).equals("pavel"));
    }
    
    @Test
    public void clearsHighScore()
    {
        setupHighScoreTest();
        high.clearHighScore();
        assertTrue(high.isInTop(1, 0));
    }
    
    @Test
    public void isInTopWorksCorrectly1()
    {
        setupHighScoreTest();
        assertTrue(high.isInTop(1, 1100) && !high.isInTop(1, 900));
    }
    @Test
    public void isInTopWorksCorrectly2()
    {
        setupHighScoreTest();
        assertTrue(high.isInTop(2, 1100) && high.isInTop(2, 900) &&
                !high.isInTop(2, 400));
    }
    @Test
    public void isInTopWorksCorrectly3()
    {
        setupHighScoreTest();
        assertTrue(high.isInTop(3, 1100) && high.isInTop(3, 900) &&
                high.isInTop(3, 400) && !high.isInTop(3, 1));
    }
    @Test
    public void isInTopWorksCorrectly4()
    {
        setupHighScoreTest();
        assertTrue(high.isInTop(4, -100));
    }
    @Test
    public void isInTopWorksCorrectlyWithExactScores()
    {
        setupHighScoreTest();
        assertTrue(!high.isInTop(1, 1000) && high.isInTop(2, 1000) &&
                !high.isInTop(2, 500) && high.isInTop(3, 500) &&
                !high.isInTop(3, 10) && high.isInTop(4, 10));
    }
}
