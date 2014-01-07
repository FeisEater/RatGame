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
 *
 * @author Pavel
 */
public class HighScore {
    private class Score implements Comparable
    {
        int points;
        String name;
        public Score(String n, int p)
        {
            points = p;
            name = n;
        }
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
    private List<Score> scores;
    public HighScore()
    {
        scores = new ArrayList<>();
        loadHighScore();
    }
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
    public void writeScores(FileWriter writer) throws IOException
    {
        for (int i = 0; i < scores.size(); i++)
        {
            writer.append("" + getScoreByRank(i));
            writer.append(" " + getNameByRank(i));
            writer.append(System.getProperty("line.separator"));
        }
    }
    public String getNameByRank(int rank)
    {
        if (rank < 0 || rank >= scores.size())  return "";
        return scores.get(rank).name;
    }
    public int getScoreByRank(int rank)
    {
        if (rank < 0 || rank >= scores.size())  return 0;
        return scores.get(rank).points;
    }
    public int getRankByScore(int points)
    {
        for (int i = 0; i < scores.size(); i++)
        {
            if (points > scores.get(i).points)
                return i;
        }
        return scores.size();
    }
    public void insertScore(String name, int points)
    {
        if (name.isEmpty()) name = "Anonymous";
        Score score = new Score(name, points);
        
        for (int i = getRankByScore(points); i < scores.size(); i++)
            score.switchPointers(scores.get(i));
        
        scores.add(score);
        saveHighScore();
    }
    public boolean isInTop(int topRanks, int points)
    {
        if (topRanks >= scores.size())  return true;
        return points > scores.get(topRanks-1).points;
    }
}
