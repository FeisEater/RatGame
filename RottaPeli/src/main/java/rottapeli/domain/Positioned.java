
package rottapeli.domain;

import rottapeli.peli.EntityList;

/**
 *
 * @author Pavel
 */
public class Positioned extends Entity {
    private double x;
    private double y;
    private double width;
    private double height;
    public Positioned(double x, double y, double w, double h)
    {
        super();
        this.x = x;
        this.y = y;
        width = w;
        height = h;
    }
    public double X()    {return x;}
    public double Y()    {return y;}
    public void setPos(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    public double getWidth()    {return width;}
    public double getHeight()    {return height;}
    
    public double leftBorder()  {return x;}
    public double rightBorder()  {return x + width;}
    public double topBorder()  {return y;}
    public double bottomBorder()  {return y + height;}

}
