package rottapeli.peli;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import rottapeli.domain.Ball;
import rottapeli.domain.Border;
import rottapeli.gui.GameField;
import rottapeli.gui.GraphicInterface;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Const;
import rottapeli.peli.GameTimer;
/**
 *
 * @author Pavel
 */
public class RottaPeli {

    public RottaPeli()
    {        
        EntityList entities = new EntityList();
        
        GameField field = createGUI(entities);
        
        GameTimer gt = new GameTimer(entities, field);
        gt.start();
        
        entities.addEntity(new Ball(5,5,Const.rightdown));
        entities.addEntity(new Border(-32, -32, Const.width + 64, 32));
        entities.addEntity(new Border(-32, -32, 32, Const.height + 64));
        entities.addEntity(new Border(-32, Const.height, Const.width + 64, 32));
        entities.addEntity(new Border(Const.width, -32, 32, Const.height + 64));
    }
    
    public GameField createGUI(EntityList el)
    {
        GraphicInterface gui = new GraphicInterface();
        SwingUtilities.invokeLater(gui);
        GameField field = gui.getField();
        
        while (field == null) {
            try {
                Thread.sleep(100);
                field = gui.getField();
            } catch (InterruptedException ex) {
                System.out.println("Piirtoalustaa ei ole vielä luotu.");
            }
        }
        
        field.setEntityList(el);
        return field;
    }
}
