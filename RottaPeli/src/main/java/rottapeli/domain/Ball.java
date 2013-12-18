package rottapeli.domain;

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
            
            Positioned testEntity = new Positioned(other.leftBorder(), other.topBorder(),
                    other.rightBorder() - other.leftBorder(),
                    other.bottomBorder() - other.topBorder());
            if (collidesWith(testEntity))
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
            correctXposition(other);
            newXspeed = -xSpeed();
        }
        if (oldy + getHeight() < other.topBorder() || oldy > other.bottomBorder())
        {
            correctYposition(other);
            newYspeed = -ySpeed();
        }
        
        setDirection(newXspeed, newYspeed);
    }
    
    public void correctXposition(Bouncable other)
    {
        double xOffset;
        if (rightBorder() >= other.leftBorder())
        {
            xOffset = other.leftBorder() - rightBorder();
            setPos(other.leftBorder() - getWidth(), Y());
        }
        else
        {
            xOffset = other.rightBorder() - leftBorder();
            setPos(other.rightBorder(), Y());
        }
        setPos(X() + xOffset, Y());
    }
    public void correctYposition(Bouncable other)
    {
        double yOffset;
        if (bottomBorder() >= other.topBorder())
        {
            yOffset = other.topBorder() - bottomBorder();
            setPos(X(), other.topBorder() - getHeight());
        }
        else
        {
            yOffset = other.bottomBorder() - topBorder();
            setPos(X(), other.bottomBorder());
        }
        setPos(X(), Y() + yOffset);
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
