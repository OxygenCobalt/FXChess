// Interface for detecting entity removals

package org.oxycblt.chess.entity;

public interface EntityRemovalListener<T> {

    void onRemoved(T removed);

}
