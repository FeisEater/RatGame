
package rottapeli.domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import rottapeli.domain.superclasses.Entity;
import rottapeli.domain.superclasses.Moveable;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Controllable;
import rottapeli.interfaces.Eatable;
import rottapeli.interfaces.Hidable;
import rottapeli.interfaces.Killable;
import rottapeli.resource.ApproachFrom;
import rottapeli.resource.Const;
import rottapeli.resource.Tools;

/**
 * Main character controlled by player 1.
 * <p>
 * Entity that is controllable by player 1. Once receiving a command to move
 * it starts moving at a constand speed at a given direction. While moving Rat
 * creates a track of Tails behind itself. Rat eats Eatable Entities. Rat
 * stops moving when it hits a Hidable Entity, destroying its Tails. Rat dies
 * when it's hit by a hazardous Entity, for example a Ball or its own Tail.
 * @author Pavel
 */
public class Rat extends Moveable implements Killable, Controllable {
/** true if Rat is currently moving. */
    private boolean ismoving;
/** true if Rat has nothing stopping it from creating a Tail segment. */
    private boolean canCreateTail;
/** Most recently created Tail segment. Colliding with it won't cause
    the Rat to die. */
    private Tail lastTail;
/**
 * Constructor
 * <p>
 * Calling an Entity constructor without parameters will place the Entity
 * in defaultPosition.
 */
    public Rat()
    {
        super(Const.ratwidth, Const.ratheight, 0, Const.ratspeed);
        ismoving = false;
        lastTail = null;
    }
/**
 * Constructor
 * 
 * @param x     X position where Rat is created.
 * @param y     Y position where Rat is created.
 */
    public Rat(double x, double y)
    {
        this();
        setPos(x,y);
    }
/**
 * Eats another Eatable Entity.
 * <p>
 * Calls getEaten() method to the Entity given as a parameter. Rat also calls
 * gameLogic to award points for this endeavour.
 * @param other Eatable Entity that is to be eaten.
 */
    public void eat(Eatable other)
    {
        other.getEaten();
        if (hasEntities() && hasGameLogic())
           gameLogic().playerAteCheese(playerID());
    }
/**
 * Hides in a Hidable object.
 * <p>
 * Rat removes its track of Tails, faces away from the Hidable Entity and
 * stops moving.
 * @param other Hidable entity where Rat hides in.
 */
    public void hide(Hidable other)
    {
        removeTails();
        correctPosition((Positioned) other);
        faceAwayFrom(other);
        stopMoving();
    }
/**
 * Faces away from the object where Rat hides in.
 * <p>
 * Judging by the way Rat approached Hidable Entity sets Rats new direction.
 * @param other Hidable entity where Rat hides in.
 */
    public void faceAwayFrom(Hidable other)
    {        
        if (collisionType((Positioned) other)[0] == ApproachFrom.LEFT)
            setDirection(Const.left);
        if (collisionType((Positioned) other)[0] == ApproachFrom.RIGHT)
            setDirection(Const.right);
        if (collisionType((Positioned) other)[1] == ApproachFrom.ABOVE)
            setDirection(Const.up);
        if (collisionType((Positioned) other)[1] == ApproachFrom.BELOW)
            setDirection(Const.down);
    }
/**
 * Reacts to the collision with Tail.
 * <p>
 * If tail was created by this Rat calls die() method on the Tail unless Tail is
 * last created Tail. In that case don't create Tail this tick on the timer.
 * @param tail Tail with which Rat collided.
 */
    public void examineTail(Tail tail)
    {
        if (tail.getOwner() == this)
        {
            canCreateTail = false;
            if (lastTail != tail && lastTail != null)
                tail.die();
        }
    }
/**
 * Creates a Tail segment as a part of the Tail track.
 */
    public void createTail()
    {
        if (!canCreateTail) return;
        
        double dir = getDirection();

        double tailx = (dir == Const.left) ? X() + getWidth() : oldX();
        double taily = (dir == Const.up) ? Y() + getHeight() : oldY();
//Each tail segment is ratspeed in length because it is created every timer tick.
//That way each gap that is created when rat moves is 'patched' by a tail segment.
//Tail thickness is either rat width or height.
        double tailwidth = (dir == Const.right || dir == Const.left) ?
                Const.ratspeed : Const.ratwidth;
        double tailheight = (dir == Const.up || dir == Const.down) ?
                Const.ratspeed : Const.ratheight;

        Tail newtail = new Tail(tailx, taily, tailwidth, tailheight, this);
        getEntities().addEntity(newtail);
        lastTail = newtail;
    }
/**
 * Removes all Tail segments created by this Rat.
 */
    public void removeTails()
    {
        if (getEntities() == null)
            return;

        List toberemoved = new ArrayList();
        List<Tail> tails = getEntities().getList(Tail.class);
        for (Tail other : tails)
        {
            if (other.getOwner() == this)
                toberemoved.add(other);
        }
        getEntities().removeAll(toberemoved);
        lastTail = null;
    }
/**
 * Stops Rat from moving and tells ScoreKeeper to reset player 1's combo.
 */
    public void stopMoving()
    {
        ismoving = false;
        canCreateTail = false;
        if (hasEntities() && hasGameLogic())
            gameLogic().getScore().resetCombo(playerID());
    }
/**
 * Places the Entity at a specified location. Must be overriden to be functional.
 * <p>
 * Rat is placed in the middle of the upper ledge of the playing field.
 * This method also destroys tails, makes Rat face downwards and stops it from
 * moving.
 */
@Override
    public void defaultPosition()
    {
        removeTails();
        setPos(Math.round(Const.width / 2), 0);
        setDirection(Const.down);
        stopMoving();
    }
/**
 * This method is called every collision in order to do further actions
 * according to the collided entity's properties. Must be overriden if this
 * Entity does anything on collisions
 * <p>
 * Rat hides in Hidable Entities, eats Eatable Entities and dies from Tails.
 * 
 * @param other Entity with which this Entity has collided.
 * @param notOnTheEdge true if other is truly inside this Entity, not simply
 *  on the edge.
 */
@Override
    public void reactToCollision(Entity other, boolean notOnTheEdge)
    {
        if (Hidable.class.isInstance(other))
            hide((Hidable)other);

        if (Eatable.class.isInstance(other) && notOnTheEdge)
            eat((Eatable)other);

        if (Tail.class.isInstance(other) && notOnTheEdge)
            examineTail((Tail)other);

    }
/**
 * Causes Killable Entity to die.
 * <p>
 * Rat is relocated to the defaultPosition() and signals gameLogic to lose a
 * life for player 1.
 */
@Override
    public void die()
    {
        defaultPosition();
        if (hasEntities() && hasGameLogic())
            gameLogic().playerDied(playerID());
    }
/**
 * This method is called every tick on the timer.
 * <p>
 * Rat moves, checks for collisions and creates Tails.
 */
@Override
    public void update()
    {
        if (ismoving)
        {            
            move();
            
            canCreateTail = true;
            checkCollisions();
            
            createTail();
        }
    }
/**
 * Contains instructions on how to represent this entity in the GameField.
 * <p>
 * Rat is a black rectangle.
 * 
 * @param g             Graphics data.
 * @param offsetX       X position of the drawing field in respect to window's location.
 * @param offsetY       Y position of the drawing field in respect to window's location.
 * @param xMultiplier   Horizontal stretching based on window's width.
 * @param yMultiplier   Vertical stretching based on window's height.
 */
@Override
    public void draw(Graphics g, double offsetX, double offsetY, double xMultiplier, double yMultiplier)
    {
        /*BufferedImage image = null;
        try {
            image = ImageIO.read(new File("assets/rat.png"));
        }   catch (IOException e) {
            System.out.println("can't find image");
        }
        g.drawImage(image, drawX(offsetX, xMultiplier), drawY(offsetY, yMultiplier),
                drawWidth(xMultiplier), drawHeight(yMultiplier), null);
        */
        g.setColor(Color.BLACK);
        g.fill3DRect(drawX(offsetX, xMultiplier), drawY(offsetY, yMultiplier),
            drawWidth(xMultiplier), drawHeight(yMultiplier), true);
    }
/**
 * Signals Controlable Entity to move to the specified direction.
 * @param dir Direction in radians.
 */
@Override
    public void moveTo(double dir)
    {
        if (ismoving && newDirectionIsOppositeToCurrentDirection(dir))
            return;
        
        setDirection(dir);
        ismoving = true;
    }
    private boolean newDirectionIsOppositeToCurrentDirection(double dir)
    {
        return Tools.round(Math.abs(dir - getDirection())) == Tools.round(Math.PI);
    }
/**
 * @return Which player ID can control this Entity.
 */
@Override
    public int playerID()   {return 1;}
}
