// Base class for entity lists

package org.oxycblt.chess.entity;

import java.util.ArrayList;

public abstract class EntityList<T> {

    protected ArrayList<T> entities;

    protected EntityChangeListener<T> listener = null;

    public EntityList() {

        entities = new ArrayList<T>();

    }

    // Entity management
    public void addEntity(final T entity) {

        entities.add(entity);

        listener.onAdded(entity);

    }

    public void removeEntity(final T entity) {

        // Notify any listeners of the entity removal as long as the entity was actually removed
        if (entities.remove(entity) && listener != null) {

            listener.onRemoved(entity);

        }

    }

    public boolean hasEntity(final T entity) {

        return entities.contains(entity);

    }

    public ArrayList<T> getEntities() {

        return entities;

    }

}
