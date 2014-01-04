package rottapeli.gui.gamelayer;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public class GameLayer extends JPanel {
    RottaPeli rp;
    public GameLayer(RottaPeli peli)
    {
        super(new BorderLayout());
        rp = peli;
        
        add(new GameField(rp));
        add(new ScoreBar(rp), BorderLayout.SOUTH);
    }
}
