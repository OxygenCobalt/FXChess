// Indicator for the current turn

package org.oxycblt.chess.menu.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import org.oxycblt.chess.model.ChessType;

public class TurnIndicator extends Rectangle {

    private ChessType color;

    public TurnIndicator(final ChessType color) {

        super(22, 22);
        relocate(292, 8);

        setStrokeWidth(3);
        setStrokeType(StrokeType.INSIDE);
        setStroke(Color.web("eaeaea"));

        setFill(Color.web(ChessType.toHex(color)));

        this.color = color;

    }

    // Update the current turn of the indicator
    public void update(final ChessType newColor) {

        if (color != newColor) {

            setFill(Color.web(ChessType.toHex(newColor)));

            color = newColor;

        }

    }

}
