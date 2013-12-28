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
import rottapeli.interfaces.Controllable;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Const;
import rottapeli.peli.GameTimer;
/**
 *
 * @author Pavel
 */
public class RottaPeli {
    private EntityList entities;
    private GameField field;
    private GameTimer timer;
    private PlayerInput input;
    private ScoreKeeper score;
    public RottaPeli(boolean createsGUI)
    {        
        input = new PlayerInput(this);
        if (createsGUI)   field = createGUI(input);
        entities = new EntityList(this);
        score = new ScoreKeeper(this);
        timer = new GameTimer(this);
        timer.start();
        resetGame();
    }
    public EntityList getEntities() {return entities;}
    public GameField getField()     {return field;}
    public GameTimer getTimer()     {return timer;}
    public PlayerInput getInput()   {return input;}
    public ScoreKeeper getScore()   {return score;}
    
    public GameField createGUI(PlayerInput pi)
    {
        GraphicInterface gui = new GraphicInterface();
        SwingUtilities.invokeLater(gui);
        GameField field = gui.getField();
        
        while (field == null) {
            try {
                Thread.sleep(100);
                field = gui.getField();
            } catch (InterruptedException ex) {
                System.out.println("Piirtoalustaa ei ole vielä luotu.");
            }
        }
        
        field.setRottaPeli(this);
        gui.setPlayerInput(pi);
        return field;
    }
    
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
    public void createAndPositionToFreeSpot(Positioned p)
    {
        entities.addEntity(p);
        p.findNearestFreeSpot();
    }
    
    public void playerGo(int id, double dir)
    {
        List<Controllable> players = entities.getList(Controllable.class);
        for (Controllable plr : players)
        {
            if (plr.playerID() == id)   plr.moveTo(dir);
        }
    }

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
    
    public void playerDied(int id)
    {
        score.loseALife(id);
        if (getPlayers().size() == 1)
            resetPosition(Ball.class, true);
    }
    
    public void playerLostAllLives(int id)
    {
        List<Controllable> players = entities.getList(Controllable.class);
        for (Controllable plr : players)
        {
            if (plr.playerID() == id)
                entities.removeEntity((Entity)plr);
        }
    }
    
    public void playerAteCheese(int id)
    {
        if (entities.getList(Cheese.class).isEmpty())
        {
            resetPosition(Rat.class, false);
            resetPosition(Ball.class, true);
            for (int i = 0; i < Const.cheeseammount; i++)
                createAndPositionToFreeSpot(new Cheese());
            createAndPositionToFreeSpot(new Ball());
        }
    }
    
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
    
    public void resetGame()
    {
        entities.removeAll(Entity.class);
        
        createBorders();
        
        entities.addEntity(new Rat());
        
        for (int i = 0; i < Const.ballAmountInFirstLevel; i++)
            createAndPositionToFreeSpot(new Ball());

        for (int i = 0; i < Const.cheeseammount; i++)
            createAndPositionToFreeSpot(new Cheese());

        score.resetScore(getPlayers());
    }
}
