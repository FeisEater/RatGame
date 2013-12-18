
package rottapeli.domain;

import rottapeli.peli.EntityList;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class Rat extends Moveable {
    private boolean ismoving;
    public Rat(double x, double y)
    {
        super(x, y, Const.ratwidth, Const.ratheight, 0, Const.ratspeed);
        ismoving = false;
    }
@Override
    public void setDirection(double dir)
    {
        super.setDirection(dir);
        ismoving = true;
    }
    
    @Override
    public void update()
    {
        if (ismoving)
            move();
    }
}
