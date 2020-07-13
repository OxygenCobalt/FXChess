// Rook chess piece

package org.oxycblt.chess.game.board.pieces;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;

public class Rook extends ChessPiece {

    private King king;

    public Rook(final ChessList list,
                final ChessType color,
                final int x, final int y) {

        super(list, ChessType.ROOK, color, x, y);

    }

    @Override
    public boolean validateMove(final int targetX, final int targetY) {

        /*
        | Rooks can move straight in all directions, but not diagonally. They also cannot hop over
        | other pieces. // TODO// Rooks can perform a move called Castling once per game with the
        | king piece, where the rook will move to the square the king just crossed during the
        | maneuver.
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

        doMove(targetX, targetY, list.findChessPiece(ChessType.inverseOf(color), x, y));

    }

    @Override
    public void update(final ChessPiece changedPiece) {

        // TODO: Add castling

    }

    // Set up a reference to the king once the home row has been generated
    public void setUpKing(final King mainKing) {

        king = mainKing;

    }

}
