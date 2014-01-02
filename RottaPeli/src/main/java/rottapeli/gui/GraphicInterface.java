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
        JPanel panel = new JPanel() {
          public boolean isOptimizedDrawingEnabled() {
            return false;
          }
        };
        LayoutManager overlay = new OverlayLayout(panel);
        panel.setLayout(overlay);
        field = new GameField();
        JPanel buttons = new JPanel(new GridLayout(3,0)) {
          public boolean isOptimizedDrawingEnabled() {
            return false;
          }
        };
        JLabel empty = new JLabel("");
        buttons.add(empty);
        buttons.add(new JButton("nappi"));
        buttons.setOpaque(false);
        //panel.add(buttons);
        panel.add(field);
        frame.add(panel);
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
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(pi);
    }
}
