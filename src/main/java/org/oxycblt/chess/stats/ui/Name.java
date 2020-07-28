// Pane for the StatBars name/Move indicator

package org.oxycblt.chess.stats.ui;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.model.EndType;

import org.oxycblt.chess.media.text.TextLoader;

public class Name extends Pane {

    private String name;
    private ChessType color;

    private ImageView[] nameText;

    public Name(final ChessType color) {

        this.name = "Player";
        this.color = color;

        // Generate the name, the maximum amount of characters is 14.
        nameText = TextLoader.createText(
            name, ChessType.inverseOf(color), 5, 5
        );

        getChildren().addAll(nameText);

        setPrefSize(9 + (name.length() * 9), 20);

    }

    public void select() {

        if (color == ChessType.WHITE) {

            setStyle("-fx-background-color: #b4b4b4");

        } else {

            setStyle("-fx-background-color: #595959");

        }

    }

    public void deselect() {

        setStyle("-fx-background-color: #" + ChessType.toHex(color));

    }

    // Update the Name of a game end
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

        deselect();
        setPrefSize(9 + (name.length() * 9), 20);

    }

}
