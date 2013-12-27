package rottapeli.domain;

import java.awt.Color;
import java.awt.Graphics;
import rottapeli.domain.superclasses.Moveable;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Bouncable;
import java.util.List;
import java.util.Set;
import rottapeli.domain.superclasses.Entity;
import rottapeli.interfaces.Killable;
import rottapeli.resource.ApproachFrom;
import rottapeli.resource.Const;
import rottapeli.resource.Tools;
/**
 * Moving bouncing ball.
 * <p>
 * Main hazard in the game. Moves with a constant speed. Changes direction only
 * if it collides with an Entity that implements Bouncable interface. On
 * collision kills entities that implement Killable interface.
 * @author Pavel
 */
public class Ball extends Moveable implements Bouncable {

/**
 * Constructor
 * <p>
 * Calling an Entity constructor without parameters will place the Entity
 * in defaultPosition.
 */
    public Ball()
    {
        super(Const.ballwidth, Const.ballheight, 
                Tools.randomDiagonalDirection(), Const.ballspeed);
    }
/**
 * Constructor
 * 
 * @param x     X position where ball is created.
 * @param y     Y position where ball is created.
 * @param ang   Initial direction in radians where ball moves to.
 */
    public Ball(double x, double y, double ang)
    {
        super(x, y, Const.ballwidth, Const.ballheight, ang, Const.ballspeed);
    }
    
/**
 * Calls die() method to the Entity given as a parameter.
 * 
 * @param other Killable Entity that is to be killed.
 */
    public void kill(Killable other)
    {
        other.die();
    }
/**
 * Bounces off the Entity given as a parameter.
 * <p>
 * When method is called, Ball will change its direction depending on its
 * previous trajectory and the other Entity's location.
 * 
 * @param other Entity from which Ball bounces off.
 */
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
/**
 * Corrects position to prevent this entity being inside the other.
 * <p>
 * This method is overriden by Ball. Instead of placing the ball at the edge
 * of the other Entity, Ball is placed further away from the other Entity
 * according to how much Ball penetrated the other Entity during collision. This
 * way the game simulates where the Ball would be located between two timer
 * ticks.
 * 
 * @param other Entity in respect to which this entity's position is corrected.
 */
    @Override
    public void correctPosition(Positioned other)
    {
        double prevX = X();
        double prevY = Y();
        
        super.correctPosition(other);
        
        setPos(X() + (X() - prevX), Y() + (Y() - prevY));
    }
/**
 * This method is called every collision in order to do further actions
 * according to the collided entity's properties. Must be overriden if this
 * Entity does anything on collisions
 * <p>
 * Ball bounces off Bouncable Entities and kills Killable Entities on collision.
 * 
 * @param other Entity with which this Entity has collided.
 * @param notOnTheEdge true if other is truly inside this Entity, not simply
 *  on the edge.
 */
    @Override
    public void reactToCollision(Entity other, boolean notOnTheEdge)
    {
        if (Bouncable.class.isInstance(other))
            bounceOff((Bouncable)other);

        if (Killable.class.isInstance(other))
            kill((Killable)other);

    }
/**
 * This method is called every tick on the timer.
 * <p>
 * Ball moves and checks for collisions.
 */
    @Override
    public void update()
    {
        move();
        checkCollisions();
    }
/**
 * Places the Entity at a specified location. Must be overriden to be functional.
 * <p>
 * Ball is placed at a random location. It is preferrable to call
 * findNearestFreeSpot() after calling this method.
 */
    @Override
    public void defaultPosition()
    {
        setPos(Math.round(Math.random() * Const.width),
                Math.round(Math.random() * Const.height));
    }
/**
 * Contains instructions on how to represent this entity in the GameField.
 * <p>
 * Ball is a red oval.
 * 
 * @param g             Graphics data.
 * @param xMultiplier   Horizontal stretching based on windows width.
 * @param yMultiplier   Vertical stretching based on windows height.
 */
    @Override
    public void draw(Graphics g, double xMultiplier, double yMultiplier)
    {
        g.setColor(Color.RED);
        g.fillOval((int)(X() * xMultiplier), (int)(Y() * yMultiplier),
            (int)(getWidth() * xMultiplier), (int)(getHeight() * yMultiplier));
    }

}
