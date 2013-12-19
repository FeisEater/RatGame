package rottapeli.domain;

import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class Cheese extends Positioned implements Bouncable {
    
    public Cheese(double x, double y)
    {
        super(x, y, Const.cheesewidth, Const.cheeseheight);
    }
}
