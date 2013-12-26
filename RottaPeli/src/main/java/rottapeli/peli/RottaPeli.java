package rottapeli.peli;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import rottapeli.domain.Ball;
import rottapeli.domain.Border;
import rottapeli.domain.Cheese;
import rottapeli.domain.PlacementBlocker;
import rottapeli.domain.Rat;
import rottapeli.domain.superclasses.Positioned;
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
    private JFrame frame;
    public RottaPeli()
    {        
        EntityList entities = new EntityList();
        
        GameField field = createGUI(entities);
        
        PlayerInput pi = new PlayerInput(entities);
        frame.addKeyListener(pi);
  
        GameTimer gt = new GameTimer(entities, field, pi);
        gt.start();
        pi.setTimer(gt);
        
        entities.addEntity(new Border(-32, -32, Const.width + 64, 32));
        entities.addEntity(new Border(-32, -32, 32, Const.height + 64));
        entities.addEntity(new Border(-32, Const.height, Const.width + 64, 32));
        entities.addEntity(new Border(Const.width, -32, 32, Const.height + 64));
        entities.addEntity(new PlacementBlocker(0, 0, Const.width, Const.placementBlockerThickness));
        entities.addEntity(new PlacementBlocker(0, 0, Const.placementBlockerThickness, Const.height));
        entities.addEntity(new PlacementBlocker(0, Const.height - Const.placementBlockerThickness, Const.width, Const.placementBlockerThickness));
        entities.addEntity(new PlacementBlocker(Const.width - Const.placementBlockerThickness, 0, Const.placementBlockerThickness, Const.height));
        
        Rat rat = new Rat(Math.round(Const.width / 2), 0);
        entities.addEntity(rat);
        
        for (int i = 0; i < Const.ballAmountInFirstLevel; i++)
            createAndPosition(new Ball(randomX(), randomY(), randomBallDirection()), entities);

        for (int i = 0; i < Const.cheeseammount; i++)
            createAndPosition(new Cheese(randomX(), randomY()), entities);
    
    }
    
    public void createAndPosition(Positioned p, EntityList entities)
    {
        entities.addEntity(p);
        p.findNearestFreeSpot();
    }
    public double randomX() {return Math.round(Math.random() * Const.width);}
    public double randomY() {return Math.round(Math.random() * Const.height);}
    public double randomBallDirection()
    {
        double randomNumber = Math.random();
        
        if (randomNumber < 0.25)    return Const.rightdown;
        if (randomNumber < 0.5)    return Const.leftdown;
        if (randomNumber < 0.75)    return Const.leftup;
        return Const.rightup;
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
        
        frame = gui.getFrame();
        field.setEntityList(el);
        return field;
    }
}
