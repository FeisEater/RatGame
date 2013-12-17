package rottapeli;

import rottapeli.domain.Ball;
import rottapeli.peli.RottaPeli;
import rottapeli.resource.Const;
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
