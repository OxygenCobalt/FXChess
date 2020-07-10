// Queen chess piece

package org.oxycblt.chess.game.board.pieces;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;

public class Queen extends ChessPiece {

    public Queen(final ChessList list,
                 final ChessType color,
                 final int x, final int y) {

        super(list, ChessType.QUEEN, color, x, y);

    }

    @Override
    public boolean validateMove(final int targetX, final int targetY) {

        /*
        | Queens can move without restriction both straight and diagonally,
        | but cannot jump over other chess pieces. They have no special abilities.
        */

        calculateDistance(targetX, targetY);

        xDist = Math.abs(xDist);
        yDist = Math.abs(yDist);

        if ((xDist == 0 ^ yDist == 0) || (xDist == yDist)) {

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

        // The queen does not need to be notified of any change

    }

}
