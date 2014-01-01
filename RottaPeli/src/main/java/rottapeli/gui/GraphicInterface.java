package rottapeli.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import rottapeli.interfaces.Updatable;
import rottapeli.peli.PlayerInput;
import rottapeli.resource.Const;

/**
 * Creates a window that contains the graphical interface of the game.
 * @author Pavel
 */
public class GraphicInterface implements Runnable {
    private JFrame frame;
    private GameField field;
/**
 * Creates components of the GUI.
 */
    public void createComponents()
    {
        field = new GameField(frame);
    }
/**
 * Runs the window with GUI.
 */
    @Override
    public void run()
    {
        frame = new JFrame("Rottapeli");
        frame.setPreferredSize(new Dimension(Const.width * 2 + 16, Const.height * 2 + 36));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createComponents();
        frame.pack();
        frame.setVisible(true);
    }
    
    public GameField getField()
    {
        return field;
    }
/**
 * Stores pointer for the player input.
 * @param pi Player input object.
 */
    public void setPlayerInput(PlayerInput pi)
    {
        frame.addKeyListener(pi);
    }
}
