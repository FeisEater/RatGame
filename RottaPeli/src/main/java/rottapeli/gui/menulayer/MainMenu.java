
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
                continuePressed();
                break;
            case "#newgame":
                newGamePressed();
                break;
            case "#settings":
                settingsPressed();
                break;
            case "#highscore":
                highscorePressed();
                break;
            case "#credits":
                creditsPressed();
                break;
            case "#exit":
                exitPressed();
                break;
            default:
                break;
        }
    }
    public void continuePressed()
    {
        getMenuLayer().popMenu(false);
        gameLogic().getTimer().setPaused(false);
    }
    public void newGamePressed()
    {
        gameLogic().resetGame();
        getMenuLayer().popMenu(false);
        gameLogic().getTimer().setPaused(false);
    }
    public void settingsPressed()
    {
        getMenuLayer().switchMenu(new SettingsMenu(rp));
    }
    public void highscorePressed()
    {
        getMenuLayer().switchMenu(new HighscoreMenu(rp));
    }
    public void creditsPressed()
    {
        getMenuLayer().switchMenu(new CreditsMenu(rp));
    }
    public void exitPressed()
    {
        gameLogic().getSettings().saveSettings();
        System.exit(0);
    }
}
