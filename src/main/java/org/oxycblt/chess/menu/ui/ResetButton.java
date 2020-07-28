// Button that resets the board

package org.oxycblt.chess.menu.ui;

import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

import org.oxycblt.chess.model.ChessType;

import org.oxycblt.chess.media.images.Texture;
import org.oxycblt.chess.media.images.TextureAtlas;

import org.oxycblt.chess.board.ui.SelectionRect;
import org.oxycblt.chess.menu.MenuPane;

public class ResetButton extends Pane {

    private MenuPane parent;
    private SelectionRect selectRect = null;

    public ResetButton(final MenuPane parent) {

        relocate(3, 3);
        setPrefSize(32, 32);
        setOnMouseClicked(confirmHandler);
        setOnMouseEntered(enterHandler);
        setOnMouseExited(exitHandler);

        this.parent = parent;

        getChildren().add(TextureAtlas.getTexture(Texture.BUTTON, 0, 0));

    }

    private EventHandler<MouseEvent> confirmHandler = event -> {

        if (event.getButton() == MouseButton.PRIMARY) {

            parent.onReset();

        }

    };

    private EventHandler<MouseEvent> enterHandler = event -> {

        if (selectRect == null) {

            selectRect = new SelectionRect(ChessType.WHITE, 0, 0);

            getChildren().add(selectRect);

        } else {

            selectRect.show(ChessType.WHITE);

        }

    };

    private EventHandler<MouseEvent> exitHandler = event -> {

        if (selectRect != null) {

            selectRect.hide();

        }

    };

}
