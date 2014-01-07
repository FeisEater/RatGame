
package rottapeli.peli;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import rottapeli.gui.menulayer.Menu;
import rottapeli.interfaces.Controllable;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Const;
import rottapeli.resource.Input;

/**
 * Component responsible for receiving player input in a correct way.
 * @author Pavel
 */
public class PlayerInput implements KeyEventDispatcher, Updatable {
    private RottaPeli rp;
    private Set<Input> pressedKeys;
    private Menu listeningMenu;
/**
 * Constructor.
 * @param peli Pointer to the game logic Object.
 */
    public PlayerInput(RottaPeli peli)
    {
        KeyboardFocusManager manager = 
                KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);

        rp = peli;
        pressedKeys = new HashSet<>();
    }
/**
 * This method is called every tick on the timer.
 * <p>
 * Examines which keys are being held pressed this tick and reacts appropriately.
 */
    @Override
    public void update()
    {
        for (Input i : pressedKeys)
        {
            switch (i)
            {
                case PLR1RIGHT:
                    rp.playerGo(1, Const.right);
                    break;
                case PLR1LEFT:
                    rp.playerGo(1, Const.left);
                    break;
                case PLR1UP:
                    rp.playerGo(1, Const.up);
                    break;
                case PLR1DOWN:
                    rp.playerGo(1, Const.down);
                    break;
                default:
                    break;
            }
        }
    }
@Override
    public boolean dispatchKeyEvent(KeyEvent e)
    {
        switch (e.getID())
        {
            case KeyEvent.KEY_PRESSED:
                keyPressed(e);
                break;
            case KeyEvent.KEY_RELEASED:
                keyReleased(e);
                break;
            default:
                break;
        }
        return false;
    }
/**
 * Method is called immediately when certain key is pressed.
 * @param e KeyEvent that called this method.
 */
    public void keyPressed(KeyEvent e)
    {
        Input input = rp.getSettings().getControl(e.getKeyCode());
        pressedKeys.add(input);
        if (input == Input.PAUSE)
            rp.getTimer().togglePause();
        if (listeningMenu != null)  listeningMenu.keyPressed(e);
    }
/**
 * Method is called immediately when certain key is released.
 * @param e KeyEvent that called this method.
 */
    public void keyReleased(KeyEvent e)
    {
        Input input = rp.getSettings().getControl(e.getKeyCode());
        pressedKeys.remove(input);
        if (listeningMenu != null)  listeningMenu.keyReleased(e);
    }
/**
 * Set certain menu to listen for KeyEvents.
 * @param menu Menu that will react to KeyEvents.
 */
    public void setListenerMenu(Menu menu)  {listeningMenu = menu;}
}
