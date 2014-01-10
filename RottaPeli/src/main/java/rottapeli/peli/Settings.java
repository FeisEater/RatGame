package rottapeli.peli;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import rottapeli.resource.Const;
import rottapeli.resource.Files;
import rottapeli.resource.Input;

/**
 * Settings manager that can be reconfigured in the program. Settings are
 * loaded from an external file and changed settings are saved to the
 * external file.
 * @author Pavel
 */
public class Settings {
/** true if game representation should respect aspect ratio. */
    private boolean aspectRatio;
/** Width of the window last retrieved from the settings file. */
    private int windowWidth;
/** Height of the window last retrieved from the settings file. */
    private int windowHeight;
/** Name of the language that is used in the program. */
    private String language;
/** Mapped controls. */
    private Map<Integer, Input> controls;
/** Game logic object. */
    private RottaPeli rp;
/**
 * Constructor. Loads default hard-coded settings then loads settings
 * from an external file.
 * @param peli Game logic object.
 */
    public Settings(RottaPeli peli)
    {
        controls = new HashMap<>();
        rp = peli;

        defaultSettings();
        loadSettings();
    }    
    public boolean hasAspectRatio() {return aspectRatio;}
    public void setAspectRatio(boolean b)   {aspectRatio = b;}
    public void toggleAspectRatio() {aspectRatio = !aspectRatio;}
    
    public int getSavedWidth()  {return windowWidth;}
    public int getSavedHeight()  {return windowHeight;}
    
    public String getLanguage() {return language;}
/**
 * Sets a language for the program
 * @param lang name of the language file ('english.txt')
 */
    public void setLanguage(String lang)
    {
        language = Files.languagePath + "/" + lang;
        if (rp != null && rp.getFrame() != null)
            rp.getFrame().setTitle(rp.getLanguage().translate("#title"));
    }
/**
 * Hard-coded settings that will be in effect if external file can't be loaded.
 */
    public void defaultSettings()
    {
        aspectRatio = false;
        windowWidth = Const.width * 4;
        windowHeight = Const.height * 4;
        language = Files.defaultLanguage;
        
        reMap(Input.PLR1RIGHT, KeyEvent.VK_RIGHT);
        reMap(Input.PLR1LEFT, KeyEvent.VK_LEFT);
        reMap(Input.PLR1UP, KeyEvent.VK_UP);
        reMap(Input.PLR1DOWN, KeyEvent.VK_DOWN);
        reMap(Input.PAUSE, KeyEvent.VK_P);
    }
/**
 * Loads the settings from an external file.
 */
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
/**
 * Changes a setting based on a line of String fetched from an external file.
 * @param setting Line of String that contains a certain setting data.
 */
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
/**
 * Converts an external data to a boolean.
 * @param setting Line of String that contains a certain setting data.
 * @return true if it ends in '1'
 */
    public boolean fetchBoolean(String setting)
    {
        return setting.endsWith("1");
    }
/**
 * Converts an external data to an integer.
 * @param setting Line of String that contains a certain setting data.
 * @return integer value.
 */
    public int fetchInteger(String setting)
    {
        return Integer.parseInt(setting.split(" ")[1]);
    }
/**
 * Converts an external data to a string.
 * @param setting Line of String that contains a certain setting data.
 * @return string value.
 */
    public String fetchString(String setting)
    {
        return setting.split(" ")[1];
    }
/**
 * Saves possibly changed settings into an external file.
 */
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
/**
 * Overwrites all settings that are possibly changed.
 * @param settings Filewriter object.
 * @throws IOException 
 */
    private void writeSettings(FileWriter settings) throws IOException
    {
        writeBoolean(settings, "aspectratio", hasAspectRatio());
        writeSetting(settings, "windowwidth", rp.getFrame().getWidth());
        writeSetting(settings, "windowheight", rp.getFrame().getHeight());
        writeSetting(settings, "language", language);

        for (Input i : Input.values())
        {
            if (i == Input.NOTMAPPED)   continue;
            writeSetting(settings, i.toString(), findKey(i));
        } 
    }
/**
 * Converts a boolean attribute into a line of string that will be written into
 * an external file.
 * @param writer Filewriter object.
 * @param var Name of the variable.
 * @param bool Boolean value of the variable.
 * @throws IOException 
 */
    private void writeBoolean(FileWriter writer, String var, boolean bool) throws IOException
    {
        writer.append(var);
        String value = bool ? "1" : "0";
        writer.append(" " + value);
        writer.append(System.getProperty("line.separator"));
    }
/**
 * Converts an object attribute into a line of string that will be written into
 * an external file.
 * @param writer Filewriter object.
 * @param var Name of the variable.
 * @param value Value of the object.
 * @throws IOException 
 */
    private void writeSetting(FileWriter writer, String var, Object value) throws IOException
    {
        writer.append(var);
        writer.append(" " + value.toString());
        writer.append(System.getProperty("line.separator"));
    }
/**
 * Converts a specific input code into an action that influences the game.
 * @param key Keycode to be converted.
 * @return An action input that is mapped to the given key.
 */
    public Input getControl(int key)
    {
        if (!controls.containsKey(key)) return Input.NOTMAPPED;
        return controls.get(key);
    }
/**
 * Remaps a certain action into a keycode.
 * @param action Input that the game will listen to.
 * @param button Keycode of the key that should be configured to an input.
 */
    public void reMap(Input action, int button)
    {
        controls.remove(findKey(action));
        controls.put(button, action);
    }
/**
 * Finds the current keycode that is configured to a certain input action.
 * @param action Specific game input.
 * @return Keycode of the key that causes a given action.
 */
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
