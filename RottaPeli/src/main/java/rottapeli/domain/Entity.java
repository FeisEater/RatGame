
package rottapeli.domain;

import rottapeli.peli.EntityList;

/**
 *
 * @author Pavel
 */
public class Entity {
    private EntityList entities;
    public Entity(EntityList l)
    {
        entities = l;
    }
    public EntityList getEntities() {return entities;}
}
