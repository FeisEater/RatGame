
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
    private Updatable entities;
    private Updatable field;
    private Updatable input;
    private boolean gameIsPaused;
    public GameTimer(Updatable el, Updatable gf, Updatable pi)
    {
        super(1000 / Const.fps, null);
        addActionListener(this);

        entities = el;
        field = gf;
        input = pi;
        
        gameIsPaused = false;
    }
    
    public void togglePause()
    {
        gameIsPaused = !gameIsPaused;
    }
@Override
    public void actionPerformed(ActionEvent ae)
    {
        if (entities != null && !gameIsPaused)
            entities.update();
        if (field != null && !gameIsPaused)
            field.update();
        if (input != null)   input.update();
    }

}
