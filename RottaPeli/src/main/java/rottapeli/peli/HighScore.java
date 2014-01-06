package rottapeli.peli;

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
        @Override
        public String toString()    {return name + ": " + points;}
    }
    private List<Score> scores;
    public HighScore()
    {
        scores = new ArrayList<Score>();
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
                String score = sc.nextLine();
                if (score.isEmpty())    continue;
                
                int points = Integer.parseInt(score.split(" ")[0]);
                String name = score.split(" ")[1];
                for (int i = 2; i < score.split(" ").length; i++)
                    name = name + " " + score.split(" ")[i];
                scores.add(new Score(name, points));
                if (oldpoints < points) sorted = false;
                oldpoints = points;
            }
            sc.close();
            if (!sorted)
            {
                System.out.println("sort was called");
                Collections.sort(scores);
            }
        }
        catch (IOException e)   {
            System.out.println("no file, stupid!");
        }
    }
    public void saveHighScore()
    {
        try
        {
            FileWriter writer = new FileWriter(Files.highscoreFile);
            writer.write("");

            for (int i = 0; i < scores.size(); i++)
            {
                writer.append("" + getScoreByRank(i));
                writer.append(" " + getNameByRank(i));
                writer.append(System.getProperty("line.separator"));
            }

            writer.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
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
