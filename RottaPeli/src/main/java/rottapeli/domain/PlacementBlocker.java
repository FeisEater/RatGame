package rottapeli.domain;

import java.awt.Graphics;
import rottapeli.domain.superclasses.Positioned;

/**
 *
 * @author Pavel
 */
public class PlacementBlocker extends Positioned {
    
    public PlacementBlocker(double x, double y, double width, double height)
    {
        super(x, y, width, height);
    }
    @Override
    public void draw(Graphics g, double xMultiplier, double yMultiplier) {}
}
