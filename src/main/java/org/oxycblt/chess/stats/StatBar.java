// Bar for the timer, player name, and buttons

package org.oxycblt.chess.stats;

import javafx.scene.layout.Pane;

import org.oxycblt.chess.shared.ChessType;

public class StatBar extends Pane {

    private boolean isSelected = false;

    public StatBar(final ChessType color) {

        setPrefSize(276, 20);

        ChessType.validateColor(color);

        if (color == ChessType.WHITE) {

            setLayoutY(354);

        }

        setStyle("-fx-background-color: #" + ChessType.toHex(color));

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
