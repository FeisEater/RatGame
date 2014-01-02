
package rottapeli.peli;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import rottapeli.interfaces.Controllable;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Const;

/**
 * Component responsible for receiving player input in a correct way.
 * @author Pavel
 */
public class PlayerInput implements KeyEventDispatcher, Updatable {
    private RottaPeli rp;
    private Set<Integer> pressedKeys;
/**
 * Constructor.
 * @param peli Pointer to the game logic Object.
 */
    public PlayerInput(RottaPeli peli)
    {
        rp = peli;
        pressedKeys = new HashSet<Integer>();
    }
/**
 * This method is called every tick on the timer.
 * <p>
 * Examines which keys are being held pressed this tick and reacts appropriately.
 */
    @Override
    public void update()
    {
        for (int e : pressedKeys)
        {
            switch (e)
            {
                case KeyEvent.VK_RIGHT:
                    rp.playerGo(1, Const.right);
                    break;
                case KeyEvent.VK_LEFT:
                    rp.playerGo(1, Const.left);
                    break;
                case KeyEvent.VK_UP:
                    rp.playerGo(1, Const.up);
                    break;
                case KeyEvent.VK_DOWN:
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
        pressedKeys.add(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_P)
            rp.getTimer().togglePause();
    }
/**
 * Method is called immediately when certain key is released.
 * @param e KeyEvent that called this method.
 */
    public void keyReleased(KeyEvent e)
    {
        pressedKeys.remove(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            rp.resetGame();
    }
}
