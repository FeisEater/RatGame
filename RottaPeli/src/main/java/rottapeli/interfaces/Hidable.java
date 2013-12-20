package rottapeli.interfaces;

import rottapeli.domain.superclasses.Moveable;
import rottapeli.resource.FieldBorder;

/**
 *
 * @author Pavel
 */
public interface Hidable {
    public double leftBorder();
    public double rightBorder();
    public double topBorder();
    public double bottomBorder();
    public FieldBorder hide(Moveable other);
}
