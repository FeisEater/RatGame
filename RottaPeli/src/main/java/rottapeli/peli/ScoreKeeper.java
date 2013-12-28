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
    private Map<Integer, Double> nextExtraLifeScore;
    private Map<Integer, Double> combo;
    private Map<Integer, Integer> lives;
    private double timebonus;
    public ScoreKeeper(RottaPeli peli)
    {
        rp = peli;
        score = new HashMap<Integer, Double>();
        nextExtraLifeScore = new HashMap<Integer, Double>();
        combo = new HashMap<Integer, Double>();
        lives = new HashMap<Integer, Integer>();
    }
    public double getPoints(int id) {return score.get(id);}
    public void pointsForEatingCheese(int id)
    {
        addScore(id, combo.get(id));
        combo.put(id, combo.get(id) * 2);
    }
    public void addScore(int id, double points)
    {
        score.put(id, score.get(id) + points);
        while (score.get(id) >= nextExtraLifeScore.get(id))
        {
            giveALife(id);
            nextExtraLifeScore.put(id, 
                    nextExtraLifeScore.get(id) + Const.extraLifeReward);
        }
    }
    public void giveALife(int id)   {lives.put(id, lives.get(id) + 1);}
    public void loseALife(int id)
    {
        lives.put(id, lives.get(id) - 1);
        if (lives.get(id) <= 0)
            rp.playerLostAllLives(id);
    }
    public void pointsForFinishingStage(int id)
    {
        for (int plr : score.keySet())
        {
            addScore(plr, timebonus);
            resetCombo(plr);
        }
        timebonus = Const.initialBonus;
    }
    public void resetCombo(int id)
    {
        combo.put(id, Const.cheesePoints);
    }
    public void resetScore(Set<Integer> players)
    {
        score.clear();
        nextExtraLifeScore.clear();
        combo.clear();
        lives.clear();
        for (int id : players)
        {
            score.put(id, 0.0);
            nextExtraLifeScore.put(id, Const.extraLifeReward);
            resetCombo(id);
            lives.put(id, Const.initialLifeAmount);
        }
        timebonus = Const.initialBonus;
    }
    
    @Override
    public void update()
    {
        timebonus -= Const.bonusDecreasingRate;
        if (timebonus < 0)  timebonus = 0;
        System.out.println("score: " + score.get(1) + " lives: " + lives.get(1) + " combo: " + score.get(1) + " timebonus: " + timebonus);
    }
}
