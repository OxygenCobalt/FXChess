// Menu used for pawn promotion

package org.oxycblt.chess.game.board.ui;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.EndListener.EndType;
import org.oxycblt.chess.game.board.animation.EndAnimation;

import org.oxycblt.chess.media.images.Texture;
import org.oxycblt.chess.media.images.TextureAtlas;

public class EndScreen extends Pane {

    private boolean isShown = false;
    private ChessType color;
    private EndType type;

    private ImageView endView = null;
    private EndAnimation endAnim;

    public EndScreen(final ChessType color, final EndType type) {

        setPrefSize(124, 12);
        relocate(66, 144);

        this.color = color;
        this.type = type;

        endAnim = new EndAnimation(this);

        show(color, type);

    }

    // Show the screen
    public void show(final ChessType newColor, final EndType newType) {

        System.out.println(newType);

        // Generate the text if the color/type has changed or if the text hasn't been created yet
        if (newColor != color || newType != type || endView == null) {

            if (endView != null) {

                getChildren().remove(endView);

            }

            /*
            | Load the specific text sprite to use based on the type of Ending and the Color
            | - Checkmate always shows the winning color
            | - Stalemate always shows color that is unable to move
            | - Draw just shows a random color
            */
            endView = TextureAtlas.getTexture(
                Texture.END_SCREEN, newColor.getCoordinate(), newType.getCoordinate(), 124, 12
            );

            color = newColor;
            type = newType;

            getChildren().add(endView);

        }

        toFront();
        endAnim.in();

        isShown = true;

    }

    // Hide the screen
    public void hide() {

        if (isShown) {

            endAnim.out();

            isShown = false;

        }

    }

}
