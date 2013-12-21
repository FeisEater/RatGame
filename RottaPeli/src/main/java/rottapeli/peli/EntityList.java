
package rottapeli.peli;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import rottapeli.domain.superclasses.Entity;

/**
 *
 * @author Pavel
 */
public class EntityList {
    private List entities;
    public EntityList()
    {
        entities = new CopyOnWriteArrayList();
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
    
    public void removeAll(Class type)
    {
        if (type == null)
        {
            removeAll(entities);
            return;
        }
        
        removeAll(getList(type));
    }
    public void removeAll(Collection<Entity> c)
    {
        for (Entity e : c)    e.setEntityList(null);
        entities.removeAll(c);
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
