// Base class for entity lists

package org.oxycblt.chess.entity;

import java.util.ArrayList;

public abstract class EntityList<T> {

    protected ArrayList<T> entities;

    public EntityList() {

        entities = new ArrayList<T>();

    }

    // Basic entity management
    public void addEntity(final T entity) {

        entities.add(entity);

    }

    public void removeEntity(final T entity) {

        entities.remove(entity);

    }

}
