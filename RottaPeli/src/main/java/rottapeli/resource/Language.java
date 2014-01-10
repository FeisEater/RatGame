package rottapeli.resource;

import java.io.File;
import java.io.FileNotFoundException;
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
        words = new HashMap<>();
        File language = new File(rp.getSettings().getLanguage());
        retrieveWords(language);
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
        if (retrieveWords(file))
            rp.getSettings().setLanguage(file.getName());
    }
/**
 * Loads all words and phrases of a specific language.
 * @param language File from which words will be retrieved.
 * @return true if words were retrieved.
 */
    public boolean retrieveWords(File language)
    {
        try
        {
            Scanner sc = new Scanner(language, "UTF-8");
            if (!isValidLanguageFile(sc))
                return false;
            
            words.clear();
            while (sc.hasNextLine())
                mapWord(sc.nextLine());
            sc.close();
        }
        catch (FileNotFoundException e)
        {
            return false;
        }
        return true;
    }
/**
 * Checks if language file is valid. File is valid if it has appropriate
 * String in the first line. Note that after calling this method sc.nextLine()
 * shouldn't be called as this method does that already.
 * @param sc    Scanner object that scans the language file.
 * @return true if language file is usable.
 */
    public boolean isValidLanguageFile(Scanner sc)
    {
        if (!sc.nextLine().contains("###Ratgame language file###"))
        {
            sc.close();
            return false;
        }
        return true;
    }
/**
 * Maps a translation from generic to a specific language based on a
 * line of String fetched from the language file.
 * @param wordPair A Line of String that contains a translation.
 */
    public void mapWord(String wordPair)
    {
        if (!wordPair.startsWith("#"))   return;
        
        if (wordPair.split("%").length == 1)
        {
            words.put(wordPair.split("%")[0], "");
            return;
        }

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
