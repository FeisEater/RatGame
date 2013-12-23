
package rottapeli.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import rottapeli.domain.superclasses.Entity;
import rottapeli.domain.superclasses.Moveable;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Eatable;
import rottapeli.interfaces.Hidable;
import rottapeli.interfaces.Killable;
import rottapeli.resource.ApproachFrom;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class Rat extends Moveable implements Killable {
    private boolean ismoving;
    private boolean canCreateTail;
    private double oldDirection;
    private Tail lastTail;
    public Rat(double x, double y)
    {
        super(x, y, Const.ratwidth, Const.ratheight, 0, Const.ratspeed);
        ismoving = false;
        lastTail = null;
    }

    public void startMovingTo(double dir)
    {
        setDirection(dir);
        ismoving = true;
    }
        
    public void eat(Eatable other)
    {
        other.getEaten();
    }
    
    public void hide(Hidable other)
    {
        removeTails();
        correctPosition((Positioned) other);
        faceAwayFrom(other);
        ismoving = false;
    }
    public void faceAwayFrom(Hidable other)
    {
        if (collisionType((Positioned) other)[0] == ApproachFrom.NONE && 
            collisionType((Positioned) other)[1] == ApproachFrom.NONE)
        {
            setDirection(oldDirection);
            return;
        }
        
        if (collisionType((Positioned) other)[0] == ApproachFrom.LEFT)
            setDirection(Const.right);
        if (collisionType((Positioned) other)[0] == ApproachFrom.RIGHT)
            setDirection(Const.left);
        if (collisionType((Positioned) other)[1] == ApproachFrom.ABOVE)
            setDirection(Const.up);
        if (collisionType((Positioned) other)[1] == ApproachFrom.BELOW)
            setDirection(Const.down);
    }
    
    public void examineTail(Tail tail)
    {
        if (tail.getOwner() == this)
        {
            canCreateTail = false;
            if (lastTail != tail && lastTail != null)
                tail.die();
        }
    }
    public void createTail()
    {
        if (!canCreateTail) return;
        
        double tailwidth = (getDirection() == Const.right ||
                getDirection() == Const.left) ?
                Const.tailthickness : Const.ratspeed;
        double tailheight = (getDirection() == Const.up ||
                getDirection() == Const.down) ?
                Const.tailthickness : Const.ratspeed;
        
        Tail newtail = new Tail(X(), Y(), tailwidth, tailheight, this);
        getEntities().addEntity(newtail);
        lastTail = newtail;
    }
    public void removeTails()
    {
        if (getEntities() == null)
            return;

        List toberemoved = new ArrayList();
        List<Tail> tails = getEntities().getList(Tail.class);
        for (Tail other : tails)
        {
            if (other.getOwner() == this)
                toberemoved.add(other);
        }
        getEntities().removeAll(toberemoved);
        lastTail = null;
    }
    
@Override
    public void reactToCollision(Entity other)
    {
        if (Hidable.class.isInstance(other))
            hide((Hidable)other);

        if (Eatable.class.isInstance(other))
            eat((Eatable)other);

        if (Tail.class.isInstance(other))
            examineTail((Tail)other);

    }

@Override
    public void die()
    {
        //Add code here
        System.out.println("DEAD!");
    }
    
@Override
    public void update()
    {
        if (ismoving)
        {            
            move();
            
            canCreateTail = true;
            checkCollisions();
            
            createTail();
            
            oldDirection = getDirection();
        }
    }
}
