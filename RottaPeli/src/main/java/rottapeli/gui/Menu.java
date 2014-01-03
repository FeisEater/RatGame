
package rottapeli.gui;

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
public class Menu extends JPanel implements ActionListener {
    private RottaPeli rp;
    private JButton continueGame;
    public Menu(RottaPeli peli)
    {
        super(new GridLayout(8,3, 0, 32));
        rp = peli;
        setOpaque(false);
        
        emptyRow();
        continueGame = createButton("Continue game");
        createButton("New game");
        createButton("Options");
        createButton("High Score");
        createButton("Credits");
        createButton("Exit");
        emptyRow();
        
        popMenu(true);
    }
    public void emptyRow()
    {
        for (int i = 0; i < 3; i++) add(new JLabel(""));
    }
    public JButton createButton(String label)
    {
        add(new JLabel(""));
        JButton button = new JButton(label);
        button.addActionListener(this);
        button.setActionCommand(label);
        add(button);
        add(new JLabel(""));
        return button;
    }
    public void popMenu(boolean visible)
    {
        setVisible(visible);
        continueGame.setVisible(!rp.isGameOver());
    }
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        switch (ae.getActionCommand())
        {
            case "Continue game":
                popMenu(false);
                rp.getTimer().setPaused(false);
                break;
            case "New game":
                rp.resetGame();
                popMenu(false);
                rp.getTimer().setPaused(false);
                break;
            case "Credits":
                System.out.println("Lead development: FeisEater");
                break;
            case "Exit":
                System.exit(0);
                break;
            default:
                break;
        }
    }
}
