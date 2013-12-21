package rottapeli.domain;

import rottapeli.domain.superclasses.Moveable;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Bouncable;
import java.util.List;
import java.util.Set;
import rottapeli.domain.superclasses.Entity;
import rottapeli.interfaces.Killable;
import rottapeli.resource.ApproachFrom;
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
        
        if (collisionType((Positioned) other)[0] != ApproachFrom.NONE)
            newXspeed = -xSpeed();
        if (collisionType((Positioned) other)[1] != ApproachFrom.NONE)
            newYspeed = -ySpeed();

        correctPosition((Positioned) other);
        setDirection(newXspeed, newYspeed);
    }
    
    @Override
    public void correctPosition(Positioned other)
    {
        double prevX = X();
        double prevY = Y();
        
        super.correctPosition(other);
        
        setPos(X() + (X() - prevX), Y() + (Y() - prevY));
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
