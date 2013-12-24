package rottapeli.domain;

import java.awt.Graphics;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Bouncable;
import rottapeli.interfaces.Hidable;

/**
 *
 * @author Pavel
 */
public class Border extends Positioned implements Bouncable, Hidable {

    public Border(double x, double y, double width, double height)
    {
        super(x,y,width,height);
    }
    
    @Override
    public void draw(Graphics g, double xMultiplier, double yMultiplier)
    {
    }

}
