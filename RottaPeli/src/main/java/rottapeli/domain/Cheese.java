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
    
    public Cheese()
    {
        super(Const.cheesewidth, Const.cheeseheight);
    }

    public Cheese(double x, double y)
    {
        this();
        setPos(x,y);
    }
    @Override
    public void defaultPosition()
    {
        setPos(Math.round(Math.random() * Const.width),
                Math.round(Math.random() * Const.height));
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
