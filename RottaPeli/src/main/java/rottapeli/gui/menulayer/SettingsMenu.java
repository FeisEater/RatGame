
package rottapeli.gui.menulayer;

import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public class SettingsMenu extends Menu {
/**
 * Constructor.
 * @param rp Game logic object.
 */
    public SettingsMenu(RottaPeli rp)   {super(rp);}
/**
 * Performs all necessary actions to make this menu show up. Creates all
 * neccessary components.
 */
    @Override
    public void showMenu()
    {
        getMenuLayer().emptyRow();
        getMenuLayer().createButton("#language");
        getMenuLayer().createButton("#controls");
        //getMenuLayer().createButton("#volume");
        
        JCheckBox box = getMenuLayer().createCheckBox("#aspectratio");
        box.setSelected(gameLogic().getSettings().hasAspectRatio());
        
        getMenuLayer().createButton("#back");
        getMenuLayer().emptyRow();
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
            case "#language":
                languagePressed();
                break;
            case "#controls":
                controlsPressed();
                break;
            case "#aspectratio":
                aspectratioPressed();
                break;
            case "#back":
                backPressed();
                break;
            default:
                break;
        }
    }
/**
 * React to the Language selection button being pressed.
 * Lets user choose a language.
 */
    public void languagePressed()
    {
        gameLogic().getLanguage().chooseLanguage();
        getMenuLayer().switchMenu(new SettingsMenu(rp));
    }
/**
 * React to the Controls selection button being pressed.
 * Go to the Controls menu.
 */
    public void controlsPressed()
    {
        getMenuLayer().switchMenu(new ControlsMenu(rp));
    }
/**
 * React to the Aspect Ratio checkbox being pressed.
 * Toggles aspect ratio on or off.
 */
    public void aspectratioPressed()
    {
        gameLogic().getSettings().toggleAspectRatio();
        getMenuLayer().switchMenu(new SettingsMenu(rp));
    }
/**
 * React to the Back button being pressed.
 * Returns to the Main menu.
 */
    public void backPressed()
    {
        gameLogic().getSettings().saveSettings();
        getMenuLayer().switchMenu(new MainMenu(rp));
    }
}
