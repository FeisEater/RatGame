
package rottapeli.peli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import rottapeli.domain.Rat;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Const;

/**
 * Timer object responsible for time-sensitive tasks in the game.
 * @author Pavel
 */
public class GameTimer extends Timer implements ActionListener {
    private boolean gameIsPaused;
    private List<Updatable> updatables;
    private RottaPeli rp;
/**
 * Constructor.
 */
    public GameTimer(RottaPeli peli)
    {
        super(1000 / Const.fps, null);
        addActionListener(this);
        updatables = new ArrayList<Updatable>();
        rp = peli;

        gameIsPaused = false;
        
        start();
    }
    public void addUpdatable(Updatable u)   {updatables.add(u);}
/**
 * Toggles whether game should be paused or not.
 */
    public void togglePause()
    {
        gameIsPaused = !gameIsPaused;
        rp.getMenu().setVisible(gameIsPaused);
    }
    public void setPaused(boolean paused)
    {
        gameIsPaused = paused;
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
            if (u != null)
            {
                if (!gameIsPaused || u.getClass() == PlayerInput.class)
                    u.update();
            }
        }
    }

}
