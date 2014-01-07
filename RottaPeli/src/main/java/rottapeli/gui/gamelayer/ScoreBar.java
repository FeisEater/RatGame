
package rottapeli.gui.gamelayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import rottapeli.interfaces.Updatable;
import rottapeli.peli.RottaPeli;

/**
 * Score bar that shows player's current statistics.
 * @author Pavel
 */
public class ScoreBar extends JPanel implements Updatable {
/** Game logic object. */
    private RottaPeli rp;
/** Label that shows player's current score. */
    private JLabel scoreText;
/** Label that shows player's current amount of lives. */
    private JLabel livesText;
/** Label that shows time bonus left. */
    private JLabel bonusText;
/**
 * Constructor.
 * @param peli Game logic object.
 */
    public ScoreBar(RottaPeli peli)
    {
        super(new GridLayout(0,3));
        rp = peli;
        setBackground(Color.black);
        rp.getTimer().addUpdatable(this);

        scoreText = createLabel();
        livesText = createLabel();
        bonusText = createLabel();
    }
/**
 * Creates a label that is consistent with the visual representation.
 * @return pointer to the created label.
 */
    public JLabel createLabel()
    {
        JLabel label = new JLabel("");
        label.setForeground(Color.white);
        label.setFont(new Font(getFont().getName(), Font.BOLD, 32));
        add(label);
        return label;
    }
    private String getScoreText()
    {
        return rp.getLanguage().translate("#score") + 
                ": " + (int)rp.getScore().getPoints(1);
    }
    private String getLivesText()
    {
        return rp.getLanguage().translate("#lives") + 
                ": " + (int)(rp.getScore().getLives(1) - 1);
    }
    private String getBonusText()
    {
        return rp.getLanguage().translate("#bonus") + 
                ": " + (int)rp.getScore().getBonus();
    }
/**
 * Called on every tick on the timer. Scorebar is not updated if the game is over.
 */
    @Override
    public void update()
    {
        this.repaint();
        if (rp != null && rp.getScore() != null && rp.getLanguage() != null &&
                !rp.isGameOver())
        {
            scoreText.setText(getScoreText());
            livesText.setText(getLivesText());
            bonusText.setText(getBonusText());
        }
    }
}
