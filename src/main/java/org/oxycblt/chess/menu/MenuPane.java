// Bar for the main buttons and the end screen

package org.oxycblt.chess.menu;

import javafx.scene.layout.Pane;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.model.EndType;

import org.oxycblt.chess.board.BoardPane;

import org.oxycblt.chess.menu.ui.ResetButton;

public class MenuPane extends Pane {

    private BoardPane board;

    public MenuPane() {

        setPrefSize(276, 32);

        getChildren().addAll(
            new ResetButton(this)
        );

    }

    // Change the turn indicator of the MenuPane
    public void changeTurn(final ChessType newColor) {



    }

    public void onReset() {

        board.onReset();

    }

    // Update the MenuPane of a game end
    public void onEnd(final ChessType winColor, final EndType type) {



    }

    public void addBoard(final BoardPane newBoard) {

        board = newBoard;

    }

}
