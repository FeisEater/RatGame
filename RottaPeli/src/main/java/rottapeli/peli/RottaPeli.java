package rottapeli.peli;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import rottapeli.domain.Ball;
import rottapeli.domain.Border;
import rottapeli.domain.Cheese;
import rottapeli.domain.PlacementBlocker;
import rottapeli.domain.Rat;
import rottapeli.domain.superclasses.Entity;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.gui.GameField;
import rottapeli.gui.GraphicInterface;
import rottapeli.gui.Menu;
import rottapeli.interfaces.Controllable;
import rottapeli.interfaces.Eatable;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Const;
import rottapeli.peli.GameTimer;
/**
 * Central hub of the program that stores all components of the game.
 * <p>
 * Provides global game logic decisions.
 * @author Pavel
 */
public class RottaPeli {
    private EntityList entities;
    private Menu menu;
    private GameTimer timer;
    private PlayerInput input;
    private ScoreKeeper score;
/**
 * Constructor.
 * <p>
 * Creates all components needed for the game.
 * @param createsGUI Set to true to create GUI. Can be set to false
 *          to improve testing environment.
 */
    public RottaPeli(boolean createsGUI)
    {        
        input = new PlayerInput(this);
        entities = new EntityList(this);
        score = new ScoreKeeper(this);
        
        timer = new GameTimer(this);
        timer.addUpdatable(input);
        timer.addUpdatable(entities);
        timer.addUpdatable(score);

        if (createsGUI)   createGUI();

        resetGame();
        entities.removeAll(Rat.class);
    }
    public EntityList getEntities() {return entities;}
    public Menu getMenu()     {return menu;}
    public GameTimer getTimer()     {return timer;}
    public PlayerInput getInput()   {return input;}
    public ScoreKeeper getScore()   {return score;}
/**
 * Creates GUI window
 */
    public void createGUI()
    {
        GraphicInterface gui = new GraphicInterface(this);
        SwingUtilities.invokeLater(gui);
        
        while (!gui.componentsCreated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("Piirtoalustaa ei ole vielä luotu.");
            }
        }
        
        menu = gui.getMenu();
    }
/**
 * Creates Bouncable Borders around the playing field and a layer of
 * PlacementBlockers.
 * <p>
 * PlacementBlockers are placed to prevent situations where Ball kills Rat
 * immediately in the beginning of the game. Also prevents placing cheese
 * to easy spots.
 */
    public void createBorders()
    {
        entities.addEntity(new Border(-32, -32, Const.width + 64, 32));
        entities.addEntity(new Border(-32, -32, 32, Const.height + 64));
        entities.addEntity(new Border(-32, Const.height, Const.width + 64, 32));
        entities.addEntity(new Border(Const.width, -32, 32, Const.height + 64));
        
        entities.addEntity(new PlacementBlocker(0, 0, Const.width, Const.placementBlockerThickness));
        entities.addEntity(new PlacementBlocker(0, 0, Const.placementBlockerThickness, Const.height));
        entities.addEntity(new PlacementBlocker(0, Const.height - Const.placementBlockerThickness, Const.width, Const.placementBlockerThickness));
        entities.addEntity(new PlacementBlocker(Const.width - Const.placementBlockerThickness, 0, Const.placementBlockerThickness, Const.height));
    }
/**
 * Creates a Positioned Entity and places it to the free spot.
 * @param p Entity that is to be placed. Parameter is usually given
 *          as a constructor without parameters in order to place it to the
 *          defaultPosition().
 */
    public void createAndPositionToFreeSpot(Positioned p)
    {
        entities.addEntity(p);
        p.findNearestFreeSpot();
    }
/**
 * Signals Controllable Entity to move to a certain direction.
 * @param id    Player ID that are supposed to receive this signal.
 * @param dir   Direction towards which Entities are supposed to go.
 */
    public void playerGo(int id, double dir)
    {
        List<Controllable> players = entities.getList(Controllable.class);
        for (Controllable plr : players)
        {
            if (plr.playerID() == id)   plr.moveTo(dir);
        }
    }
/**
 * Resets all Entities of a certain type to their defaultPosition().
 * @param type  Class or Interface of the Entity that need to be reset.
 *              Use null or Entity.class to reset all Entities.
 * @param spotMustBeFree Set to true if Entity requires to be placed
 *                          at an unoccupied location.
 */
    public void resetPosition(Class type, boolean spotMustBeFree)
    {
        List<Positioned> resettables = entities.getList(type);
        for (Positioned p : resettables)
        {
            p.defaultPosition();
            if (spotMustBeFree)
                p.findNearestFreeSpot();
        }
    }
/**
 * Reacts to the event of certain player dying.
 * <p>
 * Player loses a life. Resets Balls if amount of players is one.
 * @param id ID of the player that died.
 */
    public void playerDied(int id)
    {
        score.loseALife(id);
        if (getPlayers().size() == 1)
            resetPosition(Ball.class, true);
    }
/**
 * Reacts to the event of certain player losing all of her/his lives.
 * <p>
 * Removes all Entities that are controlled by the player.
 * @param id ID of the player that lost all lives.
 */
    public void playerLostAllLives(int id)
    {
        List<Controllable> players = entities.getList(Controllable.class);
        for (Controllable plr : players)
        {
            if (plr.playerID() == id)
                entities.removeEntity((Entity)plr);
        }
    }
/**
 * Reacts to the event of certain player eating cheese.
 * <p>
 * Gives points. If no cheese left go to the next stage and give a time bonus.
 * @param id ID of the player that ate cheese.
 */
    public void playerAteCheese(int id)
    {
        score.pointsForEatingCheese(id);
        if (entities.getList(Cheese.class).isEmpty())
        {
            nextStage();
            score.pointsForFinishingStage(id);
        }
    }
/**
 * Goes to the next stage.
 * <p>
 * Resets Rat's and Balls', resets Cheese and increases Ball amount by one.
 */
    public void nextStage()
    {
        resetPosition(Rat.class, false);
        resetPosition(Ball.class, true);
        
        entities.removeAll(Eatable.class);
        for (int i = 0; i < Const.cheeseamount; i++)
            createAndPositionToFreeSpot(new Cheese());
        
        createAndPositionToFreeSpot(new Ball());
    }
/**
 * Examines how many different players are playing the game.
 * @return A Set of player IDs that exist in the game. Use getPlayers().size()
 *          to calculate the amount in integer.
 */
    public Set<Integer> getPlayers()
    {
        Set<Integer> players = new HashSet<Integer>();
        List<Controllable> controllables = entities.getList(Controllable.class);
        for (Controllable c : controllables)
        {
            players.add(c.playerID());
        }
        return players;
    }
/**
 * Resets all Entities, resets Score.
 */
    public void resetGame()
    {
        entities.removeAll(Entity.class);
        
        createBorders();
        
        entities.addEntity(new Rat());
        
        for (int i = 0; i < Const.ballAmountInFirstLevel; i++)
            createAndPositionToFreeSpot(new Ball());

        for (int i = 0; i < Const.cheeseamount; i++)
            createAndPositionToFreeSpot(new Cheese());

        score.resetScore(getPlayers());
    }
}
