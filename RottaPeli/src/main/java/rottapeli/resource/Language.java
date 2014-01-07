package rottapeli.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JFileChooser;
import rottapeli.peli.RottaPeli;

/**
 * Language manager. Contains a map of words and phrases of a specified
 * language that will be inserted instead of a generic language. Specific
 * language shouldn't contain character '%' as a part of its translation.
 * @author Pavel
 */
public class Language {
/** Language file. */
    private File language;
/** Map of words and phrases. */
    private Map<String, String> words;
/** Game logic object. */
    private RottaPeli rp;
/**
 * Constructor. Loads the words.
 * @param peli Game logic object.
 */
    public Language(RottaPeli peli)
    {
        rp = peli;
        words = new HashMap<String, String>();
        language = new File(rp.getSettings().getLanguage());
        retrieveWords();
    }
/**
 * Shows a file chooser window and lets the user choose another
 * language file.
 */
    public void chooseLanguage()
    {
        JFileChooser fc = new JFileChooser(Files.languageDirecory);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION)
            chooseLanguage(fc.getSelectedFile());
    }
/**
 * Retranslates everything in the program from a specified language file.
 * @param file Language to which everything should be translated to.
 */
    public void chooseLanguage(File file)
    {
        language = file;
        rp.getSettings().setLanguage(file.getName());
        retrieveWords();
    }
/**
 * Loads all words and phrases of a specific language.
 */
    public void retrieveWords()
    {
        words.clear();
        
        try
        {
            Scanner sc = new Scanner(language, "UTF-8");
            while (sc.hasNextLine())
                mapWord(sc.nextLine());
            sc.close();
        }
        catch (FileNotFoundException e)   {}
    }
/**
 * Maps a translation from generic to a specific language based on a
 * line of String fetched from the language file.
 * @param wordPair A Line of String that contains a translation.
 */
    public void mapWord(String wordPair)
    {
        if (!wordPair.startsWith("#"))   return;

        words.put(wordPair.split("%")[0], wordPair.split("%")[1]);
    }
/**
 * Translates a word of generic language into a specific language.
 * @param word Word in a generic language, starts with '#'
 * @return User-friendly word of some specific language.
 */
    public String translate(String word)
    {
        if (!words.containsKey(word))
            return word;
        return words.get(word);
    }
}
