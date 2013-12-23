package rottapeli.gui;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import rottapeli.interfaces.Updatable;
import rottapeli.resource.Const;

/**
 *
 * @author Pavel
 */
public class GraphicInterface implements Runnable {
    private JFrame frame;
    private GameField field;
    
    public void createComponents(Container container)
    {
        field = new GameField();
        container.add(field);
    }
    
    @Override
    public void run()
    {
        frame = new JFrame("Rottapeli");
        frame.setPreferredSize(new Dimension(Const.width * 2 + 16, Const.height * 2 + 36));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }
    
    public GameField getField()
    {
        return field;
    }
}
