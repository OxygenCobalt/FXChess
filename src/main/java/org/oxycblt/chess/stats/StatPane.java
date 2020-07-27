// Pane that houses both StatBars

package org.oxycblt.chess.stats;

import javafx.scene.layout.Pane;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.model.EndType;

import org.oxycblt.chess.board.BoardPane;

public class StatPane extends Pane {

    private BoardPane board;

    private StatBar blackBar;
    private StatBar whiteBar;

    private boolean isDisabled = false;

    public StatPane() {

        blackBar = new StatBar(ChessType.BLACK);
        whiteBar = new StatBar(ChessType.WHITE);

        getChildren().addAll(blackBar, whiteBar);

    }

    // Change the turn/the currently selected StatBar
    public void changeTurn(final ChessType color) {

        /*
        | Dont change the turn if the Pane has been disabled through onEnd(), to prevent a bug where
        | it will change turn even after delecting both StatBars.
        */
        if (!isDisabled) {

            if (color == ChessType.WHITE) {

                blackBar.deselect();
                whiteBar.select();

            } else {

                whiteBar.deselect();
                blackBar.select();

            }

        }

    }

    // Update the StatBar of a game end
    public void onEnd(final ChessType winColor, final EndType type) {

        blackBar.onEnd(winColor, type);
        whiteBar.onEnd(winColor, type);

        isDisabled = true;

    }

    // Add the reference to BoardPane
    public void addBoard(final BoardPane newBoard) {

        board = newBoard;

    }

}
