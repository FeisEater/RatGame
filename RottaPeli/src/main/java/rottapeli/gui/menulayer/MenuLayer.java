
package rottapeli.gui.menulayer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public class MenuLayer extends JPanel {
    private RottaPeli rp;
    private Menu currentMenu;
    public MenuLayer(RottaPeli peli)
    {
        super(new GridLayout(0,3, 0, 32));
        rp = peli;
        setOpaque(false);
    }
    public void emptyRow()
    {
        for (int i = 0; i < 3; i++) add(new JLabel(""));
    }
    public JButton createButton(String label)
    {
        add(new JLabel(""));
        JButton button = new JButton(label);
        button.addActionListener(currentMenu);
        button.setActionCommand(label);
        add(button);
        add(new JLabel(""));
        return button;
    }
    public JLabel createLabel(String text)
    {
        add(new JLabel(""));
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setBackground(Color.white);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);
        add(new JLabel(""));
        return label;
    }
    public void popMenu(boolean visible)
    {
        setVisible(visible);
        switchMenu(new MainMenu(rp));
    }
    public void switchMenu(Menu menu)
    {
        if (currentMenu != null)    currentMenu.hideMenu();
        currentMenu = menu;
        menu.showMenu();
        revalidate();
        repaint();
    }
}
