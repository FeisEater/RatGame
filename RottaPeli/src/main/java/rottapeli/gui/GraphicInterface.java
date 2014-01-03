package rottapeli.gui;

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
    private Menu menu;
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
        
        JPanel game = new JPanel(new BorderLayout());
        game.add(new GameField(rp));
        game.add(new ScoreBar(rp), BorderLayout.SOUTH);
        
        menu = new Menu(rp);
        add(menu);
        add(game);
        frame.add(this);
    }
/**
 * Runs the window with GUI.
 */
    @Override
    public void run()
    {
        frame = new JFrame("Rottapeli");
        frame.setPreferredSize(new Dimension(Const.width * 6 + 16, Const.height * 6 + 36));
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
    public Menu getMenu()
    {
        return menu;
    }
    
    @Override
    public boolean isOptimizedDrawingEnabled()
    {
        return false;
    }
}
