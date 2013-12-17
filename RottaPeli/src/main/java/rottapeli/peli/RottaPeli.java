package rottapeli.peli;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import rottapeli.domain.Entity;
import rottapeli.resource.Const;
/**
 *
 * @author Pavel
 */
public class RottaPeli extends Timer implements ActionListener {
    private List<Entity> entities;
    public RottaPeli()
    {
        super(1000 / Const.fps, null);
        addActionListener(this);
        setInitialDelay(0);
        entities = new ArrayList<Entity>();
    }
    
    public void addEntity(Entity e)
    {
        entities.add(e);
    }
    
@Override
    public void actionPerformed(ActionEvent ae)
    {
        for (Entity e : entities)
        {
            e.update();
        }
    }
}
