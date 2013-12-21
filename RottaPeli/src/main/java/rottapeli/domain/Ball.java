package rottapeli.domain;

import rottapeli.domain.superclasses.Moveable;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Bouncable;
import java.util.List;
import java.util.Set;
import rottapeli.domain.superclasses.Entity;
import rottapeli.interfaces.Killable;
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


    public void kill(Killable other)
    {
        other.die();
    }
    
    public void bounceOff(Bouncable other)
    {
        double newXspeed = xSpeed();
        double newYspeed = ySpeed();
        
        if (oldX() + getWidth() < other.leftBorder() || oldX() > other.rightBorder())
        {
            boolean fromLeft = oldX() + getWidth() < other.leftBorder();
            correctXposition(other, fromLeft);
            newXspeed = -xSpeed();
        }
        if (oldY() + getHeight() < other.topBorder() || oldY() > other.bottomBorder())
        {
            boolean fromTop = oldY() + getHeight() < other.topBorder();
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
    public void reactToCollision(Set classes, Entity other)
    {
        if (classes.contains(Bouncable.class))
            bounceOff((Bouncable)other);

        if (classes.contains(Killable.class))
            kill((Killable)other);

    }

    @Override
    public void update()
    {
        move();
        checkCollisions();
    }
}
