package rottapeli.interfaces;

/**
 * Object that can be controlled through player input.
 * @author Pavel
 */
public interface Controllable {
    public int playerID();
    public void moveTo(double dir);
}
