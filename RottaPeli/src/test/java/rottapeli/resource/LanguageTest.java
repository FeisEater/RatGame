/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rottapeli.resource;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public class LanguageTest {
    private RottaPeli rp;
    public LanguageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        rp = new RottaPeli(false);
    }
    
    @After
    public void tearDown() {
    }

    public boolean languageWasSet(String path)
    {
        rp.getLanguage().chooseLanguage(new File(path));
        return rp.getSettings().getLanguage().equals(path);
    }
    @Test
    public void setsLanguage()
    {
        assertTrue(languageWasSet("assets/language/finnish.txt"));
    }
    @Test
    public void dontsetLanguageIfFileDoesntExist()
    {
        assertTrue(!languageWasSet("assets/language/piglatin.txt"));        
    }
    @Test
    public void dontsetLanguageIfFileIsNotStandard()
    {
        assertTrue(!languageWasSet("assets/language/nonstandard.txt"));        
    }
    
    @Test
    public void mapsWord()
    {
        rp.getLanguage().mapWord("#test%This is test!");
        assertTrue(rp.getLanguage().translate("#test").equals("This is test!"));
    }
    @Test
    public void dontMapWordIfDoesntStartWithSpecialCharacter()
    {
        rp.getLanguage().mapWord("test%This is test!");
        assertTrue(!rp.getLanguage().translate("#test").equals("This is test!") &&
                !rp.getLanguage().translate("test").equals("This is test!"));
    }
    @Test
    public void mapsEmptyWord()
    {
        rp.getLanguage().mapWord("#test%");
        assertTrue(rp.getLanguage().translate("#test").isEmpty());
    }
    @Test
    public void mapsEmptyWord2()
    {
        rp.getLanguage().mapWord("#test");
        assertTrue(rp.getLanguage().translate("#test").isEmpty());
    }
    @Test
    public void whenAskedToTranslateAnUntranslatableWordReturnWordItself()
    {
        assertTrue(rp.getLanguage().translate("#test").equals("#test"));
    }
}
