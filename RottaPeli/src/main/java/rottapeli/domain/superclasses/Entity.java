
package rottapeli.domain.superclasses;

import rottapeli.peli.EntityList;
import rottapeli.peli.RottaPeli;

/**
 *
 * @author Pavel
 */
public class Entity {
    private EntityList entities;
    public Entity()
    {
    }
    public void setEntityList(EntityList list)
        {entities = list;}
    public EntityList getEntities() {return entities;}
    public RottaPeli gameLogic()   {return entities.gameLogic();}
    public boolean hasGameLogic()   {return gameLogic() != null;}
    public boolean hasEntities()    {return entities != null;}
}
