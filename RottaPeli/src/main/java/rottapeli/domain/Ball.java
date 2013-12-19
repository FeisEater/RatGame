package rottapeli.domain;

import rottapeli.domain.superclasses.Moveable;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Bouncable;
import java.util.List;
import rottapeli.peli.EntityList;
import rottapeli.resource.Const;
/**
 *
 * @author Pavel
 */
public class Ball extends Moveable implements Bouncable {

    public Ball(double x, double y, double ang)
    {
        super(x, y, Const.ballwidth, Const.ballheight, ang, Const.ballspeed);
    }

    public void checkCollisions(double oldx, double oldy)
    {
        if (getEntities() == null) return;

        List<Bouncable> bouncables = getEntities().getList(Bouncable.class);
        for (Bouncable other : bouncables)
        {
            if (this == other)  continue;
            
            if (collidesWith((Positioned)other))
            {
                bounceOff(other, oldx, oldy);
            }
        }
    }
    
    public void bounceOff(Bouncable other, double oldx, double oldy)
    {
        double newXspeed = xSpeed();
        double newYspeed = ySpeed();
        
        if (oldx + getWidth() < other.leftBorder() || oldx > other.rightBorder())
        {
            boolean fromLeft = oldx + getWidth() < other.leftBorder();
            correctXposition(other, fromLeft);
            newXspeed = -xSpeed();
        }
        if (oldy + getHeight() < other.topBorder() || oldy > other.bottomBorder())
        {
            boolean fromTop = oldy + getHeight() < other.topBorder();
            correctYposition(other, fromTop);
            newYspeed = -ySpeed();
        }
        
        setDirection(newXspeed, newYspeed);
    }
    
    public void correctXposition(Bouncable other, boolean fromLeft)
    {
        double xOffset;
        if (fromLeft)
            xOffset = other.leftBorder() - rightBorder();
        else
            xOffset = other.rightBorder() - leftBorder();

        setPos(X() + 2 * xOffset, Y());
    }
    public void correctYposition(Bouncable other, boolean fromTop)
    {
        double yOffset;
        if (fromTop)
            yOffset = other.topBorder() - bottomBorder();
        else
            yOffset = other.bottomBorder() - topBorder();

        setPos(X(), Y() + 2 * yOffset);
    }

    @Override
    public void update()
    {
        double oldx = X();
        double oldy = Y();
        move();
        checkCollisions(oldx, oldy);
    }
}
