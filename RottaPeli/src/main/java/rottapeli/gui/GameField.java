package rottapeli.gui;

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
/**
 * Paints all Entities and scales them to the size of the window.
 * @param g Graphics data Object.
 */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (rp == null && rp.getEntities() == null)
            return;

        double widthMultiplier = getWidth() / (double)Const.width;
        double heightMultiplier = getHeight() / (double)Const.height;
        //double multiplier = Math.min(widthMultiplier, heightMultiplier);
        
        List<Positioned> drawables = rp.getEntities().getList(Positioned.class);
        for (Positioned p : drawables)
        {
            p.draw(g, widthMultiplier, heightMultiplier);
        }
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
