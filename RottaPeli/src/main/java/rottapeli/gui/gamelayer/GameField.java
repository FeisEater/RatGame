package rottapeli.gui.gamelayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Updatable;
import rottapeli.peli.EntityList;
import rottapeli.peli.PlayerInput;
import rottapeli.peli.RottaPeli;
import rottapeli.resource.Const;

/**
 * Represents all Entities of the game.
 * @author Pavel
 */
public class GameField extends JPanel implements Updatable {
/** Game logic Object. */
    private RottaPeli rp;
/**
 * Constructor.
 * @param peli Game logic Object.
 */
    public GameField(RottaPeli peli)
    {
        rp = peli;
        rp.getTimer().addUpdatable(this);
    }
    private void drawBackground(Graphics g, double offsetX, double offsetY, double widthMultiplier, double heightMultiplier)
    {
        g.setColor(Color.white);
        g.fillRect((int)offsetX, (int)offsetY, 
                (int)(widthMultiplier * Const.width), (int)(heightMultiplier * Const.height));
    }
    private double offsetXforAspectRatio(double widthMultiplier, double heightMultiplier)
    {
        if (heightMultiplier < widthMultiplier)
            return Const.width * ((widthMultiplier - heightMultiplier) / 2);
        return 0;
    }
    private double offsetYforAspectRatio(double widthMultiplier, double heightMultiplier)
    {
        if (widthMultiplier < heightMultiplier)
            return Const.height * ((heightMultiplier - widthMultiplier) / 2);
        return 0;
    }
/**
 * Paints all Entities and scales them to the size of the window. Looks up
 * whether aspect ratio should be respected from the current settings.
 * @param g Graphics data Object.
 */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (rp == null && rp.getEntities() == null)
            return;

        double offsetX = 0;
        double offsetY = 0;
        double widthMultiplier = getWidth() / (double)Const.width;
        double heightMultiplier = getHeight() / (double)Const.height;
        
        if (rp.getSettings().hasAspectRatio())
        {
            offsetX = offsetXforAspectRatio(widthMultiplier, heightMultiplier);
            offsetY = offsetYforAspectRatio(widthMultiplier, heightMultiplier);

            widthMultiplier = Math.min(widthMultiplier, heightMultiplier);
            heightMultiplier = Math.min(widthMultiplier, heightMultiplier);
        }
        
        drawBackground(g, offsetX, offsetY, widthMultiplier, heightMultiplier);
        
        List<Positioned> drawables = rp.getEntities().getList(Positioned.class);
        for (Positioned p : drawables)
            p.draw(g, offsetX, offsetY, widthMultiplier, heightMultiplier);
    }
/**
 * This method is called every tick on the timer.
 */
    @Override
    public void update()
    {
        this.repaint();
    }
}
