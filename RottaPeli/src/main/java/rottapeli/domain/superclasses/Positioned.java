
package rottapeli.domain.superclasses;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import rottapeli.resource.Const;
import rottapeli.resource.Tools;

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
        this.x = Tools.round(x);
        this.y = Tools.round(y);
    }
    public double getWidth()    {return width;}
    public double getHeight()    {return height;}
    
    public double leftBorder()  {return x;}
    public double rightBorder()  {return x + width;}
    public double topBorder()  {return y;}
    public double bottomBorder()  {return y + height;}
    
    public void findNearestFreeSpot()
    {
        if (getEntities() == null)  return;
        
        List<Entity> entities = getEntities().getList(null);
        Deque<Double> xQueue = new ArrayDeque<Double>();
        Deque<Double> yQueue = new ArrayDeque<Double>();
        
        xQueue.add(x);
        yQueue.add(y);
        while (!xQueue.isEmpty())
        {
            x = xQueue.poll();
            y = yQueue.poll();
            boolean noCollisions = true;
            for (Entity other : entities)
            {
                if (collidesWith((Positioned)other))
                {
                    noCollisions = false;
                    getNearbyPositions(xQueue, yQueue);
                }
            }
            if (noCollisions)    return;
        }
    }
    
    public boolean collidesWith(Positioned other)
    {
        if (this == other)  return false;
        
        return Tools.isInside(this, other) || Tools.isInside(other, this);
    }

    public void getNearbyPositions(Deque<Double> xQueue, Deque<Double> yQueue)
    {
        if (x + getWidth() > Const.width)
        {
            xQueue.add(x + 2 * getWidth());
            yQueue.add(y);
        }

        if (y + getHeight() > Const.height)
        {
            xQueue.add(x);
            yQueue.add(y + 2 * getHeight());
        }

        if (x < 0)
        {
            xQueue.add(x - 2 * getWidth());
            yQueue.add(y);
        }

        if (y < 0)
        {
            xQueue.add(x);
            yQueue.add(y - 2 * getHeight());
        }
    }
    
    public void draw(Graphics g, double xMultiplier, double yMultiplier)
    {
        g.setColor(Color.BLACK);
        g.fill3DRect((int)(X() * xMultiplier), (int)(Y() * yMultiplier),
            (int)(getWidth() * xMultiplier), (int)(getHeight() * yMultiplier), true);
    }
}
