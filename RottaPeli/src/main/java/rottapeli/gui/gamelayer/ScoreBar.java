
package rottapeli.gui.gamelayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import rottapeli.interfaces.Updatable;
import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public class ScoreBar extends JPanel implements Updatable {
    private RottaPeli rp;
    private JLabel scoreText;
    private JLabel livesText;
    private JLabel bonusText;
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
