
package rottapeli.domain;

import java.awt.Color;
import java.awt.Graphics;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Killable;

/**
 *
 * @author Pavel
 */
public class Tail extends Positioned implements Killable {
    private Killable owner;
    public Tail(double x, double y, double width, double height, Killable rat)
    {
        super(x, y, width, height);
        owner = rat;
    }
    public Killable getOwner()  {return owner;}
    @Override
    public void die()
    {
        owner.die();
    }
    
    @Override
    public void draw(Graphics g, double xMultiplier, double yMultiplier)
    {
        g.setColor(Color.MAGENTA);
        g.fill3DRect((int)(X() * xMultiplier), (int)(Y() * yMultiplier),
            (int)(getWidth() * xMultiplier), (int)(getHeight() * yMultiplier), true);
    }

}
