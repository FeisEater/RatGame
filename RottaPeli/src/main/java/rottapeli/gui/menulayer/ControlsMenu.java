
package rottapeli.gui.menulayer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import rottapeli.peli.RottaPeli;
import rottapeli.resource.Input;

/**
 * The menu that is accessed from Main menu > Settings > Change controls
 * @author Pavel
 */
public class ControlsMenu extends Menu {
/** Game action that is waiting to be configured for a certain input. 
    If null, no action is currently waiting to be configured. */
    private Input waitingInput;
/**
 * Constructor.
 * @param rp Game logic object.
 */
    public ControlsMenu(RottaPeli rp)   {super(rp);}
/**
 * Sets an input waiting to be configured. Disables all menu buttons while
 * input is not configured.
 * @param input Input to be configured. If null, notifies that no input
 *          needs to be configured.
 */
    public void setInputWaiting(Input input)
    {
        waitingInput = input;
        setEnabledToAllButtons(waitingInput == null);
    }
/**
 * Enabled or disables all buttons in the menu.
 * @param enabled enables if true, disables if false.
 */
    public void setEnabledToAllButtons(boolean enabled)
    {
        for (int i = 0; i < getMenuLayer().getComponentCount(); i++)
            getMenuLayer().getComponent(i).setEnabled(enabled);
    }
/**
 * Performs all necessary actions to make this menu show up. Creates all
 * neccessary components.
 */
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
/**
 * Makes all necessary actions to make this menu not show up anymore.
 * Removes all components created by this menu.
 */
    @Override
    public void hideMenu()
    {
        rp.getInput().setListenerMenu(null);
        super.hideMenu();
    }
/**
 * Reacts to an actionEvent, a button press for instance.
 * @param ae ActionEvent that triggered this method.
 */
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
/**
 * Reacts to a key release if it's set to listen for key presses.
 * @param e KeyEvent that triggered this method.
 */
    @Override
    public void keyReleased(KeyEvent e)
    {
        if (waitingInput == null) return;
        rp.getSettings().reMap(waitingInput, e.getKeyCode());
        setInputWaiting(null);
    }
/**
 * React to any other button press. Sets the input described in the button
 * waiting to be configured.
 * @param ae ActionEvent that specifies which button was pressed.
 */
    public void anyOtherButtonPressed(ActionEvent ae)
    {
        setInputWaiting(Input.valueOf(ae.getActionCommand()));
    }
/**
 * React to the Back button being pressed. Return to the Settings menu.
 */
    public void backPressed()
    {
        getMenuLayer().switchMenu(new SettingsMenu(rp));
    }
}
