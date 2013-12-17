
package rottapeli.domain;

import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class Rat extends MoveableEntity {
    private boolean ismoving;
    public Rat(double x, double y)
    {
        super(x, y, 0, Const.ratspeed);
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
