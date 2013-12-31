
package rottapeli.resource;

import rottapeli.domain.superclasses.Positioned;

/**
 *
 * @author Pavel
 */
public class Tools {
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
    public static boolean pointInside(Positioned other, double x, double y, boolean notOnTheEdge)
    {
        if (notOnTheEdge)
            return  x > other.leftBorder() && x < other.rightBorder() &&
                y > other.topBorder() && y < other.bottomBorder();
        else
            return  x >= other.leftBorder() && x <= other.rightBorder() &&
                y >= other.topBorder() && y <= other.bottomBorder();
    }
    private static boolean occupySameSpace(Positioned a, Positioned b)
    {
        return a.leftBorder() == b.leftBorder() &&
                a.rightBorder() == b.rightBorder() &&
                a.topBorder() == b.topBorder() &&
                a.bottomBorder() == b.bottomBorder();
    }
    private static boolean isInsideInSameHorizontalSpace(Positioned a, Positioned b)
    {
        return  a.leftBorder() == b.leftBorder() &&
                a.rightBorder() == b.rightBorder() &&
                    (pointInside(a, (b.leftBorder() + b.rightBorder()) / 2, b.topBorder(), true) ||
                    pointInside(a, (b.leftBorder() + b.rightBorder()) / 2, b.bottomBorder(), true));
    }
    private static boolean isInsideInSameVerticalSpace(Positioned a, Positioned b)
    {
        return  a.topBorder() == b.topBorder() &&
                a.bottomBorder() == b.bottomBorder() &&
                    (pointInside(a, b.leftBorder(), (b.topBorder() + b.bottomBorder()) / 2, true) ||
                    pointInside(a, b.rightBorder(), (b.topBorder() + b.bottomBorder()) / 2, true));
    }
    
    public static double round(double a)
    {
        double i = Math.floor(a);
        double d = a - i;
        
        d *= Const.roundPrecision;
        d = Math.round(d);
        d /= Const.roundPrecision;
        
        return i + d;
    }
    
    public static double randomDiagonalDirection()
    {
        double randomNumber = Math.random();
        
        if (randomNumber < 0.25)    return Const.rightdown;
        if (randomNumber < 0.5)    return Const.leftdown;
        if (randomNumber < 0.75)    return Const.leftup;
        return Const.rightup;
    }
}
