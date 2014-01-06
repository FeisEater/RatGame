package rottapeli.peli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFrame;
import rottapeli.resource.Const;
import rottapeli.resource.Files;

/**
 *
 * @author Pavel
 */
public class Settings {
    private boolean aspectRatio;
    private int windowWidth;
    private int windowHeight;
    private String language;

    private JFrame frame;
    public Settings()
    {
        aspectRatio = false;
        windowWidth = Const.width * 4;
        windowHeight = Const.height * 4;
        language = Files.defaultLanguage;
        
        loadSettings();
    }
    public void setFrame(JFrame fr)  {frame = fr;}
    
    public boolean hasAspectRatio() {return aspectRatio;}
    public void setAspectRatio(boolean b)   {aspectRatio = b;}
    public void toggleAspectRatio() {aspectRatio = !aspectRatio;}
    
    public int getSavedWidth()  {return windowWidth;}
    public int getSavedHeight()  {return windowHeight;}
    
    public String getLanguage() {return language;}
    public void setLanguage(String lang)
    {
        language = "assets" + '\\' + lang;
    }

    public void loadSettings()
    {
        try
        {
            Scanner sc = new Scanner(Files.settingsFile);
            while (sc.hasNextLine())
            {
                String setting = sc.nextLine();
                if (setting.startsWith("aspectratio"))
                    setAspectRatio(setting.endsWith("1"));
                if (setting.startsWith("windowwidth"))
                    windowWidth = Integer.parseInt(setting.split(" ")[1]);
                if (setting.startsWith("windowheight"))
                    windowHeight = Integer.parseInt(setting.split(" ")[1]);
                if (setting.startsWith("language"))
                    language = setting.split(" ")[1];
            }
            sc.close();
        }
        catch (IOException e)   {
            System.out.println("no file, stupid!");
        }
    }
    public void saveSettings()
    {
        try
        {
            FileWriter settings = new FileWriter(Files.settingsFile);
            settings.write("aspectratio ");
            if (hasAspectRatio())
                settings.append("1");
            else    settings.append("0");
            settings.append(System.getProperty("line.separator"));
            
            settings.append("windowwidth ");
            settings.append("" + frame.getWidth());
            settings.append(System.getProperty("line.separator"));

            settings.append("windowheight ");
            settings.append("" + frame.getHeight());
            settings.append(System.getProperty("line.separator"));

            settings.append("language ");
            settings.append("" + language);
            settings.append(System.getProperty("line.separator"));

            settings.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
}
