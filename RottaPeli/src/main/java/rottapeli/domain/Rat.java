
package rottapeli.domain;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import rottapeli.domain.superclasses.Entity;
import rottapeli.domain.superclasses.Moveable;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Controllable;
import rottapeli.interfaces.Eatable;
import rottapeli.interfaces.Hidable;
import rottapeli.interfaces.Killable;
import rottapeli.resource.ApproachFrom;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class Rat extends Moveable implements Killable, Controllable {
    private boolean ismoving;
    private boolean canCreateTail;
    private double oldDirection;
    private Tail lastTail;

    public Rat()
    {
        super(Const.ratwidth, Const.ratheight, 0, Const.ratspeed);
        ismoving = false;
        lastTail = null;
    }
    
    public Rat(double x, double y)
    {
        this();
        setPos(x,y);
    }
            
    public void eat(Eatable other)
    {
        other.getEaten();
        if (getEntities() != null && getEntities().gameLogic() != null)
            getEntities().gameLogic().playerAteCheese(playerID());
    }
    
    public void hide(Hidable other)
    {
        removeTails();
        correctPosition((Positioned) other);
        faceAwayFrom(other);
        ismoving = false;
        canCreateTail = false;
        if (getEntities() != null && getEntities().gameLogic() != null)
            getEntities().gameLogic().getScore().resetCombo(playerID());
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
                Const.ratspeed : Const.tailthickness;
        double tailheight = (getDirection() == Const.up ||
                getDirection() == Const.down) ?
                Const.ratspeed : Const.tailthickness;
        double tailx = (getDirection() == Const.left) ?
                X() + getWidth() : oldX();
        if (getDirection() == Const.up || getDirection() == Const.down)
            tailx = X() + Const.ratwidth / 2 - Const.tailthickness / 2;
        double taily = (getDirection() == Const.up) ?
                Y() + getHeight() : oldY();
        if (getDirection() == Const.left || getDirection() == Const.right)
            taily = Y() + Const.ratheight / 2 - Const.tailthickness / 2;

        Tail newtail = new Tail(tailx, taily, tailwidth, tailheight, this);
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
    public void defaultPosition()
    {
        removeTails();
        setPos(Math.round(Const.width / 2), 0);
        setDirection(Const.down);
        ismoving = false;
        canCreateTail = false;
        if (getEntities() != null)
            getEntities().gameLogic().getScore().resetCombo(playerID());
    }
@Override
    public void reactToCollision(Entity other, boolean notOnTheEdge)
    {
        if (Hidable.class.isInstance(other))
            hide((Hidable)other);

        if (Eatable.class.isInstance(other) && notOnTheEdge)
            eat((Eatable)other);

        if (Tail.class.isInstance(other) && notOnTheEdge)
            examineTail((Tail)other);

    }

@Override
    public void die()
    {
        defaultPosition();
        if (getEntities() != null)
            getEntities().gameLogic().playerDied(playerID());
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
    
@Override
    public void draw(Graphics g, double xMultiplier, double yMultiplier)
    {
        g.setColor(Color.BLACK);
        g.fill3DRect((int)(X() * xMultiplier), (int)(Y() * yMultiplier),
            (int)(getWidth() * xMultiplier), (int)(getHeight() * yMultiplier), true);
    }

@Override
    public void moveTo(double dir)
    {
        setDirection(dir);
        ismoving = true;
    }
@Override
    public int playerID()   {return 1;}
}
