package rottapeli.resource;

import java.io.File;

/**
 * Constants of the paths to the external files used in the program.
 * @author Pavel
 */
public class Files {
/** File that contains the saved settings from the previous session. */
    public static final File settingsFile = new File("assets/settings.txt");
/** File that contains the highscore data. */
    public static final File highscoreFile = new File("assets/highscore.txt");
/** Path to the directory that contains language files. */
    public static final String languagePath = "assets/language";
/** Directory that contains language files. */
    public static final File languageDirecory = new File(languagePath);
/** Path to the language that is loaded on default, if no file is speciffied in the settings. */
    public static final String defaultLanguage = languagePath + "/english.txt";
}
