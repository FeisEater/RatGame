
package rottapeli.domain;

import java.util.List;
import rottapeli.domain.superclasses.Entity;
import rottapeli.domain.superclasses.Moveable;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Eatable;
import rottapeli.interfaces.Hidable;
import rottapeli.interfaces.Killable;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class Rat extends Moveable implements Killable {
    private boolean ismoving;
    public Rat(double x, double y)
    {
        super(x, y, Const.ratwidth, Const.ratheight, 0, Const.ratspeed);
        ismoving = false;
    }

    public void checkCollisions()
    {
        if (getEntities() == null) return;

        checkEatables();
        checkHidables();
    }
    
    public void checkEatables()
    {
        List<Eatable> eatables = getEntities().getList(Eatable.class);
        for (Eatable other : eatables)
        {
            if (collidesWith((Positioned)other))
            {
                eat(other);
            }
        }
    }
    public void eat(Eatable other)
    {
        other.getEaten();
    }
    
    public void checkHidables()
    {
        List<Hidable> hidables = getEntities().getList(Hidable.class);
        for (Hidable other : hidables)
        {
            if (collidesWith((Positioned)other))
            {
                hide(other);
            }
        }
    }
    public void hide(Hidable other)
    {
        removeTails();
        switch (other.hide(this))
        {
            case TOP:
                setPos(X(), other.bottomBorder());
                setDirection(Const.down);
                break;
            case RIGHT:
                setPos(other.leftBorder() - getWidth(), Y());
                setDirection(Const.left);
                break;                
            case BOTTOM:
                setPos(X(), other.topBorder() - getHeight());
                setDirection(Const.left);
                break;                
            case LEFT:
                setPos(other.rightBorder(), Y());
                setDirection(Const.right);
                break;                
            default:    break;
        }
        ismoving = false;
    }
    
    public void createTail()
    {
        if (getEntities() == null) return;

        List<Tail> tails = getEntities().getList(Tail.class);
        for (Tail other : tails)
        {
            if (other.getOwner() == this && collidesWith(other))
            {
                return;
            }
        }
        
        double tailwidth = (getDirection() == Const.right ||
                getDirection() == Const.left) ?
                Const.tailthickness : Const.ratspeed;
        double tailheight = (getDirection() == Const.up ||
                getDirection() == Const.down) ?
                Const.tailthickness : Const.ratspeed;
        
        Tail newtail = new Tail(X(), Y(), tailwidth, tailheight, this);
        getEntities().addEntity(newtail);
    }
    public void removeTails()
    {
        if (getEntities() == null || getEntities().getList(Tail.class).size() == 0)
            return;

        List<Tail> tails = getEntities().getList(Tail.class);
        for (Tail other : tails)
        {
            if (other.getOwner() == this)
                getEntities().removeEntity(other);
        }
    }
@Override
    public void die()
    {
        //Add code here
    }
@Override
    public void setDirection(double dir)
    {
        super.setDirection(dir);
        ismoving = true;
    }
    
@Override
    public void update()
    {
        if (ismoving)
        {
            move();
            createTail();
            checkCollisions();
        }
    }
}
