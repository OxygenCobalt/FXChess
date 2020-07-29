// Bar for the main buttons and the end screen

package org.oxycblt.chess.menu;

import javafx.scene.layout.Pane;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.model.EndType;

import org.oxycblt.chess.board.BoardPane;

import org.oxycblt.chess.menu.ui.EndScreen;
import org.oxycblt.chess.menu.ui.ResetButton;
import org.oxycblt.chess.menu.ui.TurnIndicator;

public class MenuPane extends Pane {

    private BoardPane board;

    private TurnIndicator turn = null;
    private EndScreen endScreen = null;

    public MenuPane() {

        setPrefSize(276, 35);

        getChildren().addAll(
            new ResetButton(this)
        );

    }

    // Change the turn indicator of the MenuPane
    public void changeTurn(final ChessType newColor) {

        if (turn == null) {

            turn = new TurnIndicator(newColor);

            getChildren().add(turn);

        } else {

            turn.update(newColor);

        }

    }

    // Reset the MenuPane & the Board
    public void onReset() {

        board.onReset();

        if (endScreen != null) {

            endScreen.hide();

        }

    }

    // Update the MenuPane of a game end
    public void onEnd(final ChessType winColor, final EndType type) {

        if (endScreen == null) {

            endScreen = new EndScreen(winColor);

            getChildren().add(endScreen);

        } else {

            endScreen.show(winColor);

        }

    }

    public void addBoard(final BoardPane newBoard) {

        board = newBoard;

    }

}
