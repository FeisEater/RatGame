
package rottapeli.gui.menulayer;

import java.awt.event.ActionEvent;
import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public class CreditsMenu extends Menu {
    public CreditsMenu(RottaPeli rp)   {super(rp);}
    @Override
    public void showMenu()
    {
        getMenuLayer().emptyRow();
        getMenuLayer().emptyRow();
        getMenuLayer().createLabel("#fullcredits");
        getMenuLayer().emptyRow();
        getMenuLayer().emptyRow();
        getMenuLayer().createButton("#back");
        getMenuLayer().emptyRow();
    }
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        switch (ae.getActionCommand())
        {
            case "#back":
                getMenuLayer().switchMenu(new MainMenu(rp));
                break;
            default:
                break;
        }
    }

}
