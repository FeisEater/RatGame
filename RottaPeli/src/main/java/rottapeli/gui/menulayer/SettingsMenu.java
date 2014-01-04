
package rottapeli.gui.menulayer;

import java.awt.event.ActionEvent;
import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public class SettingsMenu extends Menu {
    public SettingsMenu(RottaPeli rp)   {super(rp);}
    @Override
    public void showMenu()
    {
        getMenuLayer().emptyRow();
        getMenuLayer().createButton("Change language");
        getMenuLayer().createButton("Change controls");
        getMenuLayer().createButton("Audio volume");
        getMenuLayer().createButton("Aspect ratio");
        getMenuLayer().createButton("Back");
        getMenuLayer().emptyRow();
    }
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        switch (ae.getActionCommand())
        {
            case "Back":
                getMenuLayer().switchMenu(new MainMenu(rp));
                break;
            default:
                break;
        }
    }

}
