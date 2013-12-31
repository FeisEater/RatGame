
package rottapeli.domain.superclasses;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import rottapeli.resource.Const;
import rottapeli.resource.Tools;

/**
 * Entity that can be positioned.
 * <p>
 * Extend this class when implementing an Entity that is drawn or has coordinates
 * and dimensions.
 * @author Pavel
 */
public class Positioned extends Entity {
    private double x;
    private double y;
    private double width;
    private double height;
/**
 * Constructor.
 * <p>
 * Without coordinates positions Entity at a defaultPosition().
 * @param w Width of the Entity.
 * @param h Height of the Entity.
 */
    public Positioned(double w, double h)
    {
        super();
        width = w;
        height = h;
        defaultPosition();
    }
/**
 * Constructor.
 * @param x X position where Entity is created.
 * @param y Y position where Entity is created.
 * @param w Width of the Entity.
 * @param h Height of the Entity.
 */
    public Positioned(double x, double y, double w, double h)
    {
        this(w, h);
        this.x = x;
        this.y = y;
    }
    public double X()    {return x;}
    public double Y()    {return y;}
/**
 * Relocates Entity to given coordinates.
 * @param x X coordinate.
 * @param y Y cooridante.
 */
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
/**
 * Check if this and other Entity collide.
 * @param other Other Entity that may collide with this Entity.
 * @param notOnTheEdge true if other is truly inside this Entity, not simply
 *  on the edge.
 * @return true if this and other Entity collide.
 */
    public boolean collidesWith(Positioned other, boolean notOnTheEdge)
    {
        if (this == other)  return false;
        
        return  Tools.isInside(this, other, notOnTheEdge) || 
                Tools.isInside(other, this, notOnTheEdge);
    }
/**
 * Finds a spot that is not occupied by another Entity. If current position
 * is not occupied doesn't do anything.
 */
    public void findNearestFreeSpot()
    {
        if (getEntities() == null)  return;

        Deque<Double> xQueue = new ArrayDeque<Double>();
        Deque<Double> yQueue = new ArrayDeque<Double>();
        
        List<Double> usedX = new ArrayList<Double>();
        List<Double> usedY = new ArrayList<Double>();
        
        enqueueCoordinate(x, y, xQueue, yQueue);
        
        List<Positioned> entities = getEntities().getList(Positioned.class);

        while (!xQueue.isEmpty())
        {
            dequeueCoordinate(xQueue, yQueue);
            
            if (isColliding(entities))
            {
                getNearbyPositions(xQueue, yQueue, usedX, usedY);
                markCurrentCoordinateAsUsed(usedX, usedY);
            }
            else    return;
        }
    }
/**
 * Enqueues coordinates above, below, left and right of the current coordinate.
 * Doesn't do anything if current position is out of bounds or current position
 * has been checked for near coordinates already.
 * @param xQueue Queue of the X coordinates.
 * @param yQueue Queue of the Y coordinates.
 * @param usedX X coordinates that have been checked already.
 * @param usedY Y coordinates that have been checked already.
 */
    public void getNearbyPositions(Deque<Double> xQueue, Deque<Double> yQueue, List<Double> usedX, List<Double> usedY)
    {
        if (outOfBounds() || currentCoordinateIsUsed(usedX, usedY))
            return;

        enqueueCoordinate(x + 2 * getWidth(), y, xQueue, yQueue);
        enqueueCoordinate(x, y + 2 * getHeight(), xQueue, yQueue);
        enqueueCoordinate(x - 2 * getWidth(), y, xQueue, yQueue);
        enqueueCoordinate(x, y - 2 * getHeight(), xQueue, yQueue);
    }
    
    private void enqueueCoordinate(double ix, double iy, Deque<Double> xQueue, Deque<Double> yQueue)
    {
        xQueue.add(ix);
        yQueue.add(iy);
    }
    private void dequeueCoordinate(Deque<Double> xQueue, Deque<Double> yQueue)
    {
        x = xQueue.poll();
        y = yQueue.poll();
    }
    private void markCurrentCoordinateAsUsed(List<Double> usedX, List<Double> usedY)
    {
        usedX.add(x);
        usedY.add(y);
    }
    private boolean isColliding(List<Positioned> entities)
    {
        for (Positioned other : entities)
        {
            if (collidesWith(other, false))
                return true;
        }
        return false;
    }
    private boolean outOfBounds()
    {
        return x + getWidth() > Const.width ||
            y + getHeight() > Const.height ||
            x < 0 || y < 0;
    }
    private boolean currentCoordinateIsUsed(List<Double> usedX, List<Double> usedY)
    {
        for (int i = 0; i < usedX.size(); i++)
        {
            if (usedX.get(i) == x && usedY.get(i) == y)
                return true;
        }
        return false;
    }
/**
 * Places the Entity at a specified location. Must be overriden to be functional.
 */
    public void defaultPosition()   {}
/**
 * Contains instructions on how to represent this entity in the GameField.
 * <p>
 * If not overriden draws a black rectangle.
 * 
 * @param g             Graphics data.
 * @param xMultiplier   Horizontal stretching based on windows width.
 * @param yMultiplier   Vertical stretching based on windows height.
 */

    public void draw(Graphics g, double xMultiplier, double yMultiplier)
    {
        g.setColor(Color.BLACK);
        g.fill3DRect((int)(X() * xMultiplier), (int)(Y() * yMultiplier),
            (int)(getWidth() * xMultiplier), (int)(getHeight() * yMultiplier), true);
    }
}
