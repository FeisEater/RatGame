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
 *
 * @author Pavel
 */
public class Language {
    private File language;
    private Map<String, String> words;
    private RottaPeli rp;
    public Language(RottaPeli peli)
    {
        rp = peli;
        words = new HashMap<String, String>();
        language = new File(rp.getSettings().getLanguage());
        retrieveWords();
    }
    public void chooseLanguage()
    {
        JFileChooser fc = new JFileChooser(Files.languageDirecory);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION)
            chooseLanguage(fc.getSelectedFile());
    }
    public void chooseLanguage(File file)
    {
        language = file;
        rp.getSettings().setLanguage(file.getName());
        retrieveWords();
    }
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
    public void mapWord(String wordPair)
    {
        if (!wordPair.startsWith("#"))   return;

        words.put(wordPair.split("%")[0], wordPair.split("%")[1]);
    }
    public String translate(String word)
    {
        if (!words.containsKey(word))
            return word;
        return words.get(word);
    }
}
