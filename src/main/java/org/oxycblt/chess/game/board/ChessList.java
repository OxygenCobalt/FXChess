// List of chess pieces

package org.oxycblt.chess.game.board;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.entity.EntityList;
import org.oxycblt.chess.game.board.pieces.ChessPiece;
import org.oxycblt.chess.entity.EntityRemovalListener;

public class ChessList extends EntityList<ChessPiece> {

    public ChessList(final EntityRemovalListener<ChessPiece> removeListener) {

        this.removeListener = removeListener;

    }

    // Search for a chess piece based on the pieces color and coordinates
    public ChessPiece findChessPiece(final ChessType color, final int x, final int y) {

        for (ChessPiece piece : entities) {

            if (piece.isMatching(color, x, y)) {

                return piece;

            }

        }

        return null;

    }

    // Update other chess pieces of a changed piece
    public void pushChange(final ChessPiece changedPiece) {

        for (ChessPiece piece : entities) {

            piece.update(changedPiece);

        }

    }

}
