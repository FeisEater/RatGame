package rottapeli.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import rottapeli.interfaces.Updatable;

/**
 *
 * @author Pavel
 */
public class GameField extends JPanel implements Updatable {

    public GameField()
    {
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
    }
    
    @Override
    public void update()
    {
        this.repaint();
    }
}
