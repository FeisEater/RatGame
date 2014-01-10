
package rottapeli.gui.menulayer;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import rottapeli.peli.RottaPeli;

/**
 * Menu abstract class that a concrete menu should inherit.
 * @author Pavel
 */
public abstract class Menu implements ActionListener {
/** Game logic object. */
    RottaPeli rp;
/**
 * Constructor.
 * @param peli Game logic object.
 */
    public Menu(RottaPeli peli)
    {
        rp = peli;
    }
    public RottaPeli gameLogic() {return rp;}
/**
 * 
 * @return Menu layer.
 */
    public MenuLayer getMenuLayer() {return rp.getMenu();}
/**
 * Performs all necessary actions to make this menu show up. Creates all
 * neccessary components.
 */
    public abstract void showMenu();
/**
 * Makes all necessary actions to make this menu not show up anymore.
 * Removes all components created by this menu.
 */
    public void hideMenu()
    {
        getMenuLayer().removeAll();
        getMenuLayer().defaultLayout();
    }
/**
 * Reacts to a key press if it's set to listen for key presses.
 * @param e KeyEvent that triggered this method.
 */
    public void keyPressed(KeyEvent e)  {}
/**
 * Reacts to a key release if it's set to listen for key presses.
 * @param e KeyEvent that triggered this method.
 */
    public void keyReleased(KeyEvent e)  {}
}
