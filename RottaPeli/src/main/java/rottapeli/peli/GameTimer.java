
package rottapeli.peli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Const;

/**
 * Timer object responsible for time-sensitive tasks in the game.
 * @author Pavel
 */
public class GameTimer extends Timer implements ActionListener {
/** True if game is paused. */
    private boolean gameIsPaused;
/** List of objects that get updated every tick on the timer. */
    private List<Updatable> updatables;
/** Game logic object. */
    private RottaPeli rp;
/**
 * Constructor.
 */
    public GameTimer(RottaPeli peli)
    {
        super(1000 / Const.fps, null);
        addActionListener(this);
        updatables = new ArrayList<>();
        rp = peli;

        gameIsPaused = false;
        
        start();
    }
/**
 * Adds an upadatable object that will be updated every tick on the timer.
 * @param u Updatable object to be updated.
 */
    public void addUpdatable(Updatable u)   {updatables.add(u);}
/**
 * Toggles whether game should be paused or not.
 */
    public void togglePause()
    {
        setPaused(!gameIsPaused);
    }
/**
 * Pauses or unpauses the game.
 * @param paused if true pauses game. otherwise unpauses.
 */
    public void setPaused(boolean paused)
    {
        if (rp != null && rp.isGameOver())
        {
            gameIsPaused = false;
            return;
        }
        gameIsPaused = paused;
        if (rp != null && rp.getMenu() != null)
            rp.getMenu().popMenu(gameIsPaused);
    }
/**
 * Method is called every tick on the timer.
 * <p>
 * Updates all components of the game that needs to be updated.
 * @param ae 
 */
@Override
    public void actionPerformed(ActionEvent ae)
    {
        for (Updatable u : updatables)
        {
            if (u == null)
                continue;
            //When game is paused only update PlayerInput
            if (gameIsPaused && u.getClass() != PlayerInput.class)
                continue;
            
            u.update();
        }
    }

}
