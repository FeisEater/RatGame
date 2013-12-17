
package rottapeli.domain;

import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class MoveableEntity extends Entity {
    private double direction;
    private double speed;
    public MoveableEntity(double x, double y, double dir, double speed)
    {
        super(x,y);
        direction = dir;
        this.speed = speed;
    }
    public void move()
    {
        setPos( X() + Math.cos(direction) * speed,
                Y() + Math.sin(direction) * speed);
    }
    public void setDirection(double dir)
    {
        direction = dir;
    }
    @Override
    public void update()    {}
}
