// Interface for detecting entity additions

package org.oxycblt.chess.entity;

public interface EntityAdditionListener<T> {

    void onAdded(T added);

}
