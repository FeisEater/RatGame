package rottapeli.domain;

import java.awt.Color;
import java.awt.Graphics;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Bouncable;
import rottapeli.interfaces.Eatable;
import rottapeli.resource.Const;

/**
 * Objective of the game.
 * <p>
 * Rat must eat all of the cheese to proceed to the next stage. Cheese causes
 * Balls to bounce off it.
 * @author Pavel
 */
public class Cheese extends Positioned implements Bouncable, Eatable {
/**
 * Constructor.
 * <p>
 * Calling an Entity constructor without parameters will place the Entity
 * in defaultPosition.
 */
    public Cheese()
    {
        super(Const.cheesewidth, Const.cheeseheight);
    }
/**
 * Constructor.
 * @param x X position where Cheese is created.
 * @param y Y position where Cheese is created.
 */
    public Cheese(double x, double y)
    {
        super(x, y, Const.cheesewidth, Const.cheeseheight);
    }
/**
 * Places the Entity at a specified location. Must be overriden to be functional.
 * <p>
 * Cheese is placed at a random location. It is preferrable to call
 * findNearestFreeSpot() after calling this method.
 */
    @Override
    public void defaultPosition()
    {
        setPos(Math.round(Math.random() * (Const.width - getWidth())),
                Math.round(Math.random() * (Const.height - getHeight())));
    }
/**
 * Remove the cheese once it's been eaten.
 */
    @Override
    public void getEaten()
    {
        getEntities().removeEntity(this);
    }
/**
 * Contains instructions on how to represent this entity in the GameField.
 * <p>
 * Cheese is a green rectangle.
 * 
 * @param g             Graphics data.
 * @param offsetX       X position of the drawing field in respect to window's location.
 * @param offsetY       Y position of the drawing field in respect to window's location.
 * @param xMultiplier   Horizontal stretching based on window's width.
 * @param yMultiplier   Vertical stretching based on window's height.
 */
    @Override
    public void draw(Graphics g, double offsetX, double offsetY, double xMultiplier, double yMultiplier)
    {
        g.setColor(Color.GREEN);
        g.fillRect(drawX(offsetX, xMultiplier), drawY(offsetY, yMultiplier),
            drawWidth(xMultiplier), drawHeight(yMultiplier));
    }

}
