
package rottapeli.interfaces;

/**
 * Entity that can be killed by hazards.
 * @author Pavel
 */
public interface Killable {
    public double leftBorder();
    public double rightBorder();
    public double topBorder();
    public double bottomBorder();
    
    public void die();
}
