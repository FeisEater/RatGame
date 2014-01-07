package rottapeli.gui.gamelayer;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public class GameLayer extends JPanel {
    public GameLayer(RottaPeli rp)
    {
        super(new BorderLayout());
        
        add(new GameField(rp));
        add(new ScoreBar(rp), BorderLayout.SOUTH);
    }
}
