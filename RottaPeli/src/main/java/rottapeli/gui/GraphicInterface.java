package rottapeli.gui;

import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;
import rottapeli.gui.gamelayer.GameLayer;
import rottapeli.gui.menulayer.MenuLayer;
import rottapeli.peli.RottaPeli;

/**
 * Creates a window that contains the graphical interface of the game.
 * @author Pavel
 */
public class GraphicInterface extends JPanel implements Runnable {
/** JFrame of the program. */
    private JFrame frame;
/** true if gui is created and is ready to be used. */
    private boolean componentsCreated;
/** Pointer to the game logic object. */
    private RottaPeli rp;
/**
 * Constructor.
 * @param peli Pointer to the game logic object.
 */
    public GraphicInterface(RottaPeli peli)
    {
        rp = peli;
        componentsCreated = false;
    }
/**
 * Creates components of the GUI, game layer and menu layer.
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
        frame = new JFrame(rp.getLanguage().translate("#title"));
        rp.setFrame(frame);
        frame.setPreferredSize(new Dimension(rp.getSettings().getSavedWidth(),
                rp.getSettings().getSavedHeight()));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createComponents();
        frame.pack();
        frame.setVisible(true);
        componentsCreated = true;
    }
/**
 * 
 * @return true if gui is created and is ready to be used.
 */
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
