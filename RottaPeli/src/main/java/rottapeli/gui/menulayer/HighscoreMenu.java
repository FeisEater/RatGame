
package rottapeli.gui.menulayer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayDeque;
import java.util.Deque;
import javax.swing.JButton;
import javax.swing.JTextField;
import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public class HighscoreMenu extends Menu {
    private Deque<Integer> scoreEnterQueue;
    private JTextField nameField;
    private int currentScore;
    
    public HighscoreMenu(RottaPeli rp)
    {
        super(rp);
        scoreEnterQueue = new ArrayDeque<Integer>();
    }
    public void enterName()
    {
        for (int plr : rp.getScore().allAttendedPlayers())
        {
            int score = (int)rp.getScore().getPoints(plr);
            if (rp.getHighScore().isInTop(10, score))
                scoreEnterQueue.add(plr);
        }
        letNextPlayerEnterName();
    }
    public void letNextPlayerEnterName()
    {
        hideMenu();
        if (scoreEnterQueue.isEmpty())
            showMenu();
        else
            showMenuWithNameEntering(scoreEnterQueue.peek());
    }
    public void showMenuWithNameEntering(int plr)
    {
        currentScore = (int)rp.getScore().getPoints(plr);
        int playersRank = rp.getHighScore().getRankByScore(currentScore);
        
        getMenuLayer().setLayout(new GridLayout(0,3));
        getMenuLayer().emptyRow();
        
        for (int i = 0; i < 10; i++)
        {
            if (i == playersRank)
            {
                nameField = new JTextField("#highscoreachieved");
                nameField.setHorizontalAlignment(JTextField.CENTER);
                nameField.addActionListener(this);
                getMenuLayer().createComponent(nameField);
            }
            else
            {
                int j = (i > playersRank) ? i-1 : i+1;
                String score = "" + (i+1) + ") " + rp.getHighScore().getNameByRank(j) + " " + rp.getHighScore().getScoreByRank(j);
                getMenuLayer().createLabel(score);
            }
        }

        getMenuLayer().emptyRow();
        getMenuLayer().createButton("#accept");
        getMenuLayer().emptyRow();
    }
    @Override
    public void showMenu()
    {
        getMenuLayer().setLayout(new GridLayout(0,3));
        getMenuLayer().emptyRow();
        
        for (int i = 0; i < 10; i++)
        {
            String score = "" + (i+1) + ") " + rp.getHighScore().getNameByRank(i) + " " + rp.getHighScore().getScoreByRank(i);
            getMenuLayer().createLabel(score);
        }

        getMenuLayer().emptyRow();
        getMenuLayer().createButton("#back");
        getMenuLayer().emptyRow();
    }
    @Override
    public void hideMenu()
    {
        getMenuLayer().setLayout(new GridLayout(0,3, 0, 32));
        super.hideMenu();
    }
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if (scoreEnterQueue.isEmpty())
        {
            switch (ae.getActionCommand())
            {
                case "#back":
                    getMenuLayer().switchMenu(new MainMenu(rp));
                    break;
                default:
                    break;
            }
        }
        else
        {
            rp.getHighScore().insertScore(nameField.getText(), currentScore);
            scoreEnterQueue.poll();
            letNextPlayerEnterName();
        }
    }

}
