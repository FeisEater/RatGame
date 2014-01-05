
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
        continueGame = getMenuLayer().createButton("#continue");
        getMenuLayer().createButton("#newgame");
        getMenuLayer().createButton("#settings");
        getMenuLayer().createButton("#highscore");
        getMenuLayer().createButton("#credits");
        getMenuLayer().createButton("#exit");
        getMenuLayer().emptyRow();
        
        continueGame.setVisible(!gameLogic().isGameOver());
    }
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        switch (ae.getActionCommand())
        {
            case "#continue":
                getMenuLayer().popMenu(false);
                gameLogic().getTimer().setPaused(false);
                break;
            case "#newgame":
                gameLogic().resetGame();
                getMenuLayer().popMenu(false);
                gameLogic().getTimer().setPaused(false);
                break;
            case "#settings":
                getMenuLayer().switchMenu(new SettingsMenu(rp));
                break;
            case "#highscore":
                getMenuLayer().switchMenu(new HighscoreMenu(rp));
                break;
            case "#credits":
                getMenuLayer().switchMenu(new CreditsMenu(rp));
                break;
            case "#exit":
                gameLogic().getSettings().saveSettings();
                System.exit(0);
                break;
            default:
                break;
        }
    }
}
