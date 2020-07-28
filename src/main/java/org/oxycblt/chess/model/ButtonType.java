// Enum of button types

package org.oxycblt.chess.model;

public enum ButtonType {

    RESIGN(0), DRAW(1), RESET(2), DRAW_CONFIRM(3);

    private final int coordinate;

    ButtonType(final int coordinate) {

        this.coordinate = coordinate;

    }

    public int getCoordinate() {

        return coordinate;

    }

}
