/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rottapeli.peli;

import java.awt.event.KeyEvent;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.resource.Input;

/**
 *
 * @author Pavel
 */
public class SettingsTest {
    private Settings settings;
    public SettingsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        settings = new Settings(null);
        settings.defaultSettings();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void setsLanguage()
    {
        settings.setLanguage("romanian.txt");
        assertTrue(settings.getLanguage().equals("assets/language/romanian.txt"));
    }
    
    @Test
    public void fetchesAspectRatio()
    {
        settings.fetchSetting("aspectratio 1");
        boolean b1 = settings.hasAspectRatio();
        settings.fetchSetting("aspectratio 0");
        boolean b2 = !settings.hasAspectRatio();
        assertTrue(b1 && b2);
    }
    @Test
    public void fetchesWindowWidth()
    {
        settings.fetchSetting("windowwidth 100");
        int i1 = settings.getSavedWidth();
        settings.fetchSetting("windowwidth 200");
        int i2 = settings.getSavedWidth();
        settings.fetchSetting("windowwidth 500");
        int i3 = settings.getSavedWidth();
        assertTrue(i1 == 100 && i2 == 200 && i3 == 500);
    }
    @Test
    public void fetchesWindowHeight()
    {
        settings.fetchSetting("windowheight 200");
        int i1 = settings.getSavedHeight();
        settings.fetchSetting("windowheight 100");
        int i2 = settings.getSavedHeight();
        settings.fetchSetting("windowheight 500");
        int i3 = settings.getSavedHeight();
        assertTrue(i1 == 200 && i2 == 100 && i3 == 500);
    }
    public void fetchesLanguage()
    {
        settings.fetchSetting("language spanish.txt");
        String s1 = settings.getLanguage();
        settings.fetchSetting("language italian.txt");
        String s2 = settings.getLanguage();
        settings.fetchSetting("language chinese.txt");
        String s3 = settings.getLanguage();
        assertTrue(s1.equals("assets/language/spanish.txt") &&
                s2.equals("assets/language/italian.txt") &&
                s3.equals("assets/language/chinese.txt"));
    }
    @Test
    public void fetchesInputs()
    {
        settings.fetchSetting("PLR1RIGHT 41");
        settings.fetchSetting("PLR1UP 88");
        settings.fetchSetting("PAUSE 16");
        assertTrue(settings.getControl(41) == Input.PLR1RIGHT &&
                settings.getControl(88) == Input.PLR1UP &&
                settings.getControl(16) == Input.PAUSE &&
                settings.findKey(Input.PLR1RIGHT) == 41 &&
                settings.findKey(Input.PLR1UP) == 88 &&
                settings.findKey(Input.PAUSE) == 16);
    }
    
    @Test
    public void findsCorrectDefaultInputs()
    {
        assertTrue(settings.getControl(KeyEvent.VK_RIGHT) == Input.PLR1RIGHT &&
                settings.getControl(KeyEvent.VK_LEFT) == Input.PLR1LEFT &&
                settings.getControl(KeyEvent.VK_UP) == Input.PLR1UP &&
                settings.getControl(KeyEvent.VK_DOWN) == Input.PLR1DOWN &&
                settings.getControl(KeyEvent.VK_P) == Input.PAUSE);
    }
    @Test
    public void findsCorrectDefaultKeys()
    {
        assertTrue(settings.findKey(Input.PLR1RIGHT) == KeyEvent.VK_RIGHT &&
                settings.findKey(Input.PLR1LEFT) == KeyEvent.VK_LEFT &&
                settings.findKey(Input.PLR1UP) == KeyEvent.VK_UP &&
                settings.findKey(Input.PLR1DOWN) == KeyEvent.VK_DOWN &&
                settings.findKey(Input.PAUSE) == KeyEvent.VK_P);
    }
    @Test
    public void tryNotMappedKey()
    {
        assertTrue(settings.getControl(KeyEvent.VK_H) == Input.NOTMAPPED &&
                settings.getControl(KeyEvent.VK_ENTER) == Input.NOTMAPPED &&
                settings.getControl(KeyEvent.VK_F6) == Input.NOTMAPPED);
    }
    @Test
    public void tryFindingAKeyForUnMappedInput()
    {
        assertTrue(settings.findKey(Input.NOTMAPPED) == -1);
    }
    
    @Test
    public void remapsKey()
    {
        boolean b1 = settings.getControl(KeyEvent.VK_D) == Input.NOTMAPPED;
        settings.reMap(Input.PLR1RIGHT, KeyEvent.VK_D);
        boolean b2 = settings.getControl(KeyEvent.VK_D) == Input.PLR1RIGHT;
        assertTrue(b1 && b2);
    }
    @Test
    public void whenKeyIsUnmappedItDoesntDoAnything()
    {
        boolean b1 = settings.getControl(KeyEvent.VK_RIGHT) == Input.PLR1RIGHT;
        settings.reMap(Input.PLR1RIGHT, KeyEvent.VK_D);
        boolean b2 = settings.getControl(KeyEvent.VK_RIGHT) == Input.NOTMAPPED;
        assertTrue(b1 && b2);
    }
    @Test
    public void onSeveralRemappingsNoRedundantKeys()
    {
        Random r = new Random();
        for (int i = 0; i < 1000; i++)
            settings.reMap(Input.values()[r.nextInt(6)], r.nextInt(100));
        int inputs = 0;
        for (int i = 0; i < 100; i++)
        {
            if (settings.getControl(i) != Input.NOTMAPPED)
                inputs++;
        }
        assertTrue(inputs <= Input.values().length - 1);
    }
}
