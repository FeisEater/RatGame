
package rottapeli.gui.menulayer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
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
        super(new GridLayout(0,3, 0, 16));
        rp = peli;
        setOpaque(false);
    }
    public void emptyRow()
    {
        for (int i = 0; i < 3; i++) add(new JLabel(""));
    }
    public JButton createButton(String label)
    {
        JButton button = new JButton(label);
        createActionComponent(button);
        return button;
    }
    public JCheckBox createCheckBox(String label)
    {
        JCheckBox box = new JCheckBox(label);
        createActionComponent(box);
        return box;
    }
    public JLabel createLabel(String text)
    {
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setText(rp.getLanguage().translate(label.getText()));
        createComponent(label);
        return label;
    }
    public void createComponent(JComponent component)
    {
        add(new JLabel(""));
        add(component);
        add(new JLabel(""));
    }
    public void createActionComponent(AbstractButton button)
    {
        createComponent(button);
        button.addActionListener(currentMenu);
        button.setActionCommand(button.getText());
        button.setHorizontalAlignment(AbstractButton.CENTER);
        button.setText(rp.getLanguage().translate(button.getText()));
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
    public void showHighScore()
    {
        setVisible(true);
        HighscoreMenu high = new HighscoreMenu(rp);
        switchMenu(high);
        high.enterName();
    }
    public void defaultLayout()
    {
        setLayout(new GridLayout(0,3, 0, 16));
    }
}
