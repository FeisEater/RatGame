
package rottapeli.resource;

import rottapeli.domain.superclasses.Positioned;

/**
 * Provides miscellaneous algorithms that are autonomous from any class.
 * @author Pavel
 */
public class Tools {
/**
 * Checks if Positioned Entity b is inside Positioned Entity a. Used for
 * collision checking.
 * @param a Certain Positioned Entity.
 * @param b Certain Positioned Entity.
 * @param notOnTheEdge Set to true if method should return false even if two
 *                      Entites are touching sides. That way true is returned
 *                      if Entities are truly inside each other. All cases
 *                      where isInside(a,b,false) == true will return
 *                      isInside(a,b,true) == true. However this is not the case
 *                      vice versa.
 * @return True if b is inside a.
 */
    public static boolean isInside(Positioned a, Positioned b, boolean notOnTheEdge)
    {
        if (notOnTheEdge)
        {
            if (occupySameSpace(a, b))
                return true;

            if (isInsideInSameHorizontalSpace(a, b))
                return true;
            
            if (isInsideInSameVerticalSpace(a, b))
                return true;
        }
        
        return  pointInside(a, b.leftBorder(), b.topBorder(), notOnTheEdge) ||
                pointInside(a, b.rightBorder(), b.topBorder(), notOnTheEdge) ||
                pointInside(a, b.leftBorder(), b.bottomBorder(), notOnTheEdge) ||
                pointInside(a, b.rightBorder(), b.bottomBorder(), notOnTheEdge);
    }
/**
 * Checks if certain coordinates are inside certain Positioned Entity.
 * @param other Positioned Entity.
 * @param x X coordinate.
 * @param y Y coordinate.
 * @param notOnTheEdge If true, method will return false even if coordinates
 *                      are on the side of the Positioned Entity.
 * @return True if coordinates are inside a Positioned Entity.
 */
    public static boolean pointInside(Positioned other, double x, double y, boolean notOnTheEdge)
    {
        if (notOnTheEdge)
            return  x > other.leftBorder() && x < other.rightBorder() &&
                y > other.topBorder() && y < other.bottomBorder();
        else
            return  x >= other.leftBorder() && x <= other.rightBorder() &&
                y >= other.topBorder() && y <= other.bottomBorder();
    }
/**
 * 
 * @param a Certain Positioned Entity.
 * @param b Certain Positioned Entity.
 * @return True if both Entities have exact same position, width and height.
 */
    private static boolean occupySameSpace(Positioned a, Positioned b)
    {
        return a.leftBorder() == b.leftBorder() &&
                a.rightBorder() == b.rightBorder() &&
                a.topBorder() == b.topBorder() &&
                a.bottomBorder() == b.bottomBorder();
    }
/**
 * 
 * @param a Certain Positioned Entity.
 * @param b Certain Positioned Entity.
 * @return True if Entities are inside each other while on the same X-axis.
 */
    private static boolean isInsideInSameHorizontalSpace(Positioned a, Positioned b)
    {
        return  a.leftBorder() == b.leftBorder() &&
                a.rightBorder() == b.rightBorder() &&
                    (pointInside(a, (b.leftBorder() + b.rightBorder()) / 2, b.topBorder(), true) ||
                    pointInside(a, (b.leftBorder() + b.rightBorder()) / 2, b.bottomBorder(), true));
    }
/**
 * 
 * @param a Certain Positioned Entity.
 * @param b Certain Positioned Entity.
 * @return True if Entities are inside each other while on the same Y-axis.
 */
    private static boolean isInsideInSameVerticalSpace(Positioned a, Positioned b)
    {
        return  a.topBorder() == b.topBorder() &&
                a.bottomBorder() == b.bottomBorder() &&
                    (pointInside(a, b.leftBorder(), (b.topBorder() + b.bottomBorder()) / 2, true) ||
                    pointInside(a, b.rightBorder(), (b.topBorder() + b.bottomBorder()) / 2, true));
    }
/**
 * Rounds the value to a certain precision. Used for rounding off coordinates.
 * @param a Value that needs to be round off.
 * @return Rounded off value of a.
 */
    public static double round(double a)
    {
        double i = Math.floor(a);
        double d = a - i;
        
        d *= Const.roundPrecision;
        d = Math.round(d);
        d /= Const.roundPrecision;
        
        return i + d;
    }
/**
 * Chooses one of four diagonal directions randomly.
 * @return Randomly chosen diagonal direction.
 */
    public static double randomDiagonalDirection()
    {
        double randomNumber = Math.random();
        
        if (randomNumber < 0.25)    return Const.rightdown;
        if (randomNumber < 0.5)    return Const.leftdown;
        if (randomNumber < 0.75)    return Const.leftup;
        return Const.rightup;
    }
}
