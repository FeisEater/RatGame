
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
    public Rat(double x, double y)
    {
        super(x, y, Const.ratwidth, Const.ratheight, 0, Const.ratspeed);
        ismoving = false;
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
        faceAwayFrom(other);
        correctPosition((Positioned) other);
        ismoving = false;
    }
    public void faceAwayFrom(Hidable other)
    {
        if (collisionType((Positioned) other)[0] == ApproachFrom.LEFT)
            setDirection(Const.right);
        if (collisionType((Positioned) other)[0] == ApproachFrom.RIGHT)
            setDirection(Const.left);
        if (collisionType((Positioned) other)[1] == ApproachFrom.ABOVE)
            setDirection(Const.down);
        if (collisionType((Positioned) other)[1] == ApproachFrom.BELOW)
            setDirection(Const.up);
    }
    
    public void examineTail(Tail tail)
    {
        if (tail.getOwner() == this)    canCreateTail = false;
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
    }
    
@Override
    public void reactToCollision(Set classes, Entity other)
    {
        if (classes.contains(Hidable.class))
            hide((Hidable)other);

        if (classes.contains(Eatable.class))
            eat((Eatable)other);

        if (classes.contains(Tail.class))
            examineTail((Tail)other);

    }

@Override
    public void die()
    {
        //Add code here
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
        }
    }
}
