// Interface for detecting changed in an entitylist

package org.oxycblt.chess.entity;

public interface EntityChangeListener<T> {

    void onAdded(T added);
    void onRemoved(T removed);

}
