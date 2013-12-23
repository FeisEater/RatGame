package rottapeli.peli;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import rottapeli.gui.GameField;
import rottapeli.gui.GraphicInterface;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Const;
/**
 *
 * @author Pavel
 */
public class RottaPeli extends Timer implements ActionListener {
    private EntityList entities;
    private Updatable field;
    public RottaPeli()
    {
        super(1000 / Const.fps, null);
        addActionListener(this);
        //setInitialDelay(0);
        
        entities = new EntityList();
        
        GraphicInterface gui = new GraphicInterface();
        SwingUtilities.invokeLater(gui);
        field = gui.getField();
        
        while (field == null) {
            try {
                Thread.sleep(100);
                field = gui.getField();
                System.out.println("tried it");
            } catch (InterruptedException ex) {
                System.out.println("Piirtoalustaa ei ole vielä luotu.");
            }
        }
        
        start();
    }

    public EntityList getEntityList() {return entities;}
    
@Override
    public void actionPerformed(ActionEvent ae)
    {
        entities.update();
        field.update();
    }
}
