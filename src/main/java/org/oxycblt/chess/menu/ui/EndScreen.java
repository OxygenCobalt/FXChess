// Game end screen

package org.oxycblt.chess.menu.ui;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import org.oxycblt.chess.model.ChessType;

import org.oxycblt.chess.media.Texture;
import org.oxycblt.chess.media.TextureAtlas;

import org.oxycblt.chess.menu.animation.EndAnimation;

public class EndScreen extends Pane {

    private boolean isShown = false;
    private ChessType color;

    private ImageView endView = null;
    private EndAnimation endAnim;

    public EndScreen(final ChessType color) {

        setPrefSize(124, 12);
        relocate(99, 35);

        this.color = color;

        endAnim = new EndAnimation(this);

        show(color);

    }

    // Show the screen
    public void show(final ChessType newColor) {

        // Generate the text if the color/type has changed or if the text hasn't been created yet
        if (newColor != color || endView == null) {

            if (endView != null) {

                getChildren().remove(endView);

            }

            // Get the texture for the end screen based on which color won.
            endView = TextureAtlas.getTexture(
                Texture.END_SCREEN, 0, newColor.getCoordinate(), 124, 12
            );

            color = newColor;

            getChildren().add(endView);

        }

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
