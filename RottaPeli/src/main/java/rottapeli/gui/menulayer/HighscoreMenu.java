
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
 * The menu that is accessed from Main menu > High Score
 * @author Pavel
 */
public class HighscoreMenu extends Menu {
/** Queue of the player ID's that got to the high score. */
    private Deque<Integer> scoreEnterQueue;
/** Text field where the name of the player that got a high score is entered. */
    private JTextField nameField;
/** The score of the player that is currently entering her/his name. */
    private int currentScore;
/**
 * Constructor.
 * @param rp Game logic object.
 */
    public HighscoreMenu(RottaPeli rp)
    {
        super(rp);
        scoreEnterQueue = new ArrayDeque<>();
    }
/**
 * Check if any players hit the high score and should enter their name.
 */
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
/**
 * Moves the player queue up and lets the next player enter their name.
 * If no players left in the queue, shows default menu.
 */
    public void letNextPlayerEnterName()
    {
        hideMenu();
        if (scoreEnterQueue.isEmpty())
            showMenu();
        else
            showMenuWithNameEntering(scoreEnterQueue.peek());
    }
/**
 * Show menu with a text field for the player to enter their name.
 * @param plr ID of the player.
 */
    public void showMenuWithNameEntering(int plr)
    {
        currentScore = (int)rp.getScore().getPoints(plr);
        int playersRank = rp.getHighScore().getRankByScore(currentScore);
        createComponents(playersRank);
    }
/**
 * Creates a single label that shows the name and a score of the specified
 * rank in the high score. If player that was playing the game recently hit
 * that rank, create a text field for name entering instead.
 * @param rank Rank of the high score that should be shown.
 * @param playersRank Rank that the player hit.
 */
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
/**
 * Creates a text field for name entering.
 */
    public void createNameTextField()
    {
        nameField = new JTextField("");
        setNameFieldText();
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.addActionListener(this);
        getMenuLayer().createComponent(nameField);
    }
/**
 * Set a default text to the name entering text field.
 */
    public void setNameFieldText()
    {
        String firstPart = rp.getLanguage().translate("#highscoreachieved");
        String lastPart = rp.getLanguage().translate("#highscoreachieved2");
        nameField.setText(firstPart + " " + scoreEnterQueue.peek() + " " + lastPart);
    }
/**
 * Creates components for the menu to show.
 * @param playersRank Rank of the player in the high score. If no game was
 *      played recently, give a value that is out of bounds as a parameter.
 *      For example if only top ten scores are shown, put playersRank = 11
 *      to show the menu without a textfiels.
 */
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
/**
 * Performs all necessary actions to make this menu show up. Creates all
 * neccessary components. Show the menu without a text field for entering a name.
 */
    @Override
    public void showMenu()
    {
        createComponents(Const.highScoreAmount + 1);
    }
/**
 * Reacts to an actionEvent, a button press for instance. If score should be
 * entered, confirms the entered name regardless of the actual ActionEvent.
 * @param ae ActionEvent that triggered this method.
 */
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
/**
 * React to the Back button being pressed. Return to the Main menu.
 */
    public void backPressed()
    {
        getMenuLayer().switchMenu(new MainMenu(rp));
    }
/**
 * React to the name enter confirmation. Lets the next player enter their
 * name or shows the basic high score menu.
 */
    public void confirmNameEnter()
    {
        rp.getHighScore().insertScore(nameField.getText(), currentScore, true);
        scoreEnterQueue.poll();
        letNextPlayerEnterName();
    }
}
