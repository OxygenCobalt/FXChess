// Menu used for pawn promotion

package org.oxycblt.chess.game.board.ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    private ChessType color;

    private SelectionRect selectRect = null;
    private ImageView[] choiceViews;

    private int mouseX = 0;
    private int simpleX = 0;
    private int cacheSimpleX = -1;

    public PromotionMenu(final PromotionEndListener listener,
                         final ChessType color,
                         final int x, final int y) {

        setPrefSize(128, 32);
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

            if (validateX()) {

                // Find the choice that matches the current position of the mouse and confirm
                // that, hiding the menu in the process
                updateSimpleX();

                listener.onPromotionEnd(ChessType.PROMOTION_ORDER[simpleX]);

                hide();

            }

        }

    };

    EventHandler<MouseEvent> hoverHandler = event -> {

        if (isShown) {

            normalizePointer(event);

            if (validateX()) {

                updateSimpleX();

                // Make sure the mouse coordinate has meaninfully changed
                if (simpleX != cacheSimpleX) {

                    if (selectRect == null) {

                        selectRect = new SelectionRect(color, simpleX, 0);
                        getChildren().add(selectRect);

                    } else {

                        selectRect.relocate(simpleX * 32, 0);

                        // Change the stroke to its respective color if it has
                        // been set to a transparent through hide()
                        if (selectRect.getStroke() == Color.TRANSPARENT) {

                            selectRect.setStroke(color);

                        }

                    }

                    cacheSimpleX = simpleX;

                }

            }

        }

    };

    // Normalize a mouse pointer so that the coordinates are solely within the bounds of the menu
    private void normalizePointer(final MouseEvent event) {

        // The menu is positioned relative to BoardPane, so 33 must be added to the actual
        // positon of the menu in the scene and normalize the pointer correctly
        mouseX = (int) (event.getSceneX() - (getLayoutX() + 33));

    }

    // Validate that the X coordinate is not out of bounds
    private boolean validateX() {

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
            setLayoutX((x * 32));
        } else {
            setLayoutX((x * 32) - 96);
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

        color = newColor;
        cacheSimpleX = -1;
        isShown = true;

    }

    // Populate the menu with ImageViews for each choice
    private void createViews(final ChessType newColor) {

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

        selectRect.setStroke(Color.TRANSPARENT);
        isShown = false;

    }

}
