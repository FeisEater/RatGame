package rottapeli.peli;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JFrame;
import rottapeli.resource.Const;
import rottapeli.resource.Files;
import rottapeli.resource.Input;

/**
 *
 * @author Pavel
 */
public class Settings {
    private boolean aspectRatio;
    private int windowWidth;
    private int windowHeight;
    private String language;
    private Map<Integer, Input> controls;

    private JFrame frame;
    public Settings()
    {
        controls = new HashMap<Integer, Input>();
        
        aspectRatio = false;
        windowWidth = Const.width * 4;
        windowHeight = Const.height * 4;
        language = Files.defaultLanguage;
        
        controls.put(KeyEvent.VK_RIGHT, Input.PLR1RIGHT);
        controls.put(KeyEvent.VK_DOWN, Input.PLR1DOWN);
        controls.put(KeyEvent.VK_LEFT, Input.PLR1LEFT);
        controls.put(KeyEvent.VK_UP, Input.PLR1UP);
        controls.put(KeyEvent.VK_P, Input.PAUSE);
        
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
                
                if (setting.startsWith("plr1right"))
                    reMap(Input.PLR1RIGHT, Integer.parseInt(setting.split(" ")[1]));
                if (setting.startsWith("plr1left"))
                    reMap(Input.PLR1LEFT, Integer.parseInt(setting.split(" ")[1]));
                if (setting.startsWith("plr1up"))
                    reMap(Input.PLR1UP, Integer.parseInt(setting.split(" ")[1]));
                if (setting.startsWith("plr1down"))
                    reMap(Input.PLR1DOWN, Integer.parseInt(setting.split(" ")[1]));
                if (setting.startsWith("pausebutton"))
                    reMap(Input.PAUSE, Integer.parseInt(setting.split(" ")[1]));
 
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
            
            writeSetting(settings, "windowwidth", frame.getWidth());
            writeSetting(settings, "windowheight", frame.getHeight());
            writeSetting(settings, "language", language);

            writeSetting(settings, "plr1right", findKey(Input.PLR1RIGHT));
            writeSetting(settings, "plr1left", findKey(Input.PLR1LEFT));
            writeSetting(settings, "plr1up", findKey(Input.PLR1UP));
            writeSetting(settings, "plr1down", findKey(Input.PLR1DOWN));
            writeSetting(settings, "pausebutton", findKey(Input.PAUSE));
            
            settings.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
    public void writeSetting(FileWriter writer, String var, Object value) throws IOException
    {
        writer.append(var);
        writer.append(" " + value.toString());
        writer.append(System.getProperty("line.separator"));
    }
    public Input getControl(int key)
    {
        if (!controls.containsKey(key)) return Input.NOTMAPPED;
        return controls.get(key);
    }
    public void reMap(Input action, int button)
    {
        for (int key : controls.keySet())
        {
            if (controls.get(key) == action)
            {
                controls.remove(key);
                controls.put(button, action);
                return;
            }
        }
    }
    public int findKey(Input action)
    {
        for (int key : controls.keySet())
        {
            if (controls.get(key) == action)
                return key;
        }
        return -1;
    }
}
