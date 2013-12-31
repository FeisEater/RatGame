
package rottapeli.peli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private RottaPeli rp;
    private boolean gameIsPaused;
/**
 * Constructor.
 * @param peli Pointer to the game logic Object.
 */
    public GameTimer(RottaPeli peli)
    {
        super(1000 / Const.fps, null);
        addActionListener(this);

        rp = peli;
        gameIsPaused = false;
    }
/**
 * Toggles whether game should be paused or not.
 */
    public void togglePause()
    {
        gameIsPaused = !gameIsPaused;
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
        if (rp.getInput() != null)   rp.getInput().update();
        
        if (rp.getEntities() != null && !gameIsPaused)
            rp.getEntities().update();
        
        if (rp.getScore() != null && !gameIsPaused)
            rp.getScore().update();
        
        if (rp.getField() != null && !gameIsPaused)
            rp.getField().update();
    }

}
