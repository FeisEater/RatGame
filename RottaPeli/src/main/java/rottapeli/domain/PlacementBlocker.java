package rottapeli.domain;

import java.awt.Graphics;
import rottapeli.domain.superclasses.Positioned;

/**
 * Dummy Entity that blocks Entities from placing them in this area.
 * Only works if placed Entity has findNearestFreeSpot() called.
 * @author Pavel
 */
public class PlacementBlocker extends Positioned {
/**
 * Constructor.
 * @param x X position where PlacementBlocker is created.
 * @param y Y position where PlacementBlocker is created.
 * @param width Width of the PlacementBlocker.
 * @param height Height of the PlacementBlocker.
 */
    public PlacementBlocker(double x, double y, double width, double height)
    {
        super(x, y, width, height);
    }
/**
 * Contains instructions on how to represent this entity in the GameField.
 * <p>
 * PlacementBlocker is invisible.
 * 
 * @param g             Graphics data.
 * @param offsetX       X position of the drawing field in respect to window's location.
 * @param offsetY       Y position of the drawing field in respect to window's location.
 * @param xMultiplier   Horizontal stretching based on window's width.
 * @param yMultiplier   Vertical stretching based on window's height.
 */
    @Override
    public void draw(Graphics g, double offsetX, double offsetY, double xMultiplier, double yMultiplier) {}
}
