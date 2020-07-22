// Menu used for pawn promotion

package org.oxycblt.chess.game.board.ui;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.EndListener.EndType;
import org.oxycblt.chess.game.board.animation.FadeAnimation;

import org.oxycblt.chess.media.images.Texture;
import org.oxycblt.chess.media.images.TextureAtlas;

public class EndScreen extends Pane {

    private ImageView endView = null;
    private FadeAnimation fadeAnim;
    private ChessType color;
    private EndType type;

    public EndScreen(final ChessType color, final EndType type) {

        setPrefSize(124, 12);
        relocate(66, 122);

        this.color = color;
        this.type = type;

        fadeAnim = new FadeAnimation(this);

        show(color, type);

    }

    // Show the screen
    public void show(final ChessType newColor, final EndType newType) {

        // Generate the text if the color/type has changed or if the text hasn't been created yet
        if (newColor != color || newType != type || endView == null) {

            /*
            | Load the specific text sprite to use based on the type of Ending and the Color
            | - Checkmate always shows the winning color
            | - Stalemate always shows color that is unable to move
            | - Draw just shows a random color
            */
            endView = TextureAtlas.getTexture(
                Texture.END_SCREEN, color.getCoordinate(), type.getCoordinate(), 124, 12
            );

            color = newColor;
            type = newType;

        }

        getChildren().add(endView);

        fadeAnim.fadeIn();

    }

    // Hide the screen
    public void hide() {

        // Possibly add the fadeout here [maybe]

        getChildren().remove(endView);
        toBack();

    }

}
