// Bar for the timer, player name, and buttons

package org.oxycblt.chess.stats;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import org.oxycblt.chess.stats.ui.Timer;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.model.EndType;

import org.oxycblt.chess.media.text.TextLoader;

public class StatBar extends Pane {

    private String name;
    private ChessType color;

    private ImageView[] nameText;
    private Timer timer;

    private boolean isSelected = false;

    public StatBar(final ChessType color) {

        setPrefSize(276, 20);

        // The black StatBar remains at the top of the screen, the white one is moved to the bottom.
        if (color == ChessType.WHITE) {

            setLayoutY(354);

        }

        ChessType.validateColor(color);

        this.name = "Player";
        this.color = color;

        nameText = TextLoader.createText(
            name, ChessType.inverseOf(color), 4, 5
        );

        timer = new Timer(color);

        getChildren().addAll(nameText);
        getChildren().add(timer);

    }

    // Select the statbar, // TODO // Starting the timer as well.
    public void select() {

        if (!isSelected) {

            timer.start();

            isSelected = true;

        }

    }

    // Delect the statbar, // TODO // Stopping the timer.
    public void deselect() {

        if (isSelected) {

            timer.stop();

            isSelected = false;

        }

    }

    public void onEnd(final ChessType winColor, final EndType type) {

        getChildren().removeAll(nameText);

        if (winColor == color) {

            name += type.getWinText();

        } else {

            name += type.getLoseText();

        }

        nameText = TextLoader.createText(
            name, ChessType.inverseOf(color), 4, 5
        );

        getChildren().addAll(nameText);

        timer.stop();

    }

}
