
package rottapeli.domain;

import java.awt.Color;
import java.awt.Graphics;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Killable;

/**
 * Tail segment that is created when Rat moves. When it dies it causes its
 * creator to die.
 * @author Pavel
 */
public class Tail extends Positioned implements Killable {
    private Killable owner;
/**
 * Constructor.
 * @param x X position where Tail is created.
 * @param y Y position where Tail is created.
 * @param width Width of the Tail.
 * @param height Height of the Tail.
 * @param rat Rat that created this Tail segment.
 */
    public Tail(double x, double y, double width, double height, Killable rat)
    {
        super(x, y, width, height);
        owner = rat;
    }
    public Killable getOwner()  {return owner;}
/**
 * Causes Killable Entity to die.
 * <p>
 * Tail causes its creator to die.
 */
    @Override
    public void die()
    {
        owner.die();
    }
/**
 * Contains instructions on how to represent this entity in the GameField.
 * <p>
 * Tail is a magenta rectangle.
 * 
 * @param g             Graphics data.
 * @param xMultiplier   Horizontal stretching based on windows width.
 * @param yMultiplier   Vertical stretching based on windows height.
 */
    @Override
    public void draw(Graphics g, double offsetX, double offsetY, double xMultiplier, double yMultiplier)
    {
        g.setColor(Color.MAGENTA);
        g.fillRect(drawX(offsetX, xMultiplier), drawY(offsetY, yMultiplier),
            drawWidth(xMultiplier), drawHeight(yMultiplier));
    }
}
