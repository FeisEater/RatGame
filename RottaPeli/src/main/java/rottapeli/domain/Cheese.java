package rottapeli.domain;

import java.awt.Color;
import java.awt.Graphics;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Bouncable;
import rottapeli.interfaces.Eatable;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class Cheese extends Positioned implements Bouncable, Eatable {
    
    public Cheese(double x, double y)
    {
        super(x, y, Const.cheesewidth, Const.cheeseheight);
    }
    @Override
    public void getEaten()
    {
        getEntities().removeEntity(this);
    }
    
    @Override
    public void draw(Graphics g, double xMultiplier, double yMultiplier)
    {
        g.setColor(Color.GREEN);
        g.fill3DRect((int)(X() * xMultiplier), (int)(Y() * yMultiplier),
            (int)(getWidth() * xMultiplier), (int)(getHeight() * yMultiplier), true);
    }

}
