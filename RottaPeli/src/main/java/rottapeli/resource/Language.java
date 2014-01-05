package rottapeli.resource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Pavel
 */
public class Language {
    private File language;
    private Map<String, String> words;
    public Language()
    {
        words = new HashMap<String, String>();
        language = Files.defaultLanguage;
        retrieveWords();
    }
    public void chooseLanguage(File file)
    {
        language = file;
        retrieveWords();
    }
    public void retrieveWords()
    {
        words.clear();
        
        try
        {
            Scanner sc = new Scanner(language, "UTF-8");
            while (sc.hasNextLine())
            {
                String wordPair = sc.nextLine();
                if (!wordPair.startsWith("#"))   continue;
                
                words.put(wordPair.split("%")[0], wordPair.split("%")[1]);
            }
            sc.close();
        }
        catch (IOException e)   {
            System.out.println("no file, stupid!");
        }
    }
    public String translate(String word)
    {
        if (!words.containsKey(word))
            return word;
        return words.get(word);
    }
}
