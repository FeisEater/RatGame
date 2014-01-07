
package rottapeli.gui.menulayer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayDeque;
import java.util.Deque;
import javax.swing.JButton;
import javax.swing.JTextField;
import rottapeli.peli.RottaPeli;
import rottapeli.resource.Const;

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
            if (rp.getHighScore().isInTop(Const.highScoreAmount, score))
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
        createComponents(playersRank);
    }
    public void showScore(int rank, int playersRank)
    {
        if (rank == playersRank)
            createNameTextField();
        else
        {
            int j = (rank > playersRank) ? rank-1 : rank;
            String score = "" + (rank+1) + ") " + 
                    rp.getHighScore().getNameByRank(j) + " " + 
                    rp.getHighScore().getScoreByRank(j);
            getMenuLayer().createLabel(score);
        }
    }
    public void createNameTextField()
    {
        nameField = new JTextField("#highscoreachieved");
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.addActionListener(this);
        getMenuLayer().createComponent(nameField);
    }
    public void createComponents(int playersRank)
    {
        getMenuLayer().setLayout(new GridLayout(0,3));
        getMenuLayer().emptyRow();
        
        for (int i = 0; i < Const.highScoreAmount; i++)
            showScore(i, playersRank);

        getMenuLayer().emptyRow();
        if (playersRank > Const.highScoreAmount)
            getMenuLayer().createButton("#back");
        else
            getMenuLayer().createButton("#confirm");
        getMenuLayer().emptyRow();
    }
    @Override
    public void showMenu()
    {
        createComponents(Const.highScoreAmount + 1);
    }
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if (scoreEnterQueue.isEmpty())
        {
            switch (ae.getActionCommand())
            {
                case "#back":
                    backPressed();
                    break;
                default:
                    break;
            }
        }
        else    confirmNameEnter();
    }
    public void backPressed()
    {
        getMenuLayer().switchMenu(new MainMenu(rp));
    }
    public void confirmNameEnter()
    {
        rp.getHighScore().insertScore(nameField.getText(), currentScore);
        scoreEnterQueue.poll();
        letNextPlayerEnterName();
    }
}
