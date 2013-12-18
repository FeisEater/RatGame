
package rottapeli.domain;

import rottapeli.peli.EntityList;

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
}
