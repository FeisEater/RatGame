
package rottapeli.domain;

import rottapeli.peli.EntityList;

/**
 *
 * @author Pavel
 */
public class Moveable extends Positioned implements Updatable {
    private double direction;
    private double speed;
    public Moveable(double x, double y, double w, double h, double dir, double speed, EntityList l)
    {
        super(x,y,w,h,l);
        direction = dir;
        this.speed = speed;
    }
    public void move()
    {
        setPos( X() + xSpeed(),
                Y() + ySpeed());
    }
    public double xSpeed()
    {
        return Math.cos(direction) * speed;
    }
    public double ySpeed()
    {
        return Math.sin(direction) * speed;
    }
    
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
    @Override
    public void update()    {}
}
