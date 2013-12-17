
package rottapeli.domain;

/**
 *
 * @author Pavel
 */
public class Moveable extends Positioned implements Updatable {
    private double direction;
    private double speed;
    public Moveable(double x, double y, double dir, double speed)
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
