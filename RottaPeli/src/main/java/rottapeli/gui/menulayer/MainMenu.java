
package rottapeli.gui.menulayer;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public class MainMenu extends Menu {
    private JButton continueGame;
    public MainMenu(RottaPeli rp)   {super(rp);}
    @Override
    public void showMenu()
    {
        getMenuLayer().emptyRow();
        continueGame = getMenuLayer().createButton("Continue game");
        getMenuLayer().createButton("New game");
        getMenuLayer().createButton("Settings");
        getMenuLayer().createButton("High Score");
        getMenuLayer().createButton("Credits");
        getMenuLayer().createButton("Exit");
        getMenuLayer().emptyRow();
        
        continueGame.setVisible(!gameLogic().isGameOver());
    }
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        switch (ae.getActionCommand())
        {
            case "Continue game":
                getMenuLayer().popMenu(false);
                gameLogic().getTimer().setPaused(false);
                break;
            case "New game":
                gameLogic().resetGame();
                getMenuLayer().popMenu(false);
                gameLogic().getTimer().setPaused(false);
                break;
            case "Settings":
                getMenuLayer().switchMenu(new SettingsMenu(rp));
                break;
            case "High Score":
                getMenuLayer().switchMenu(new HighscoreMenu(rp));
                break;
            case "Credits":
                getMenuLayer().switchMenu(new CreditsMenu(rp));
                break;
            case "Exit":
                System.exit(0);
                break;
            default:
                break;
        }
    }
}
