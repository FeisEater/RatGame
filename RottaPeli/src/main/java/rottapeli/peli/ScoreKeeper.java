package rottapeli.peli;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class ScoreKeeper implements Updatable {
    private RottaPeli rp;
    private Map<Integer, Double> score;
    private Map<Integer, Double> combo;
    private Map<Integer, Integer> lives;
    private double timebonus;
    public ScoreKeeper(RottaPeli peli)
    {
        rp = peli;
        score = new HashMap<Integer, Double>();
        combo = new HashMap<Integer, Double>();
        lives = new HashMap<Integer, Integer>();
    }
    public void addScore(int id, double points)
    {
        score.put(id, score.get(id) + points);
    }
    public void loseALife(int id)
    {
        lives.put(id, lives.get(id) - 1);
        if (lives.get(id) <= 0)
            rp.playerLostAllLives(id);
    }
    public void resetScore(Set<Integer> players)
    {
        for (int id : players)
        {
            score.put(id, 0.0);
            combo.put(id, 0.0);
            lives.put(id, Const.initialLifeAmount);
        }
        timebonus = Const.initialBonus;
    }
    
    @Override
    public void update()
    {
        timebonus -= Const.bonusDecreasingRate;
        if (timebonus < 0)  timebonus = 0;
    }
}
