
package rottapeli.gui;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Pavel
 */
public class Menu extends JPanel {
    
    public Menu()
    {
        super(new GridLayout(6,3, 0, 64));
        setOpaque(false);
        
        emptyRow();
        createButton("New game");
        createButton("Options");
        createButton("Credits");
        createButton("Exit");
        emptyRow();
    }
    public void emptyRow()
    {
        for (int i = 0; i < 3; i++) add(new JLabel(""));
    }
    public void createButton(String label)
    {
        add(new JLabel(""));
        add(new JButton(label));
        add(new JLabel(""));
    }
}
