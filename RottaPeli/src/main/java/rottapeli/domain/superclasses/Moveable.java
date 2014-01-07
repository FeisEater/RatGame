
package rottapeli.domain.superclasses;

import java.util.List;
import java.util.Set;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Tools;
import rottapeli.resource.ApproachFrom;
/**
 * Entity that moves.
 * <p>
 * Extend this class when implementing an Entity that can move and can cause
 * collisions.
 * @author Pavel
 */
public class Moveable extends Positioned implements Updatable {
/** Direction towards which Moveable Entity is moving in radians. */
    private double direction;
/** Current speed of the movement. */
    private double speed;
/** X coordinate that Entity occupied in the previous tick. */
    private double oldx;
/** Y coordinate that Entity occupied in the previous tick. */
    private double oldy;
/**
 * Constructor.
 * <p>
 * Without coordinates positions Entity at a defaultPosition().
 * @param w Width of the Moveable Entity.
 * @param h Height of the Moveable Entity.
 * @param dir Direction to which Moveable Entity will move to.
 * @param speed Movement speed.
 */
    public Moveable(double w, double h, double dir, double speed)
    {
        super(w,h);
        oldx = X();
        oldy = Y();
        direction = dir;
        this.speed = speed;
    }
/**
 * Constructor.
 * @param x X position where Moveable Entity is created.
 * @param y Y position where Moveable Entity is created.
 * @param w Width of the Moveable Entity.
 * @param h Height of the Moveable Entity.
 * @param dir Direction to which Moveable Entity will move to.
 * @param speed Movement speed.
 */
    public Moveable(double x, double y, double w, double h, double dir, double speed)
    {
        this(w, h, dir, speed);
        oldx = x;
        oldy = y;
        setPos(x,y);
    }
/**
 * Moves this Entity for a tick of the timer. Stores old coordinates for 
 * collision calculation.
 */
    public void move()
    {
        oldx = X();
        oldy = Y();
        setPos( X() + xSpeed(),
                Y() + ySpeed());
    }
/**
 * @return X component of the previous coordinate.
 */
    public double oldX()  {return oldx;}
/**
 * @return Y component of the previous coordinate.
 */
    public double oldY()  {return oldy;}
/**
 * @return X component of the current speed.
 */
    public double xSpeed()
    {
        return Math.cos(direction) * speed;
    }
/**
 * @return Y component of the current speed.
 */
    public double ySpeed()
    {
        return Math.sin(direction) * speed;
    }
    
    public double getDirection()    {return direction;}
    public void setDirection(double dir)
    {
        direction = dir;
    }
/**
 * Sets new direction by calculating speed components.
 * @param xspeed X component of the speed.
 * @param yspeed Y component of the speed.
 */
    public void setDirection(double xspeed, double yspeed)
    {
        if (xspeed == xSpeed() && yspeed == ySpeed())
            return;
        
        direction = Math.atan2(yspeed, xspeed);
    }
/**
 * Goes through all Entities and calls reactToCollision() if collision is detected.
 */
    public void checkCollisions()
    {
        if (getEntities() == null)  return;
        
        List<Entity> entities = getEntities().getList(null);
        for (Entity other : entities)
        {
            if (collidesWith((Positioned)other, false))
            {
                reactToCollision(other, collidesWith((Positioned)other, true));
            }
        }
    }
/**
 * This method is called every collision in order to do further actions
 * according to the collided entity's properties. Must be overriden if this
 * Entity does anything on collisions.
 * @param other Entity with which this Entity has collided.
 * @param notOnTheEdge true if other is truly inside this Entity, not simply
 *  on the edge.
 */
    public void reactToCollision(Entity other, boolean notOnTheEdge)   {}
/**
 * Returns the type of the collision that happened between this and other Entity.
 * @param other Other Entity with which this Entity collided.
 * @return  collisionType(other)[0] tells if this Entity approached from left or right.
 *          <p>
 *          collisionType(other)[1] tells if this Entity approached from above or below.
 */
    public ApproachFrom[] collisionType(Positioned other)
    {
        ApproachFrom[] af = new ApproachFrom[2];
        af[0] = ApproachFrom.NONE;
        af[1] = ApproachFrom.NONE;

        if (oldX() + getWidth() <= other.leftBorder())
            af[0] = ApproachFrom.LEFT;
        else if (oldX() >= other.rightBorder())
            af[0] = ApproachFrom.RIGHT;
        
        if (oldY() + getHeight() <= other.topBorder())
            af[1] = ApproachFrom.ABOVE;
        else if (oldY() >= other.bottomBorder())
            af[1] = ApproachFrom.BELOW;
        
        return af;
    }
/**
 * Corrects position to prevent this entity being inside the other.
 * @param other Entity in respect to which this entity's position is corrected.
 */
    public void correctPosition(Positioned other)
    {
        if (collisionType(other)[0] == ApproachFrom.LEFT)
            setPos(other.leftBorder() - getWidth(), Y());
        if (collisionType(other)[0] == ApproachFrom.RIGHT)
            setPos(other.rightBorder(), Y());
        
        if (collisionType(other)[1] == ApproachFrom.ABOVE)
            setPos(X(), other.topBorder() - getHeight());
        if (collisionType(other)[1] == ApproachFrom.BELOW)
            setPos(X(), other.bottomBorder());
    }
/**
 * This method is called every tick on the timer.
 */
    @Override
    public void update()    {}
}
