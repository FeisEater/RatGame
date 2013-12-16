package rottapeli.peli;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import rottapeli.resource.Const;
/**
 *
 * @author Pavel
 */
public class RottaPeli extends Timer implements ActionListener {

    public RottaPeli()
    {
        super(1000 / Const.fps, null);
        addActionListener(this);
        setInitialDelay(0);
    }
@Override
    public void actionPerformed(ActionEvent ae)
    {
    }
}
