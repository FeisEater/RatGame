
package rottapeli.gui.menulayer;

import java.awt.event.ActionListener;
import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public abstract class Menu implements ActionListener {
    RottaPeli rp;
    public Menu(RottaPeli peli)
    {
        rp = peli;
    }
    public RottaPeli gameLogic() {return rp;}
    public MenuLayer getMenuLayer() {return rp.getMenu();}
    public abstract void showMenu();
    public void hideMenu()
    {
        getMenuLayer().removeAll();
    }
}
