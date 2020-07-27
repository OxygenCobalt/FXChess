// A Time Control timer/Move indicator

package org.oxycblt.chess.stats.ui;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.media.text.TextLoader;

public class Timer extends Pane {

    private ChessType color;

    private int hours;
    private int minutes;
    private int seconds;

    private ImageView[] digits;

    public Timer(final ChessType color) {

        relocate(277, 0);
        setPrefSize(53, 20);

        this.color = color;

        hours = 10;
        minutes = 0;
        seconds = 0;

        generateDigits();

    }

    // Start the timer
    public void start() {

        setStyle("-fx-background-color: #026ced");

        // TODO: White timers need to have their letters swapped out to White to be more readable

    }

    // Stop the timer
    public void stop() {

        setStyle("-fx-background-color: #" + ChessType.toHex(color));

    }

    // Generate the digits [Placeholder function]
    private void generateDigits() {

        digits = TextLoader.createText(
            "02:00", ChessType.inverseOf(color), 5, 5
        );

        getChildren().addAll(digits);

    }

}
