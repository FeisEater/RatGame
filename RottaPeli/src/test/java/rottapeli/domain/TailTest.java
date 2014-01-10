package rottapeli.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import rottapeli.peli.EntityList;

/**
 *
 * @author Pavel
 */
public class TailTest {
    @Test
    public void onDeathKillOwner()
    {
        Rat rat = new Rat(64, 64);
        EntityList list = new EntityList(null);
        list.addEntity(rat);
        Tail tail = new Tail(0,0,1,1,rat);
        tail.die();
        assertTrue(list.getList(Tail.class).isEmpty());
    }
}
