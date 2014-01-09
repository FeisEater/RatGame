
package rottapeli.resource;

/**
 * Defines constants used throughout the program.
 * @author Pavel
 */
public class Const {
    /** How many ticks Timer will provide per second. */
    public static final int fps = 60;

    /** Width of the playing field. */
    public static final int width = 192;
    /** Height of the playing field. */
    public static final int height = 148;
    
    /** Thickness from borders where Entities can't be placed randomly. */
    public static final double placementBlockerThickness = 16;
    
    /** Amount of time bonus that is decreased each tick. */
    public static final double bonusDecreasingRate = 1.667;
    /** Amount of lives that is given to each player initially. */
    public static final int initialLifeAmount = 100;
    /** Amount of time bonus that counter starts with. */
    public static final double initialBonus = 6000;
    /** Amount of score that awards an extra life. */
    public static final double extraLifeReward = 10000;
    /** Amount of points awarded for eating cheese. */
    public static final double cheesePoints = 100;
    
    /** Amount of cheese created for each stage. */
    public static final int cheeseamount = 15;
    /** Amount of balls in the first stage. */
    public static final int ballAmountInFirstLevel = 2;
    
    /** Speed of the Ball. */
    public static final double ballspeed = 1.0;
    /** Speed of the Rat. */
    public static final double ratspeed = 1.0;
    
    /** Width of the Ball. */
    public static final double ballwidth = 2.0;
    /** Height of the Ball. */
    public static final double ballheight = 2.0;
    /** Width of the Rat. */
    public static final double ratwidth = 2.0;
    /** Height of the Rat. */
    public static final double ratheight = 2.0;
    //public static final double tailthickness = 1.0;
    /** Width of the Cheese. */
    public static final double cheesewidth = 1.0;
    /** Height of the Cheese. */
    public static final double cheeseheight = 1.0;    

    /** Direction to the right in radians. */
    public static final double right = 0;
    /** Direction down in radians. */
    public static final double down = Math.PI / 2;
    /** Direction to the left in radians. */
    public static final double left = Math.PI;
    /** Direction up in radians. */
    public static final double up = -Math.PI / 2;
    /** Direction to the right and down in radians. */
    public static final double rightdown = Math.PI / 4;
    /** Direction to the left and down in radians. */
    public static final double leftdown = 3 * Math.PI / 4;
    /** Direction to the left and up in radians. */
    public static final double leftup = -3 * Math.PI / 4;
    /** Direction to the right and up in radians. */
    public static final double rightup = -Math.PI / 4;

    /** Amount of highest scores that will be shown in the High Score menu. */
    public static final int highScoreAmount = 10;
    
    /** Precision on which X- and Y-coordinates are rounded off.
    *   Smallest increment is this value ^ -1. 
    */
    public static final double roundPrecision = 100;
}
