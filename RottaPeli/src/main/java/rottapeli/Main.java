package rottapeli;

import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public class Main {
    public static void main(String[] args)
    {
        RottaPeli rottapeli = new RottaPeli();
        rottapeli.start();
//placeholder before implementing graphical interface
        while (rottapeli.isRunning())
        {
        }
    }
}
