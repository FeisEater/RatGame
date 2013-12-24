package rottapeli.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Updatable;
import rottapeli.peli.EntityList;
import rottapeli.peli.PlayerInput;
import rottapeli.resource.Const;

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
        
        double widthMultiplier = getWidth() / (double)Const.width;
        double heightMultiplier = getHeight() / (double)Const.height;
        List<Positioned> drawables = entities.getList(Positioned.class);
        for (Positioned p : drawables)
        {
            p.draw(g, widthMultiplier, heightMultiplier);
        }
    }
    
    @Override
    public void update()
    {
        this.repaint();
    }
}
