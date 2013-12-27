
package rottapeli.resource;

import java.util.HashSet;
import java.util.Set;
import rottapeli.domain.superclasses.Entity;
import rottapeli.domain.superclasses.Positioned;

/**
 *
 * @author Pavel
 */
public class Tools {
    public static boolean isInside(Positioned a, Positioned b)
    {
        return  pointInside(a, b.leftBorder(), b.topBorder()) ||
                pointInside(a, b.rightBorder(), b.topBorder()) ||
                pointInside(a, b.leftBorder(), b.bottomBorder()) ||
                pointInside(a, b.rightBorder(), b.bottomBorder());
    }
    public static boolean pointInside(Positioned other, double x, double y)
    {
        return  x >= other.leftBorder() && x <= other.rightBorder() &&
                y >= other.topBorder() && y <= other.bottomBorder();
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
