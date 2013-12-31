
package rottapeli.peli;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import rottapeli.domain.superclasses.Entity;
import rottapeli.interfaces.Updatable;

/**
 * List that contains all Entities that were created in the game.
 * @author Pavel
 */
public class EntityList implements Updatable {
    private RottaPeli rp;
    private List entities;
/**
 * Constructor.
 * @param peli Pointer to the game logic Object.
 */
    public EntityList(RottaPeli peli)
    {
        rp = peli;
        entities = new CopyOnWriteArrayList();
    }
    public RottaPeli gameLogic()    {return rp;}
/**
 * Adds an Entity to the game.
 * @param e Entity to be added.
 */
    public void addEntity(Entity e)
    {
        e.setEntityList(this);
        entities.add(e);
    }
/**
 * Removes an Entity from the game.
 * @param e Entity that is to be removed.
 */
    public void removeEntity(Entity e)
    {
        e.setEntityList(null);
        entities.remove(e);
    }
/**
 * Removes all Entities of the specified class or interface.
 * @param type Type of Entities that needs to be removed.
 */
    public void removeAll(Class type)
    {
        removeAll(getList(type));
    }
/**
 * Removes all Entities contained in a Collection.
 * @param c Data structure that contains Entities.
 */
    public void removeAll(Collection<Entity> c)
    {
        for (Entity e : c)    e.setEntityList(null);
        entities.removeAll(c);
    }
/**
 * Retrieves a List of Entites of a certain class or interface.
 * @param type Class or interface by which Entities are to be filtered.
 * @return List of filtered Entities.
 */
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
/**
 * This method is called every tick on the timer.
 * <p>
 * EntityList updates all Entities that can be updated.
 */
    @Override
    public void update()
    {
        List<Updatable> updatables = getList(Updatable.class);
        for (Updatable u : updatables)
            u.update();
    }
}
