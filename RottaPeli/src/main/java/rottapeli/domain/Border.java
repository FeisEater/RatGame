package rottapeli.domain;

import rottapeli.domain.superclasses.Moveable;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Bouncable;
import rottapeli.interfaces.Hidable;
import rottapeli.resource.FieldBorder;

/**
 *
 * @author Pavel
 */
public class Border extends Positioned implements Bouncable, Hidable {
    private FieldBorder type;
    public Border(double x, double y, double width, double height, FieldBorder type)
    {
        super(x,y,width,height);
        this.type = type;
    }
    @Override
    public FieldBorder hide(Moveable other)
    {
        return type;
    }
}
