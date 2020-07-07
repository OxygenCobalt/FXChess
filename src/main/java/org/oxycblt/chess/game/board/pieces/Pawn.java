// Pawn chess piece

package org.oxycblt.chess.game.board.pieces;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;

public class Pawn extends ChessPiece {

    public Pawn(final ChessList list,
                final ChessType color,
                final int x, final int y) {

        super(list, ChessType.PAWN, color, x, y);

    }

    @Override
    public void validateMove(final int targetX, final int targetY) {

        moveIsValid = true;

    }

    @Override
    public void update(final ChessPiece changedPiece) {



    }

}
