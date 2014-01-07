package rottapeli.peli;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import rottapeli.resource.Files;

/**
 * High score container. Object that reads an external highscore file and
 * stores the data within it. When the game is over, new scores are added
 * into the container and updated highscore data is overwritten to the
 * external file.
 * @author Pavel
 */
public class HighScore {
/**
 * Internal Score class that stores a name and score value. Scores
 * can be compared by the amount of points.
 */
    private class Score implements Comparable
    {
/** Score gotten from the game. */
        int points;
/** The name of the player that achieved the score. */
        String name;
/**
 * Constructor.
 * @param n The name of the player that achieved the score.
 * @param p Score gotten from the game.
 */
        public Score(String n, int p)
        {
            points = p;
            name = n;
        }
/**
 * Switches data between this Score and other Score.
 * @param other Another score.
 */
        public void switchPointers(Score other)
        {
            int bufferPoints = this.points;
            String bufferName = this.name;
            this.points = other.points;
            this.name = other.name;
            other.points = bufferPoints;
            other.name = bufferName;
        }
        @Override
        public int compareTo(Object o)
        {
            if (o.getClass() != Score.class)  return 0;
            Score other = (Score)o;
            return other.points - this.points;
        }
    }
/** Score container. */
    private List<Score> scores;
/**
 * Constructor. Loads the high score data.
 */
    public HighScore()
    {
        scores = new ArrayList<>();
        loadHighScore();
    }
/**
 * Loads and stores the highscore data. Stored highscore will be in order
 * from highest to lowest score.
 */
    public void loadHighScore()
    {
        try
        {
            Scanner sc = new Scanner(Files.highscoreFile);
            int oldpoints = Integer.MAX_VALUE;
            boolean sorted = true;
            while (sc.hasNextLine())
            {
                String string = sc.nextLine();
                if (string.isEmpty())    continue;

                int points = retrieveScore(string);
                if (oldpoints < points) sorted = false;
                oldpoints = points;
            }
            sc.close();
            if (!sorted)    Collections.sort(scores);
        }
        catch (FileNotFoundException e)   {}
    }
/**
 * Translates a line of string into the score data that contains
 * a name and score value.
 * @param string A line of string found from the external file.
 * @return Score value.
 */
    public int retrieveScore(String string)
    {
        String[] splitString = string.split(" ");
        int points = getPoints(splitString);

        scores.add(new Score(getName(splitString), points));
        return points;
    }
    private String getName(String[] splitString)
    {
        if (splitString.length <= 1)    return "";
        String name = splitString[1];
        for (int i = 2; i < splitString.length; i++)
            name = name + " " + splitString[i];
        return name;
    }
    private int getPoints(String[] splitString)
    {
        if (splitString.length <= 0)    return 0;
        return Integer.parseInt(splitString[0]);
    }
/**
 * Saves the updated highscore data.
 */
    public void saveHighScore()
    {
        try
        {
            FileWriter writer = new FileWriter(Files.highscoreFile);
            writer.write("");

            writeScores(writer);
            writer.close();
        }
        catch (IOException e)   {}
    }
/**
 * Writes all of the scores contained in this object into an external data.
 * @param writer Filewriter object.
 * @throws IOException 
 */
    public void writeScores(FileWriter writer) throws IOException
    {
        for (int i = 0; i < scores.size(); i++)
        {
            writer.append("" + getScoreByRank(i));
            writer.append(" " + getNameByRank(i));
            writer.append(System.getProperty("line.separator"));
        }
    }
/**
 * Retrieves the name of the player that has a high score at a specified rank.
 * @param rank Specified rank of the high score.
 * @return The name of the player at a specified rank.
 */
    public String getNameByRank(int rank)
    {
        if (rank < 0 || rank >= scores.size())  return "";
        return scores.get(rank).name;
    }
/**
 * Retrieves the score at a specified rank.
 * @param rank Specified rank of the high score.
 * @return The score value at a specified rank.
 */
    public int getScoreByRank(int rank)
    {
        if (rank < 0 || rank >= scores.size())  return 0;
        return scores.get(rank).points;
    }
/**
 * Retrieves a rank that given score value is entitled for.
 * @param points Specified score value.
 * @return A rank that given score value is entitled for.
 */
    public int getRankByScore(int points)
    {
        for (int i = 0; i < scores.size(); i++)
        {
            if (points > scores.get(i).points)
                return i;
        }
        return scores.size();
    }
/**
 * Inserts a score into the high score while not breaking the order of the high score.
 * @param name Name of the player that achieved the score.
 * @param points Score value that is to be inserted.
 */
    public void insertScore(String name, int points)
    {
        if (name.isEmpty()) name = "Anonymous";
        Score score = new Score(name, points);
        
        for (int i = getRankByScore(points); i < scores.size(); i++)
            score.switchPointers(scores.get(i));
        
        scores.add(score);
        saveHighScore();
    }
/**
 * Checks if the specified score value is among the best scores.
 * @param topRanks Lowest rank to be considered. To check if points
 *                  are among top ten scores, set topRanks = 10;
 * @param points Score value that is checked.
 * @return true if score is among best scores.
 */
    public boolean isInTop(int topRanks, int points)
    {
        if (topRanks >= scores.size())  return true;
        return points > scores.get(topRanks-1).points;
    }
}
