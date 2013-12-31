
package rottapeli.peli;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import rottapeli.domain.superclasses.Entity;
import rottapeli.interfaces.Updatable;

/**
 *
 * @author Pavel
 */
public class EntityList implements Updatable {
    private RottaPeli rp;
    private List entities;
    public EntityList(RottaPeli peli)
    {
        rp = peli;
        entities = new CopyOnWriteArrayList();
    }
    public RottaPeli gameLogic()    {return rp;}
    
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
    
    @Override
    public void update()
    {
        List<Updatable> updatables = getList(Updatable.class);
        for (Updatable u : updatables)
            u.update();
    }
}
