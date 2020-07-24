// Menu used for pawn promotion

package org.oxycblt.chess.game.board.ui;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.animation.FadeAnimation;

import org.oxycblt.chess.media.images.Texture;
import org.oxycblt.chess.media.images.TextureAtlas;

public class PromotionMenu extends Pane {

    private boolean isShown = false;

    private PromotionEndListener listener;
    private ChessType color;

    private SelectionRect selectRect = null;
    private FadeAnimation fadeAnim;
    private ImageView[] choiceViews;

    private int mouseX = 0;
    private int mouseY = 0;
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

        fadeAnim = new FadeAnimation(this);

        show(color, x, y);

    }

    EventHandler<MouseEvent> confirmHandler = event -> {

        MouseButton button = event.getButton();

        if (button == MouseButton.PRIMARY && isShown) {

            normalizePointer(event);

            if (validateXY()) {

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

            if (validateXY()) {

                updateSimpleX();

                // Make sure the mouse coordinate has meaningfully changed
                if (simpleX != cacheSimpleX) {

                    if (selectRect == null) {

                        selectRect = new SelectionRect(color, simpleX, 0);

                        getChildren().add(selectRect);

                    } else {

                        selectRect.relocate(simpleX * 32, 0);

                        if (!selectRect.getShown()) {

                            selectRect.show(color);

                        }

                    }

                    cacheSimpleX = simpleX;

                }

            // Hide the selection rect if the mouse is outside of the menu
            } else if (selectRect != null)  {

                if (selectRect.getShown()) {

                    selectRect.hide();

                    cacheSimpleX = -1;

                }

            }

        }

    };

    // Normalize a mouse pointer so that the coordinates are solely within the bounds of the menu
    private void normalizePointer(final MouseEvent event) {

        // The menu is positioned relative to BoardPane, so 10/59 must be added to the actual
        // position of the pointer in the scene and normalize the pointer correctly
        mouseX = (int) (event.getSceneX() - (getLayoutX() + 10));
        mouseY = (int) (event.getSceneY() - (getLayoutY() + 59));

    }

    // Validate that the X coordinate is not out of bounds
    // Returns true if valid, false if not
    private boolean validateXY() {

        return mouseX > 0 && mouseX < getPrefWidth()
            && mouseY > 0 && mouseY < getPrefHeight();

    }

    // Update the simple X coordinate from the mouse X coordinate
    private void updateSimpleX() {

        simpleX = mouseX / 32;

    }

    // Show the menu
    public void show(final ChessType newColor, final int x, final int y) {

        ChessType.validateColor(newColor);

        // The menu is aligned differently depending on which half of the board pawn is.
        if (x < 4) {
            setLayoutX((x * 32));
        } else {
            setLayoutX((x * 32) - 96);
        }

        /*
        | WHITE pawns are promoted when they reach the top of the board, so align the menu to be
        | on top of them. BLACK pawns are promoted when they reach the bottom of the board, so
        | the menu is aligned to be below them.
        */
        if (newColor == ChessType.WHITE) {
            setLayoutY((y * 32) - 38);
        } else {
            setLayoutY((y * 32) + 38);
        }

        createViews(newColor);
        fadeAnim.fadeIn();

        color = newColor;
        cacheSimpleX = -1;
        isShown = true;

    }

    // Populate the menu with ImageViews for each choice
    private void createViews(final ChessType newColor) {

        /*
        | If the color has changed or if the list of views hasn't been created yet,
        | regenerate/create the ImageViews with each choice
        */
        if (newColor != color || choiceViews == null) {

            if (choiceViews != null) {

                getChildren().removeAll(choiceViews);

            }

            choiceViews = new ImageView[4];

            for (int i = 0; i < 4; i++) {

                // Add the texture for each chess piece available in the promotion choices.
                choiceViews[i] = TextureAtlas.getTexture(
                    Texture.CHESS_PIECES,
                    ChessType.PROMOTION_ORDER[i].getCoordinate(),
                    newColor.getCoordinate()
                );

                choiceViews[i].setX(i * 32);

            }

            getChildren().addAll(choiceViews);

        }

    }

    // Hide the menu
    public void hide() {

        if (isShown) {

            if (selectRect != null) {

                selectRect.hide();

            }

            toBack();
            fadeAnim.fadeOut();
            isShown = false;

        }

    }

}
