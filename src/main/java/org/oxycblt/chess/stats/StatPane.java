// Pane that houses both StatBars

package org.oxycblt.chess.stats;

import javafx.scene.layout.Pane;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.model.EndType;

import org.oxycblt.chess.board.BoardPane;

public class StatPane extends Pane {

    private BoardPane board = null;

    private StatBar blackBar;
    private StatBar whiteBar;

    private boolean isDisabled;

    public StatPane() {

        blackBar = new StatBar(ChessType.BLACK);
        whiteBar = new StatBar(ChessType.WHITE);

        getChildren().addAll(blackBar, whiteBar);

    }

    public void changeTurn(final ChessType color) {

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

    public void onEnd(final ChessType winColor, final EndType type) {

        blackBar.onEnd(winColor, type);
        whiteBar.onEnd(winColor, type);

        isDisabled = true;

    }

    public void addBoard(final BoardPane newBoard) {

        board = newBoard;

    }

}
