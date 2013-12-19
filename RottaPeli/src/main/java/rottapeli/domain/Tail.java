
package rottapeli.domain;

import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Killable;

/**
 *
 * @author Pavel
 */
public class Tail extends Positioned implements Killable {
    private Killable owner;
    public Tail(double x, double y, double width, double height, Killable rat)
    {
        super(x, y, width, height);
        owner = rat;
    }
    public Killable getOwner()  {return owner;}
    @Override
    public void die()
    {
        owner.die();
    }
}
