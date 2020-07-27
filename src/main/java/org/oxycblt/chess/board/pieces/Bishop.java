// Bishop Chess Piece

package org.oxycblt.chess.board.pieces;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.board.ChessList;

public class Bishop extends ChessPiece {

    public Bishop(final ChessList list,
                  final ChessType color,
                  final int x, final int y) {

        super(list, ChessType.BISHOP, color, x, y);

    }

    @Override
    public boolean validateMove(final int targetX, final int targetY) {

        /*
        | Bishops can only move diagonally, but cannot jump over other pieces. They have no
        | special moves.
        */

        calculateDistance(targetX, targetY);

        xDist = Math.abs(xDist);
        yDist = Math.abs(yDist);

        if (xDist == yDist) {

            if (!findBlockingPieces(targetX, targetY)) {

                return true;

            }

        }

        return false;

    }

    @Override
    public void confirmMove(final int targetX, final int targetY) {

        doMove(targetX, targetY, list.findChessPiece(ChessType.inverseOf(color), targetX, targetY));

    }

    @Override
    public void update(final ChessPiece changedPiece) {

        // The bishop does not need to be notified of any change

    }

}
