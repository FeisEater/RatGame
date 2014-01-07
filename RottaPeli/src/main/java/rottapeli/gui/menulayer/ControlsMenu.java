
package rottapeli.gui.menulayer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import rottapeli.peli.RottaPeli;
import rottapeli.resource.Input;

/**
 *
 * @author Pavel
 */
public class ControlsMenu extends Menu {
    private Input waitingInput;
    
    public ControlsMenu(RottaPeli rp)   {super(rp);}
    
    public void setInputWaiting(Input input)
    {
        waitingInput = input;
        setEnabledToAllButtons(waitingInput == null);
    }
    public void setEnabledToAllButtons(boolean enabled)
    {
        for (int i = 0; i < getMenuLayer().getComponentCount(); i++)
            getMenuLayer().getComponent(i).setEnabled(enabled);
    }
    @Override
    public void showMenu()
    {
        getMenuLayer().setLayout(new GridLayout(0,3));

        getMenuLayer().emptyRow();
        getMenuLayer().createLabel("#controlstitle");
        getMenuLayer().emptyRow();
        for (Input i : Input.values())
        {
            if (i == Input.NOTMAPPED)   continue;
            getMenuLayer().createButton(i.toString());
        }
        getMenuLayer().emptyRow();
        getMenuLayer().createButton("#back");
        getMenuLayer().emptyRow();
        
        rp.getInput().setListenerMenu(this);
    }
    @Override
    public void hideMenu()
    {
        rp.getInput().setListenerMenu(null);
        super.hideMenu();
    }
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        switch (ae.getActionCommand())
        {
            case "#back":
                backPressed();
                break;
            default:
                anyOtherButtonPressed(ae);
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e)
    {
        if (waitingInput == null) return;
        rp.getSettings().reMap(waitingInput, e.getKeyCode());
        setInputWaiting(null);
    }
    public void anyOtherButtonPressed(ActionEvent ae)
    {
        setInputWaiting(Input.valueOf(ae.getActionCommand()));
    }
    public void backPressed()
    {
        getMenuLayer().switchMenu(new SettingsMenu(rp));
    }
}
