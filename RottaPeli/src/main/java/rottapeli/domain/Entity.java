
package rottapeli.domain;

/**
 *
 * @author Pavel
 */
public abstract class Entity {
    private double x;
    private double y;
    public Entity(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    public double X()    {return x;}
    public double Y()    {return y;}
    public void setPos(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public abstract void update();
}
