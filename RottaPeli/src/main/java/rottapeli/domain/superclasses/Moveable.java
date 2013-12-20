
package rottapeli.domain.superclasses;

import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Tools;

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
    
    public boolean collidesWith(Positioned other)
    {
        if (this == other)  return false;
        
        return Tools.isInside(this, other) || Tools.isInside(other, this);
    }
    
    @Override
    public void update()    {}
}
