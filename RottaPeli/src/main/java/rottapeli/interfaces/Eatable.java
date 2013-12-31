package rottapeli.interfaces;

/**
 * Entity that can be eaten by Rat.
 * @author Pavel
 */
public interface Eatable {
    public double leftBorder();
    public double rightBorder();
    public double topBorder();
    public double bottomBorder();
    public void getEaten();
}
