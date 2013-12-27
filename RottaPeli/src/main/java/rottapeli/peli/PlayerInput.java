
package rottapeli.peli;

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
 *
 * @author Pavel
 */
public class PlayerInput implements KeyListener, Updatable {
    private RottaPeli rp;
    private Set<Integer> pressedKeys;
    public PlayerInput(RottaPeli peli)
    {
        rp = peli;
        pressedKeys = new HashSet<Integer>();
    }
        
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
    public void keyPressed(KeyEvent e)
    {
        pressedKeys.add(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_P)
            rp.getTimer().togglePause();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }
}
