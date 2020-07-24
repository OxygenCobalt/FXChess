// Pane that houses both StatBars

package org.oxycblt.chess.stats;

import javafx.scene.layout.Pane;

import org.oxycblt.chess.shared.ChessType;

import org.oxycblt.chess.board.BoardPane;

public class StatPane extends Pane {

    private BoardPane board = null;

    private StatBar blackBar;
    private StatBar whiteBar;

    public StatPane() {

        blackBar = new StatBar(ChessType.BLACK, "Player 1"); // Remove these placeholders!
        whiteBar = new StatBar(ChessType.WHITE, "Player 2");

        getChildren().addAll(blackBar, whiteBar);

    }

    public void changeTurn(final ChessType color) {

        if (color == ChessType.WHITE) {

            blackBar.deselect();
            whiteBar.select();

        } else {

            whiteBar.deselect();
            blackBar.select();

        }

    }

    public void addBoard(final BoardPane newBoard) {

        board = newBoard;

    }

}
