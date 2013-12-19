package rottapeli.domain;

import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Bouncable;
import rottapeli.interfaces.Eatable;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class Cheese extends Positioned implements Bouncable, Eatable {
    
    public Cheese(double x, double y)
    {
        super(x, y, Const.cheesewidth, Const.cheeseheight);
    }
    @Override
    public void getEaten()
    {
        getEntities().removeEntity(this);
    }
}
