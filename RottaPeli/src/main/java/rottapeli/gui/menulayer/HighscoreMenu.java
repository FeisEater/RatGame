
package rottapeli.gui.menulayer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public class HighscoreMenu extends Menu {
    public HighscoreMenu(RottaPeli rp)   {super(rp);}
    @Override
    public void showMenu()
    {
        getMenuLayer().setLayout(new GridLayout(0,3));
        getMenuLayer().emptyRow();
        getMenuLayer().createLabel("#1 FeisEater 9999");
        getMenuLayer().createLabel("#2 gfdsgfdgg 9998");
        getMenuLayer().createLabel("#3 fgjsrtshg 9997");
        getMenuLayer().createLabel("#4 fdsfsdfd 9996");
        getMenuLayer().createLabel("#5 bobb 9995");
        getMenuLayer().createLabel("#6 dasdsdsdr 9994");
        getMenuLayer().createLabel("#7 jhgfjftyr 9993");
        getMenuLayer().createLabel("#8 mnbbvjhjh 9992");
        getMenuLayer().createLabel("#9 rererewer 9991");
        getMenuLayer().createLabel("#10 grgrdgdrg 1");
        getMenuLayer().emptyRow();
        getMenuLayer().createButton("Back");
        getMenuLayer().emptyRow();
    }
    @Override
    public void hideMenu()
    {
        getMenuLayer().setLayout(new GridLayout(0,3, 0, 32));
        super.hideMenu();
    }
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        switch (ae.getActionCommand())
        {
            case "Back":
                getMenuLayer().switchMenu(new MainMenu(rp));
                break;
            default:
                break;
        }
    }

}
