package rottapeli.gui;

import rottapeli.gui.menulayer.MenuLayer;
import rottapeli.gui.gamelayer.ScoreBar;
import rottapeli.gui.gamelayer.GameField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager;
import java.awt.PopupMenu;
import java.util.ArrayDeque;
import java.util.Deque;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;
import rottapeli.gui.gamelayer.GameLayer;
import rottapeli.interfaces.Updatable;
import rottapeli.peli.PlayerInput;
import rottapeli.peli.RottaPeli;
import rottapeli.resource.Const;

/**
 * Creates a window that contains the graphical interface of the game.
 * @author Pavel
 */
public class GraphicInterface extends JPanel implements Runnable {
    private JFrame frame;
    private boolean componentsCreated;
    private RottaPeli rp;
    public GraphicInterface(RottaPeli peli)
    {
        rp = peli;
        componentsCreated = false;
    }
/**
 * Creates components of the GUI.
 */
    public void createComponents()
    {
        LayoutManager overlay = new OverlayLayout(this);
        setLayout(overlay);
                
        MenuLayer menu = new MenuLayer(rp);
        rp.setMenu(menu);

        add(menu);
        add(new GameLayer(rp));
        
        frame.add(this);
    }
/**
 * Runs the window with GUI.
 */
    @Override
    public void run()
    {
        frame = new JFrame("Rottapeli");
        rp.getSettings().setFrame(frame);
        frame.setPreferredSize(new Dimension(rp.getSettings().getSavedWidth(),
                rp.getSettings().getSavedHeight()));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createComponents();
        frame.pack();
        frame.setVisible(true);
        componentsCreated = true;
    }
    
    public boolean componentsCreated()
    {
        return componentsCreated;
    }
    
    @Override
    public boolean isOptimizedDrawingEnabled()
    {
        return false;
    }
}
