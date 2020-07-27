// Bar for the timer, player name, and buttons

package org.oxycblt.chess.stats;

import javafx.scene.layout.Pane;

import org.oxycblt.chess.stats.ui.Name;

import org.oxycblt.chess.shared.ChessType;

public class StatBar extends Pane {

    private Name name;

    private boolean isSelected = false;

    public StatBar(final ChessType color) {

        setPrefSize(276, 20);

        // The black StatBar remains at the top of the screen, the white one is moved to the bottom.
        if (color == ChessType.WHITE) {

            setLayoutY(354);

        }

        ChessType.validateColor(color);

        name = new Name(color);

        getChildren().addAll(
            name
        );

    }

    public void select() {

        if (!isSelected) {

            System.out.println("Select");

            isSelected = true;

        }

    }

    public void deselect() {

        if (isSelected) {

            System.out.println("Deselect");

            isSelected = false;

        }

    }

}
