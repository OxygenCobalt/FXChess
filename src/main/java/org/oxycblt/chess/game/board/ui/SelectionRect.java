// Decorative rectangle shown when selecting something

package org.oxycblt.chess.game.board.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import org.oxycblt.chess.game.ChessType;

public class SelectionRect extends Rectangle {

    // Basic constructor
    public SelectionRect() {

        super(32, 32);

        setFill(Color.TRANSPARENT);
        setStrokeWidth(3);
        setStrokeType(StrokeType.INSIDE);

    }

    // Advanced constructor
    public SelectionRect(final ChessType color, final int x, final int y) {

        super(32, 32);

        relocate(x, y);
        setFill(Color.TRANSPARENT);
        setStroke(color);
        setStrokeWidth(3);
        setStrokeType(StrokeType.INSIDE);

    }

    // Variation of setStroke that can take ChessType colors
    public void setStroke(final ChessType color) {

        ChessType.validateColor(color);

        if (color == ChessType.WHITE) {

            super.setStroke(Color.WHITE);

        } else {

            super.setStroke(Color.BLACK);

        }

    }

    // Variation of relocate() that takes simple coordinates
    public void relocate(final int x, final int y) {

        super.relocate(x * 32, y * 32);

    }

}
