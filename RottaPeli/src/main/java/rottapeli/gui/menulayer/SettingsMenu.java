
package rottapeli.gui.menulayer;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JCheckBox;
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
        getMenuLayer().createButton("#language");
        getMenuLayer().createButton("#controls");
        getMenuLayer().createButton("#volume");
        
        JCheckBox box = getMenuLayer().createCheckBox("#aspectratio");
        box.setSelected(gameLogic().getSettings().hasAspectRatio());
        
        getMenuLayer().createButton("#back");
        getMenuLayer().emptyRow();
    }
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        switch (ae.getActionCommand())
        {
            case "#aspectratio":
                gameLogic().getSettings().toggleAspectRatio();
                break;
            case "#back":
                gameLogic().getSettings().saveSettings();
                getMenuLayer().switchMenu(new MainMenu(rp));
                break;
            default:
                break;
        }
    }

}
