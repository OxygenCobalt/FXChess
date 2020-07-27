// Name of the player/indicator of game state

package org.oxycblt.chess.stats.ui;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.media.text.TextLoader;

public class Name extends Pane {

    private String name;
    private ImageView[] text;

    public Name(final ChessType color) {

        name = "Placeholder"; // Replace this with the user-chosen values

        text = TextLoader.createText(
            name, ChessType.inverseOf(color), 5, 5
        );

        getChildren().addAll(text);

    }

    public void updateName() {

        // Stub

    }

}
