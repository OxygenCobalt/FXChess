// Bishop Chess Piece

package org.oxycblt.chess.game.board.pieces;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;

public class Bishop extends ChessPiece {

    public Bishop(final ChessList list,
                  final ChessType color,
                  final int x, final int y) {

        super(list, ChessType.BISHOP, color, x, y);

    }

    public void validateMove(final int targetX, final int targetY) {

        /*
        | Bishops can only move diagonally, and cannot jump over
        | other pieces. They have no special moves.
        */

        moveIsValid = false;

        calculateDistance(targetX, targetY);

        xDist = Math.abs(xDist);
        yDist = Math.abs(yDist);

        if (xDist == yDist) {

            if (!findBlockingPieces(targetX, targetY)) {

                moveIsValid = true;

            }

        }

    }

    public void confirmMove(final int targetX, final int targetY) {

        doMove(targetX, targetY, list.findChessPiece(ChessType.inverseOf(color), x, y));

    }

    public void update(final ChessPiece changedPiece) {

        // The bishop does not need to be notified of any change

    }

}
