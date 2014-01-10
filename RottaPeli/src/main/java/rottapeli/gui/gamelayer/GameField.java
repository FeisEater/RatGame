package rottapeli.gui.gamelayer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;
import rottapeli.domain.superclasses.Positioned;
import rottapeli.interfaces.Updatable;
import rottapeli.peli.RottaPeli;
import rottapeli.resource.Const;

/**
 * Represents all Entities of the game.
 * @author Pavel
 */
public class GameField extends JPanel implements Updatable {
/** Game logic Object. */
    private RottaPeli rp;
/**
 * Constructor.
 * @param peli Game logic Object.
 */
    public GameField(RottaPeli peli)
    {
        rp = peli;
        rp.getTimer().addUpdatable(this);
    }
/**
 * Draws a background for the entire game field.
 * @param g Graphics data.
 * @param offsetX X position of the drawing field in respect to window's location.
 * @param offsetY Y position of the drawing field in respect to window's location.
 * @param widthMultiplier Horizontal stretching based on window's width.
 * @param heightMultiplier Vertical stretching based on window's height.
 */
    private void drawBackground(Graphics g, double offsetX, double offsetY, double widthMultiplier, double heightMultiplier)
    {
        g.setColor(Color.white);
        g.fillRect((int)offsetX, (int)offsetY, 
                (int)(widthMultiplier * Const.width), (int)(heightMultiplier * Const.height));
    }
/**
 * Calculates an x offset in case aspect ratio is on.
 * @param widthMultiplier Horizontal stretching based on window's width.
 * @param heightMultiplier Vertical stretching based on window's height.
 * @return X position from which gamefield will be drawn in respect to window.
 */
    private double offsetXforAspectRatio(double widthMultiplier, double heightMultiplier)
    {
        if (heightMultiplier < widthMultiplier)
            return Const.width * ((widthMultiplier - heightMultiplier) / 2);
        return 0;
    }
/**
 * Calculates an y offset in case aspect ratio is on.
 * @param widthMultiplier Horizontal stretching based on window's width.
 * @param heightMultiplier Vertical stretching based on window's height.
 * @return Y position from which gamefield will be drawn in respect to window.
 */
    private double offsetYforAspectRatio(double widthMultiplier, double heightMultiplier)
    {
        if (widthMultiplier < heightMultiplier)
            return Const.height * ((heightMultiplier - widthMultiplier) / 2);
        return 0;
    }
/**
 * Paints all Entities and scales them to the size of the window. Looks up
 * whether aspect ratio should be respected from the current settings.
 * @param g Graphics data Object.
 */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (rp == null && rp.getEntities() == null)
            return;

        double offsetX = 0;
        double offsetY = 0;
        double widthMultiplier = getWidth() / (double)Const.width;
        double heightMultiplier = getHeight() / (double)Const.height;
        
        if (rp.getSettings().hasAspectRatio())
        {
            offsetX = offsetXforAspectRatio(widthMultiplier, heightMultiplier);
            offsetY = offsetYforAspectRatio(widthMultiplier, heightMultiplier);

            widthMultiplier = Math.min(widthMultiplier, heightMultiplier);
            heightMultiplier = Math.min(widthMultiplier, heightMultiplier);
        }
        
        drawBackground(g, offsetX, offsetY, widthMultiplier, heightMultiplier);
        
        List<Positioned> drawables = rp.getEntities().getList(Positioned.class);
        for (Positioned p : drawables)
            p.draw(g, offsetX, offsetY, widthMultiplier, heightMultiplier);
    }
/**
 * This method is called every tick on the timer.
 */
    @Override
    public void update()
    {
        this.repaint();
    }
}
