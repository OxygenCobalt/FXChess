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

    private PromotionEndListener listener;

    private final ChessType[] promotionChoices = new ChessType[]{
        ChessType.ROOK, ChessType.KNIGHT, ChessType.BISHOP, ChessType.QUEEN
    };

    private boolean isShown;

    private ChessType color;
    private ImageView[] choiceViews;
    private int i = 0;

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

                // Find the choice that matches the current
                // position of the mouse, and confirm that
                updateSimpleX();

                listener.onPromotionEnd(promotionChoices[simpleX - 1]);

                hide();

            }

        }

    };

    EventHandler<MouseEvent> hoverHandler = event -> {

        // TODO: Add selection rect [As its own object]

    };

    // Normalize a mouse pointer so that the coordinates are solely within the bounds of the
    private void normalizePointer(final MouseEvent event) {

        mouseX = (int) (event.getSceneX() - getLayoutX());

    }

    // Validate that the X coordinate is not out of bounds
    private boolean validateXY() {

        return mouseX > 0 && mouseX < getPrefWidth();

    }

    // Update the simple X coordinate from the mouse coords
    private void updateSimpleX() {

        simpleX = mouseX / 32;

    }

    // Show the menu
    public void show(final ChessType newColor,
                     final int x, final int y) {

        if (newColor != ChessType.BLACK && newColor != ChessType.WHITE) {

            throw new IllegalArgumentException("Given color is not BLACK or WHITE");

        }

        // Depending on which side of the board the pawn is,
        // show a different orientation of the menu
        if (x > 4) {

            relocate((x * 32) - 64, 0);

        } else {

            relocate((x * 32) - 32, 0);

        }

        if (newColor == ChessType.WHITE) {

            relocate(getLayoutX(), (y * 32) - 34);

        } else {

            relocate(getLayoutX(), (y * 32) + 34);

        }

        if (color != newColor) {

            createViews(newColor);

        }

        isShown = true;
        color = newColor;

    }

    // Hide the menu
    public void hide() {

        getChildren().removeAll(choiceViews);

        toBack();

        isShown = false;

    }

    // Create the images for each choice
    private void createViews(final ChessType newColor) {

        // Reset the list of views, as this function is always
        // called when the object is either constructed or has to change color
        choiceViews = new ImageView[4];
        i = 0;

        for (ChessType choice : promotionChoices) {

            // For every option avalible for promotion, make an image for them
            choiceViews[i] = TextureAtlas.getTexture(
                Texture.CHESS_PIECES,
                choice.getTextureCoordinate(),
                color.getTextureCoordinate()
            );

            choiceViews[i].relocate(i * 32, 0);

            i++;

        }

        getChildren().addAll(choiceViews);

    }

}
