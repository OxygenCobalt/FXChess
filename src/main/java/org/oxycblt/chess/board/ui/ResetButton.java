// Button that resets the board

package org.oxycblt.chess.board.ui;

import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

import org.oxycblt.chess.model.ChessType;

import org.oxycblt.chess.media.images.Texture;
import org.oxycblt.chess.media.images.TextureAtlas;

public class ResetButton extends Pane {

    private ResetListener listener;
    private SelectionRect selectRect = null;

    public ResetButton(final ResetListener listener) {

        relocate(-30, -46);
        setPrefSize(32, 32);
        setOnMouseClicked(confirmHandler);
        setOnMouseEntered(enterHandler);
        setOnMouseExited(exitHandler);

        this.listener = listener;

        getChildren().add(TextureAtlas.getTexture(Texture.BUTTON, 0, 0));

    }

    private EventHandler<MouseEvent> confirmHandler = event -> {

        if (event.getButton() == MouseButton.PRIMARY) {

            listener.onReset();

        }

    };

    private EventHandler<MouseEvent> enterHandler = event -> {

        if (selectRect == null) {

            selectRect = new SelectionRect(ChessType.WHITE, 0, 0);

        }

        getChildren().add(selectRect);

    };

    private EventHandler<MouseEvent> exitHandler = event -> {

        if (selectRect != null) {

            getChildren().remove(selectRect);

        }

    };

}
