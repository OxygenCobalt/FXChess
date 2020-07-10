// Menu used for pawn promotion

package org.oxycblt.chess.game.board.ui;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.media.images.Texture;
import org.oxycblt.chess.media.images.TextureAtlas;

public class PromotionMenu extends Pane {

    private boolean isShown = false;

    private PromotionEndListener listener;
    private ImageView[] choiceViews;
    private ChessType color;

    private int mouseX;
    private int simpleX;

    public PromotionMenu(final PromotionEndListener listener,
                         final ChessType color,
                         final int x, final int y) {

        setPrefSize(160, 32);
        setOnMouseClicked(confirmHandler);
        setOnMouseMoved(hoverHandler);
        setOnMouseDragged(hoverHandler);

        this.listener = listener;
        this.color = color;

        show(color, x, y);
        createViews(color);

    }

    EventHandler<MouseEvent> confirmHandler = event -> {

        MouseButton button = event.getButton();

        if (button == MouseButton.PRIMARY && isShown) {

            normalizePointer(event);

            if (validateXY()) {

                // Find the choice that matches the current position of the mouse and confirm
                // that, hiding the menu in the process
                updateSimpleX();

                listener.onPromotionEnd(ChessType.PROMOTION_ORDER[simpleX - 1]);

                hide();

            }

        }

    };

    EventHandler<MouseEvent> hoverHandler = event -> {

        // TODO: Add selection rect [As its own object]

    };

    // Normalize a mouse pointer so that the coordinates are solely within the bounds of the menu
    private void normalizePointer(final MouseEvent event) {

        mouseX = (int) (event.getSceneX() - getLayoutX());

    }

    // Validate that the X coordinate is not out of bounds
    private boolean validateXY() {

        return mouseX > 0 && mouseX < getPrefWidth();

    }

    // Update the simple X coordinate from the mouse X coordinate
    private void updateSimpleX() {

        simpleX = mouseX / 32;

    }

    // Show the menu
    public void show(final ChessType newColor, final int x, final int y) {

        ChessType.validateColor(newColor);

        // The menu is aligned differently depending on which half
        // of the board the target piece is.
        if (x < 4) {
            setLayoutX((x * 32) - 32);
        } else {
            setLayoutX((x * 32) - 64);
        }

        /*
        | WHITE pieces are promoted when they reach the top of the
        | board, so align the menu to be on top of them
        | BLACK pieces are promoted when they reach the bottom of
        | the board, so the board is aligned to be below them.
        */
        if (newColor == ChessType.WHITE) {
            setLayoutY((y * 32) - 32);
        } else {
            setLayoutY((y * 32) + 32);
        }

        // Recreate the choice views if the new color is different
        if (newColor != color) {

            createViews(newColor);

        }

        isShown = true;

    }

    // Populate the menu with ImageViews for each choice
    private void createViews(final ChessType newColor) {

        // Reset the list of views
        choiceViews = new ImageView[4];

        for (int i = 0; i < 4; i++) {

            // Add the texture for each chess piece available
            // in the promotion choices.
            choiceViews[i] = TextureAtlas.getTexture(
                Texture.CHESS_PIECES,
                ChessType.PROMOTION_ORDER[i].getCoordinate(),
                newColor.getCoordinate()
            );

            choiceViews[i].setX(i * 32);

        }

        getChildren().addAll(choiceViews);

    }

    // Hide the menu
    private void hide() {

        getChildren().removeAll(choiceViews);
        toBack();
        isShown = false;

    }

}
