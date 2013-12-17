package rottapeli.peli;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import rottapeli.domain.Updatable;
import rottapeli.resource.Const;
/**
 *
 * @author Pavel
 */
public class RottaPeli extends Timer implements ActionListener {
    private EntityList entities;
    public RottaPeli()
    {
        super(1000 / Const.fps, null);
        addActionListener(this);
        //setInitialDelay(0);
        
        entities = new EntityList();
    }
        
@Override
    public void actionPerformed(ActionEvent ae)
    {
        for (Object o : entities.getList(Updatable.class))
        {
            Updatable m = (Updatable)o;
            m.update();
        }
    }
}
