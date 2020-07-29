// Interface for detecting changes in an EntityList

package org.oxycblt.chess.entity;

public interface EntityChangeListener<T> {

    void onAdded(T added);
    void onRemoved(T removed);

}
