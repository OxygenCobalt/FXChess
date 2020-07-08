// Rook chess piece

package org.oxycblt.chess.game.board.pieces;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;

public class Rook extends ChessPiece {

    public Rook(final ChessList list,
                final ChessType color,
                final int x, final int y) {

        super(list, ChessType.ROOK, color, x, y);

    }

    public void validateMove(final int targetX, final int targetY) {

        /*
        | Rooks can move straight in all directions, but not diagonally. They also cannot hop over
        | other pieces. // TODO// Rooks can perform a move called Castling once per game with the
        | king piece, where the rook will move to the square the king just crossed during the
        | manuver.
        */

        moveIsValid = false;

        calculateDistance(targetX, targetY);

        if (xDist == 0 ^ yDist == 0) {

            if (!findBlockingPieces(targetX, targetY)) {

                moveIsValid = true;

            }

        }

    }

    public void confirmMove(final int targetX, final int targetY) {

        doMove(targetX, targetY, list.findChessPiece(ChessType.inverseOf(color), x, y));

    }

    public void update(final ChessPiece changedPiece) {

        // TODO: Add castling

    }

}
