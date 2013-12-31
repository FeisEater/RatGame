package rottapeli.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.List;
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
    private JLabel scoreText;
    public GameField(Container container)
    {
        super(new GridLayout(6,3));
        for (int i = 0; i < 17; i++)    add(new JLabel(""));
        scoreText = new JLabel("Score: ");
        add(scoreText);
    }
/**
 * Stores a pointer to the game logic Object.
 * @param peli Game logic Object.
 */
    public void setRottaPeli(RottaPeli peli)
    {
        rp = peli;
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
        scoreText.setText("Score: " + (int)rp.getScore().getPoints(1));
    }
}
