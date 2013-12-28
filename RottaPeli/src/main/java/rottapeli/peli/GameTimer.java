
package rottapeli.peli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;
import rottapeli.domain.Rat;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class GameTimer extends Timer implements ActionListener {
    private RottaPeli rp;
    private boolean gameIsPaused;
    public GameTimer(RottaPeli peli)
    {
        super(1000 / Const.fps, null);
        addActionListener(this);

        rp = peli;
        gameIsPaused = false;
    }
    
    public void togglePause()
    {
        gameIsPaused = !gameIsPaused;
    }
@Override
    public void actionPerformed(ActionEvent ae)
    {
        if (rp.getEntities() != null && !gameIsPaused)
            rp.getEntities().update();
        if (rp.getField() != null && !gameIsPaused)
            rp.getField().update();
        if (rp.getScore() != null && !gameIsPaused)
            rp.getScore().update();
        if (rp.getInput() != null)   rp.getInput().update();
    }

}
