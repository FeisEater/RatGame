
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
    public GameTimer(Updatable el, Updatable gf, Updatable pi)
    {
        super(1000 / Const.fps, null);
        addActionListener(this);
        //setInitialDelay(0);

        entities = el;
        field = gf;
        input = pi;
    }
    
@Override
    public void actionPerformed(ActionEvent ae)
    {
        if (entities != null)   entities.update();
        if (field != null)   field.update();
        if (input != null)   input.update();
    }

}
