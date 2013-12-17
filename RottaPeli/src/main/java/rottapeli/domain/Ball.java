package rottapeli.domain;

import rottapeli.resource.Const;
/**
 *
 * @author Pavel
 */
public class Ball extends Moveable implements Bouncable {

    public Ball(double x, double y, double ang)
    {
        super(x, y, ang, Const.ballspeed);
    }
    @Override
    public void update()
    {
        move();
    }
}
