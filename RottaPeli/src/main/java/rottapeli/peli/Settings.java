package rottapeli.peli;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
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
        controls = new HashMap<>();
        
        defaultSettings();
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

    public void defaultSettings()
    {
        aspectRatio = false;
        windowWidth = Const.width * 4;
        windowHeight = Const.height * 4;
        language = Files.defaultLanguage;
        
        controls.put(KeyEvent.VK_RIGHT, Input.PLR1RIGHT);
        controls.put(KeyEvent.VK_DOWN, Input.PLR1DOWN);
        controls.put(KeyEvent.VK_LEFT, Input.PLR1LEFT);
        controls.put(KeyEvent.VK_UP, Input.PLR1UP);
        controls.put(KeyEvent.VK_P, Input.PAUSE);
    }
    
    public void loadSettings()
    {
        try
        {
            Scanner sc = new Scanner(Files.settingsFile);
            while (sc.hasNextLine())
                fetchSetting(sc.nextLine());
            sc.close();
        }
        catch (FileNotFoundException e)   {}
    }
    public void fetchSetting(String setting)
    {
        if (setting.startsWith("aspectratio"))
            setAspectRatio(fetchBoolean(setting));
        if (setting.startsWith("windowwidth"))
            windowWidth = fetchInteger(setting);
        if (setting.startsWith("windowheight"))
            windowHeight = fetchInteger(setting);
        if (setting.startsWith("language"))
            language = fetchString(setting);

        for (Input i : Input.values())
        {
            if (i == Input.NOTMAPPED)   continue;
            if (setting.startsWith(i.toString()))
                reMap(i, fetchInteger(setting));
        } 
    }
    public boolean fetchBoolean(String setting)
    {
        return setting.endsWith("1");
    }
    public int fetchInteger(String setting)
    {
        return Integer.parseInt(setting.split(" ")[1]);
    }
    public String fetchString(String setting)
    {
        return setting.split(" ")[1];
    }
    public void saveSettings()
    {
        try
        {
            FileWriter settings = new FileWriter(Files.settingsFile);
            settings.write("");
            
            writeSettings(settings);
            settings.close();
        }
        catch (IOException e)   {}
    }
    public void writeSettings(FileWriter settings) throws IOException
    {
        writeBoolean(settings, "aspectratio", hasAspectRatio());
        writeSetting(settings, "windowwidth", frame.getWidth());
        writeSetting(settings, "windowheight", frame.getHeight());
        writeSetting(settings, "language", language);

        for (Input i : Input.values())
        {
            if (i == Input.NOTMAPPED)   continue;
            writeSetting(settings, i.toString(), findKey(i));
        } 
    }
    public void writeBoolean(FileWriter writer, String var, boolean bool) throws IOException
    {
        writer.append(var);
        String value = bool ? "1" : "0";
        writer.append(" " + value);
        writer.append(System.getProperty("line.separator"));
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
        controls.remove(findKey(action));
        controls.put(button, action);
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
