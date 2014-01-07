
package rottapeli.gui.menulayer;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import rottapeli.peli.RottaPeli;

/**
 * The menu that is shown first.
 * @author Pavel
 */
public class MainMenu extends Menu {
/**
 * Constructor.
 * @param rp Game logic object.
 */
    public MainMenu(RottaPeli rp)   {super(rp);}
/**
 * Performs all necessary actions to make this menu show up. Creates all
 * neccessary components. Continue button is showed only if game is paused.
 */
    @Override
    public void showMenu()
    {
        getMenuLayer().emptyRow();
        JButton continueGame = getMenuLayer().createButton("#continue");
        getMenuLayer().createButton("#newgame");
        getMenuLayer().createButton("#settings");
        getMenuLayer().createButton("#highscore");
        getMenuLayer().createButton("#credits");
        getMenuLayer().createButton("#exit");
        getMenuLayer().emptyRow();
        
        continueGame.setVisible(!gameLogic().isGameOver());
    }
/**
 * Reacts to an actionEvent, a button press for instance.
 * @param ae ActionEvent that triggered this method.
 */
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
/**
 * React to the Continue button being pressed.
 * Return to the game if it's paused.
 */
    public void continuePressed()
    {
        getMenuLayer().popMenu(false);
        gameLogic().getTimer().setPaused(false);
    }
/**
 * React to the New Game button being pressed.
 * Start a new game.
 */
    public void newGamePressed()
    {
        gameLogic().resetGame();
        getMenuLayer().popMenu(false);
        gameLogic().getTimer().setPaused(false);
    }
/**
 * React to the Settings button being pressed.
 * Go to the Settings menu.
 */
    public void settingsPressed()
    {
        getMenuLayer().switchMenu(new SettingsMenu(rp));
    }
/**
 * React to the High Score button being pressed.
 * Go to the High Score menu.
 */
    public void highscorePressed()
    {
        getMenuLayer().switchMenu(new HighscoreMenu(rp));
    }
/**
 * React to the Credits button being pressed.
 * Go to the Credits menu.
 */
    public void creditsPressed()
    {
        getMenuLayer().switchMenu(new CreditsMenu(rp));
    }
/**
 * React to the Exit button being pressed.
 * Ends the program.
 */
    public void exitPressed()
    {
        gameLogic().getSettings().saveSettings();
        System.exit(0);
    }
}
