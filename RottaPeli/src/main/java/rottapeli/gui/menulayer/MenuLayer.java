
package rottapeli.gui.menulayer;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import rottapeli.peli.RottaPeli;

/**
 * GUI layer that contains menu navigation.
 * @author Pavel
 */
public class MenuLayer extends JPanel {
/** Game logic object. */
    private RottaPeli rp;
/** Menu that is currently shown. */
    private Menu currentMenu;
/**
 * Constructor.
 * @param peli Game logic button.
 */
    public MenuLayer(RottaPeli peli)
    {
        super(new GridLayout(0,3, 0, 16));
        rp = peli;
        setOpaque(false);
    }
/**
 * Fills an empty row.
 */
    public void emptyRow()
    {
        for (int i = 0; i < 3; i++) add(new JLabel(""));
    }
/**
 * Creates a button for this row.
 * @param label Generic title of the button.
 * @return Pointer to the created button.
 */
    public JButton createButton(String label)
    {
        JButton button = new JButton(label);
        createActionComponent(button);
        return button;
    }
/**
 * Creates a checkbox for this row.
 * @param label Generic title of the checkbox.
 * @return Pointer to the created checkbox.
 */
    public JCheckBox createCheckBox(String label)
    {
        JCheckBox box = new JCheckBox(label);
        createActionComponent(box);
        return box;
    }
/**
 * Creates a label for this row.
 * @param text Generic text of the label.
 * @return Pointer to the created label.
 */
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
/**
 * Creates any component to fit the row in a consistent style.
 * @param component Pointer to a component that is to be placed.
 */
    public void createComponent(JComponent component)
    {
        add(new JLabel(""));
        add(component);
        add(new JLabel(""));
    }
/**
 * Creates a component that will invoke an ActionEvent to fit a row.
 * @param button Pointer to the component that is to be placed.
 */
    public void createActionComponent(AbstractButton button)
    {
        createComponent(button);
        button.addActionListener(currentMenu);
        button.setActionCommand(button.getText());
        button.setHorizontalAlignment(AbstractButton.CENTER);
        button.setText(rp.getLanguage().translate(button.getText()));
    }
/**
 * Goes to the main menu and either shows it or hides it.
 * @param visible true if menu is to be shown, false if it's to be hidden.
 */
    public void popMenu(boolean visible)
    {
        setVisible(visible);
        switchMenu(new MainMenu(rp));
    }
/**
 * Switches and reloads to a specified menu.
 * @param menu Specific menu that will be shown.
 */
    public void switchMenu(Menu menu)
    {
        if (currentMenu != null)    currentMenu.hideMenu();
        currentMenu = menu;
        menu.showMenu();
        revalidate();
        repaint();
    }
/**
 * Shows the highscore menu with a possibility of entering the name.
 */
    public void showHighScore()
    {
        setVisible(true);
        HighscoreMenu high = new HighscoreMenu(rp);
        switchMenu(high);
        high.enterName();
    }
/**
 * Default layout of the components. Called every time hideMenu() is called.
 */
    public void defaultLayout()
    {
        setLayout(new GridLayout(0,3, 0, 16));
    }
}
