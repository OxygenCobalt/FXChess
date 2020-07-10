// Base class for entity lists

package org.oxycblt.chess.entity;

import java.util.ArrayList;

public abstract class EntityList<T> {

    protected ArrayList<T> entities;

    protected EntityAdditionListener<T> addListener = null;
    protected EntityRemovalListener<T> removeListener = null;

    public EntityList() {

        entities = new ArrayList<T>();

    }

    // Entity management
    public void addEntity(final T entity) {

        entities.add(entity);

        addListener.onAdded(entity);

    }

    public void removeEntity(final T entity) {

        // Notify any listeners of the entity removal as long
        // as the entity was actually removed
        if (entities.remove(entity) && removeListener != null) {

            removeListener.onRemoved(entity);

        }

    }

}
