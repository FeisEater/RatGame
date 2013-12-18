package rottapeli.domain;

import java.util.List;
import rottapeli.peli.EntityList;
import rottapeli.resource.Const;
/**
 *
 * @author Pavel
 */
public class Ball extends Moveable implements Bouncable {

    public Ball(double x, double y, double ang, EntityList l)
    {
        super(x, y, Const.ballwidth, Const.ballheight, ang, Const.ballspeed, l);
    }
    
    public void checkCollisions()
    {
        List<Bouncable> bouncables = getEntities().getList(Bouncable.class);
        for (Bouncable other : bouncables)
        {
            bounceOff(other);
        }
    }
    
    public void bounceOff(Bouncable other)
    {
        double newXspeed = xSpeed();
        double newYspeed = ySpeed();
        if (rightBorder() >= other.leftBorder() || leftBorder() <= other.rightBorder())
        {
            correctXposition(other);
            newXspeed = -xSpeed();
        }
        if (bottomBorder() >= other.topBorder() || topBorder() <= other.bottomBorder())
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
        move();
        checkCollisions();
    }
}
