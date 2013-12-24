
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
    private EntityList entities;
    private GameTimer timer;
    private Set<Integer> pressedKeys;
    public PlayerInput(EntityList list)
    {
        entities = list;
        pressedKeys = new HashSet<Integer>();
    }
    public void setTimer(GameTimer gt)  {timer = gt;}

    public void playerGo(int id, double dir)
    {
        List<Controllable> players = entities.getList(Controllable.class);
        for (Controllable plr : players)
        {
            if (plr.playerID() == id)   plr.moveTo(dir);
        }
    }
    
    @Override
    public void update()
    {
        for (int e : pressedKeys)
        {
            switch (e)
            {
                case KeyEvent.VK_RIGHT:
                    playerGo(1, Const.right);
                    break;
                case KeyEvent.VK_LEFT:
                    playerGo(1, Const.left);
                    break;
                case KeyEvent.VK_UP:
                    playerGo(1, Const.up);
                    break;
                case KeyEvent.VK_DOWN:
                    playerGo(1, Const.down);
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
