// Bar for the timer, player name, and buttons

package org.oxycblt.chess.stats;

import javafx.scene.layout.Pane;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.model.EndType;

import org.oxycblt.chess.stats.ui.Name;

public class StatBar extends Pane {

    private boolean isSelected = false;

    private Name name;

    public StatBar(final ChessType color) {

        setPrefSize(276, 20);

        // The black StatBar remains at the top of the screen, the white one is moved to the bottom.
        if (color == ChessType.WHITE) {

            setLayoutY(354);

        }

        ChessType.validateColor(color);

        name = new Name(color);

        getChildren().add(name);

    }

    // Select the statbar
    public void select() {

        if (!isSelected) {

            name.select();

            isSelected = true;

        }

    }

    // Delect the statbar
    public void deselect() {

        if (isSelected) {

            name.deselect();

            isSelected = false;

        }

    }

    // Update the StatBar of a game end
    public void onEnd(final ChessType winColor, final EndType type) {

        name.onEnd(winColor, type);

    }

}
