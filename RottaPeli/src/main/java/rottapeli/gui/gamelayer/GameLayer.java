package rottapeli.gui.gamelayer;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import rottapeli.peli.RottaPeli;

/**
 * GUI layer that contains game related representation.
 * @author Pavel
 */
public class GameLayer extends JPanel {
/**
 * Constructor. Creates a gamefield and a score bar below it.
 * @param rp Game logic object.
 */
    public GameLayer(RottaPeli rp)
    {
        super(new BorderLayout());
        
        add(new GameField(rp));
        add(new ScoreBar(rp), BorderLayout.SOUTH);
    }
}
