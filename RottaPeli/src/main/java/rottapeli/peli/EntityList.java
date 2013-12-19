
package rottapeli.peli;

import java.util.ArrayList;
import java.util.List;
import rottapeli.domain.superclasses.Entity;

/**
 *
 * @author Pavel
 */
public class EntityList {
    private List entities;
    public EntityList()
    {
        entities = new ArrayList();
    }
    
    public void addEntity(Entity e)
    {
        e.setEntityList(this);
        entities.add(e);
    }

    public void removeEntity(Entity e)
    {
        e.setEntityList(null);
        entities.remove(e);
    }
    
    public List getList(Class type)
    {
        if (type == null)   return entities;
        List list = new ArrayList();
        for (Object e : entities)
        {
            if (type.isInstance(e))
            {
                list.add(e);
            }
        }
        return list;
    }
}
