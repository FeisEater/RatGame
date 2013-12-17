
package rottapeli.domain;

/**
 *
 * @author Pavel
 */
public abstract class Positioned implements Entity {
    private double x;
    private double y;
    public Positioned(double x, double y)
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

}
