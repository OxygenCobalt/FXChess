// Rook chess piece

package org.oxycblt.chess.board.pieces;

import org.oxycblt.chess.shared.ChessType;
import org.oxycblt.chess.board.ChessList;

public class Rook extends ChessPiece {

    public Rook(final ChessList list,
                final ChessType color,
                final int x, final int y) {

        super(list, ChessType.ROOK, color, x, y);

    }

    @Override
    public boolean validateMove(final int targetX, final int targetY) {

        /*
        | Rooks can move straight in all directions, but not diagonally. They also cannot hop over
        | other pieces. Rooks are also involved in the Castling move with the King.
        */

        calculateDistance(targetX, targetY);

        if (xDist == 0 ^ yDist == 0) {

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

        // The rook does not need to be notified of any change

    }

}
