package rottapeli.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Updatable;
import rottapeli.peli.EntityList;

/**
 *
 * @author Pavel
 */
public class GameField extends JPanel implements Updatable {
    private EntityList entities;
    
    public GameField()
    {
    }
    public void setEntityList(EntityList el)
        {entities = el;}
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        List<Positioned> drawables = entities.getList(Positioned.class);
        for (Positioned p : drawables)
        {
            g.setColor(Color.RED);
            g.fill3DRect((int)p.X() * 2, (int)p.Y() * 2,
                    (int)p.getWidth() * 2, (int)p.getHeight() * 2, true);
        }
    }
    
    @Override
    public void update()
    {
        this.repaint();
    }
}
