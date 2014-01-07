
package rottapeli.gui.menulayer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import rottapeli.peli.RottaPeli;

/**
 * The menu that is accessed from Main menu > Credits
 * @author Pavel
 */
public class CreditsMenu extends Menu {
/**
 * Constructor.
 * @param rp Game logic object.
 */
    public CreditsMenu(RottaPeli rp)   {super(rp);}
/**
 * Performs all necessary actions to make this menu show up. Creates all
 * neccessary components.
 */
    @Override
    public void showMenu()
    {
        getMenuLayer().setLayout(new GridLayout(0,3));

        getMenuLayer().emptyRow();
        getMenuLayer().emptyRow();
        getMenuLayer().createLabel("#fullcredits");
        getMenuLayer().createLabel("#fullcredits2");
        getMenuLayer().createLabel("#fullcredits3");
        getMenuLayer().emptyRow();
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
            case "#back":
                backPressed();
                break;
            default:
                break;
        }
    }
/**
 * React to the Back button being pressed. Return to the Main menu.
 */
    public void backPressed()
    {
        getMenuLayer().switchMenu(new MainMenu(rp));
    }
}
