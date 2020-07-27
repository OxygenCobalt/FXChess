// Knight Chess Piece

package org.oxycblt.chess.board.pieces;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.board.ChessList;

public class Knight extends ChessPiece {

    public Knight(final ChessList list,
                  final ChessType color,
                  final int x, final int y) {

        super(list, ChessType.KNIGHT, color, x, y);

    }

    @Override
    public boolean validateMove(final int targetX, final int targetY) {

        /*
        | Knights can move in an L-Shape in all directions, regardless of if any pieces are in the
        | way. They have no special moves.
        */

        calculateDistance(targetX, targetY);

        xDist = Math.abs(xDist);
        yDist = Math.abs(yDist);

        if ((xDist == 1 && yDist == 2) || (xDist == 2 && yDist == 1)) {

            return true;

        }

        return false;

    }

    @Override
    public void confirmMove(final int targetX, final int targetY) {

        doMove(targetX, targetY, list.findChessPiece(ChessType.inverseOf(color), targetX, targetY));

    }

    @Override
    public void update(final ChessPiece changedPiece) {

        // The knight does not need to be notified of any change

    }

}
