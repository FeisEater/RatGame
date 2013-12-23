
package rottapeli.peli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class GameTimer extends Timer implements ActionListener {
    private Updatable entities;
    private Updatable field;
    public GameTimer(Updatable el, Updatable gf)
    {
        super(1000 / Const.fps, null);
        addActionListener(this);
        //setInitialDelay(0);

        entities = el;
        field = gf;
    }
    
@Override
    public void actionPerformed(ActionEvent ae)
    {
        if (entities != null)   entities.update();
        if (field != null)   field.update();
    }

}
