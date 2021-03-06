package rottapeli.peli;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Const;

/**
 * Keeps score of every player in the game. Also stores other stats of the game,
 * including life amounts, combos, time bonus, stage number.
 * @author Pavel
 */
public class ScoreKeeper implements Updatable {
/** Game logic object. */
    private RottaPeli rp;
/** Scores by player ID. */
    private Map<Integer, Double> score;
/** Next score reaching which will award an extra life by player ID. */
    private Map<Integer, Double> nextExtraLifeScore;
/** Combo by player ID. */
    private Map<Integer, Double> combo;
/** Lives left by player ID. */
    private Map<Integer, Integer> lives;
/** Current time bonus counter value. */
    private double timebonus;
/**
 * Constructor.
 * @param peli Pointer to the game logic Object.
 */
    public ScoreKeeper(RottaPeli peli)
    {
        rp = peli;
        score = new HashMap<>();
        nextExtraLifeScore = new HashMap<>();
        combo = new HashMap<>();
        lives = new HashMap<>();
    }
/**
 * 
 * @param id Player ID.
 * @return Certain player's score.
 */
    public double getPoints(int id)
    {
        if (!score.containsKey(id)) return -1;
        return score.get(id);
    }
/**
 * 
 * @param id Player ID.
 * @return Certain player's life amount.
 */
    public double getLives(int id)
    {
        if (!lives.containsKey(id)) return -1;
        return lives.get(id);
    }
/**
 * 
 * @param id Player ID.
 * @return Certain player's combo.
 */
    public double getCombo(int id)
    {
        if (!combo.containsKey(id)) return -1;
        return combo.get(id);
    }
/**
 * 
 * @return Current time bonus left.
 */
    public double getBonus() {return timebonus;}
/**
 * Calculates and adds score for eating cheese. Increases combo, so more
 * points are awarded for consequtive cheese eating.
 * @param id Which player ate cheese.
 */
    public void pointsForEatingCheese(int id)
    {
        addScore(id, combo.get(id));
        combo.put(id, combo.get(id) * 2);
    }
/**
 * Adds score. When certain amount is reached, awards an extra life.
 * @param id Player that receives points.
 * @param points Amount of points that is to be added.
 */
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
/**
 * Give one life to a certain player.
 * @param id Player ID.
 */
    public void giveALife(int id)   {lives.put(id, lives.get(id) + 1);}
/**
 * Decereases amount of lives by one. If no lives left, signal game logic
 * Object about it.
 * @param id Player that lost a life.
 */
    public void loseALife(int id)
    {
        lives.put(id, lives.get(id) - 1);
        if (lives.get(id) <= 0)
            rp.playerLostAllLives(id);
    }
/**
 * Awards all players a time bonus for finishing a stage. Resets time bonus counter.
 * @param id Player that triggered this event.
 */
    public void pointsForFinishingStage(int id)
    {
        for (int plr : score.keySet())
        {
            addScore(plr, timebonus);
            resetCombo(plr);
        }
        timebonus = Const.initialBonus;
    }
/**
 * Sets combo counter to zero.
 * @param id Player that has his/her combo reset.
 */
    public void resetCombo(int id)
    {
        combo.put(id, Const.cheesePoints);
    }
/**
 * 
 * @return All player ID's that have participated in the game.
 */
    public Set<Integer> allAttendedPlayers()
    {
        return score.keySet();
    }
/**
 * Resets all score data.
 * @param players Set of player IDs that will play the game.
 */
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
/**
 * This method is called every tick on the timer.
 * <p>
 * ScoreKeeper decreases its time bonus counter.
 */
    @Override
    public void update()
    {
//Don't update scorebar if game is over.
        if (rp.isGameOver())    return;
        
        timebonus -= Const.bonusDecreasingRate;
        if (timebonus < 0)  timebonus = 0;
    }
}
