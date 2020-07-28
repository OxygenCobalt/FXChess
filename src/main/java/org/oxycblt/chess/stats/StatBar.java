// Bar for the timer, player name, and buttons

package org.oxycblt.chess.stats;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.model.EndType;

import org.oxycblt.chess.media.text.TextLoader;

public class StatBar extends Pane {

    private String name;
    private String oldName;
    private ChessType color;

    private ImageView[] nameText;

    private boolean isSelected = false;

    public StatBar(final ChessType color) {

        setPrefSize(276, 20);

        // The black StatBar remains at the top of the screen, the white one is moved to the bottom.
        if (color == ChessType.WHITE) {

            setLayoutY(354);

        }

        ChessType.validateColor(color);

        this.name = "Player"; // Remove this placeholder!
        this.color = color;

        // Generate the name, the maximum amount of characters is 14.
        nameText = TextLoader.createText(
            name, ChessType.inverseOf(color), 5, 5
        );

        getChildren().addAll(nameText);

    }

    // Select the statbar
    public void select() {

        if (!isSelected) {

            isSelected = true;

        }

    }

    // Delect the statbar
    public void deselect() {

        if (isSelected) {

            isSelected = false;

        }

    }

    // Update the StatBar of a game end
    public void onEnd(final ChessType winColor, final EndType type) {

        getChildren().removeAll(nameText);

        /*
        | If the game ended in a draw, append "Draw" onto the current player name. Otherwise
        | append "Won" or "Lost" depending if the winning color matches the color of the StatBar.
        */
        if (type == EndType.DRAW) {

            name += " (Draw)";

        } else {

            if (winColor == color) {

                name += " (Won)";

            } else {

                name += " (Lost)";

            }

        }

        nameText = TextLoader.createText(
            name, ChessType.inverseOf(color), 5, 5
        );

        getChildren().addAll(nameText);

    }

}
