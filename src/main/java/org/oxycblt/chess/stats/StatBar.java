// Bar for the timer, player name, and buttons

package org.oxycblt.chess.stats;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.model.EndType;

import org.oxycblt.chess.media.text.TextLoader;

public class StatBar extends Pane {

    private String name;
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

        this.name = "Placeholder";
        this.color = color;

        nameText = TextLoader.createText(
            name, ChessType.inverseOf(color), 5, 5
        );

        getChildren().addAll(nameText);

    }

    // Select the statbar, // TODO // Starting the timer as well.
    public void select() {

        if (!isSelected) {

            System.out.println("Select");

            isSelected = true;

        }

    }

    // Delect the statbar, // TODO // Stopping the timer.
    public void deselect() {

        if (isSelected) {

            System.out.println("Deselect");

            isSelected = false;

        }

    }

    public void onEnd(final ChessType winColor, final EndType type) {

        getChildren().remove(nameText);

        if (winColor == color) {

            name += type.getWinText();

        } else {

            name += type.getLoseText();

        }

        nameText = TextLoader.createText(
            name, ChessType.inverseOf(color), 5, 5
        );

        getChildren().addAll(nameText);

    }

}
