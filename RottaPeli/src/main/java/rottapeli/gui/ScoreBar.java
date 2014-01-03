
package rottapeli.gui;

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
        setBackground(Color.WHITE);
        rp.getTimer().addUpdatable(this);

        scoreText = new JLabel("");
        createLabel(scoreText);
        livesText = new JLabel("");
        createLabel(livesText);
        bonusText = new JLabel("");
        createLabel(bonusText);
    }
    public void createLabel(JLabel label)
    {
        label.setFont(new Font(getFont().getName(), Font.BOLD, 32));
        add(label);
    }
    @Override
    public void update()
    {
        this.repaint();
        if (rp != null && rp.getScore() != null && !rp.isGameOver())
        {
            scoreText.setText("Score: " + (int)rp.getScore().getPoints(1));
            livesText.setText("Lives: " + (int)rp.getScore().getLives(1));
            bonusText.setText("Bonus: " + (int)rp.getScore().getBonus());
        }
    }
}
