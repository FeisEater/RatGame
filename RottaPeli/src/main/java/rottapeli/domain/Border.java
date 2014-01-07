package rottapeli.domain;

import java.awt.Graphics;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Bouncable;
import rottapeli.interfaces.Hidable;

/**
 * Dummy entity which causes Balls to bounce off it and Rats to hide within it.
 * @author Pavel
 */
public class Border extends Positioned implements Bouncable, Hidable {
/**
 * Constructor.
 * @param x X position where Border is created.
 * @param y Y position where Border is created.
 * @param width Width of the Border.
 * @param height Height of the Border.
 */
    public Border(double x, double y, double width, double height)
    {
        super(x,y,width,height);
    }
/**
 * Contains instructions on how to represent this entity in the GameField.
 * <p>
 * Border is invisible.
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
    }

}
