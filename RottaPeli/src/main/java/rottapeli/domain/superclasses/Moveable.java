
package rottapeli.domain.superclasses;

import java.util.List;
import java.util.Set;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Tools;
import rottapeli.resource.ApproachFrom;
/**
 *
 * @author Pavel
 */
public class Moveable extends Positioned implements Updatable {
    private double direction;
    private double speed;
    private double oldx;
    private double oldy;
    public Moveable(double x, double y, double w, double h, double dir, double speed)
    {
        super(x,y,w,h);
        oldx = x;
        oldy = y;
        direction = dir;
        this.speed = speed;
    }
    public void move()
    {
        oldx = X();
        oldy = Y();
        setPos( X() + xSpeed(),
                Y() + ySpeed());
    }
    
    public double oldX()  {return oldx;}
    public double oldY()  {return oldy;}
    public double xSpeed()
    {
        return Math.cos(direction) * speed;
    }
    public double ySpeed()
    {
        return Math.sin(direction) * speed;
    }
    
    public double getDirection()    {return direction;}
    public void setDirection(double dir)
    {
        direction = dir;
    }
    public void setDirection(double xspeed, double yspeed)
    {
        if (xspeed == xSpeed() && yspeed == ySpeed())
            return;
        
        direction = Math.atan2(yspeed, xspeed);
    }
    
    public void checkCollisions()
    {
        if (getEntities() == null)  return;
        
        List<Entity> entities = getEntities().getList(null);
        for (Entity other : entities)
        {
            if (collidesWith((Positioned)other))
            {
                reactToCollision(other);
            }
        }
    }
    public boolean collidesWith(Positioned other)
    {
        if (this == other)  return false;
        
        return Tools.isInside(this, other) || Tools.isInside(other, this);
    }
    
    public void reactToCollision(Entity other)   {}
    
    public ApproachFrom[] collisionType(Positioned other)
    {
        ApproachFrom[] af = new ApproachFrom[2];
        af[0] = ApproachFrom.NONE;
        af[1] = ApproachFrom.NONE;

        if (oldX() + getWidth() < other.leftBorder())
            af[0] = ApproachFrom.LEFT;
        else if (oldX() > other.rightBorder())
            af[0] = ApproachFrom.RIGHT;
        
        if (oldY() + getHeight() < other.topBorder())
            af[1] = ApproachFrom.ABOVE;
        else if (oldY() > other.bottomBorder())
            af[1] = ApproachFrom.BELOW;
        
        return af;
    }
    
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
    
    @Override
    public void update()    {}
}
