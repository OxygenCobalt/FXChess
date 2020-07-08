// King Chess Piece

package org.oxycblt.chess.game.board.pieces;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;

public class King extends ChessPiece {

    public King(final ChessList list,
                final ChessType color,
                final int x, final int y) {

        super(list, ChessType.KING, color, x, y);

    }

    public boolean validateMove(final int targetX, final int targetY) {

        /*
        | Kings can move in all directions, as long as the distance moved is one.
        | // TODO // Kings can also perform a move called castling with a rook,
        | which involve the king moving two spaces, and the rook moving to the space
        | that the king passed.
        */

        calculateDistance(targetX, targetY);

        xDist = Math.abs(xDist);
        yDist = Math.abs(yDist);

        if (xDist + yDist < 2) {

            return true;

        } else if (xDist + yDist == 2) {

            if (xDist == yDist) {

                return true;

            }

        }

        return false;

    }

    public void confirmMove(final int targetX, final int targetY) {

        doMove(targetX, targetY, list.findChessPiece(ChessType.inverseOf(color), x, y));

    }

    public void update(final ChessPiece changedPiece) {

        // TODO: Add castling
        // TODO: Add check/checkmate

    }

}
