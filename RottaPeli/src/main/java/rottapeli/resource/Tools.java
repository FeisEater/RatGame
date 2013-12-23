
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
}
