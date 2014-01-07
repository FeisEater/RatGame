
package rottapeli.domain.superclasses;

import rottapeli.peli.EntityList;
import rottapeli.peli.RottaPeli;

/**
 * Entity that is stored by EntityList. All objects of the game extend this class.
 * @author Pavel
 */
public class Entity {
/** Pointer to the Entity list. */
    private EntityList entities;
    public void setEntityList(EntityList list)
        {entities = list;}
    public EntityList getEntities() {return entities;}
    public RottaPeli gameLogic()   {return entities.gameLogic();}
    public boolean hasGameLogic()   {return gameLogic() != null;}
    public boolean hasEntities()    {return entities != null;}
}
