// List of chess pieces

package org.oxycblt.chess.game.board;

import java.util.ArrayList;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.pieces.King;
import org.oxycblt.chess.game.board.pieces.ChessPiece;

import org.oxycblt.chess.entity.EntityList;
import org.oxycblt.chess.entity.EntityChangeListener;

public class ChessList extends EntityList<ChessPiece> {

    private ArrayList<ChessPiece> killedPieces;

    public ChessList(final EntityChangeListener<ChessPiece> listener) {

        super(listener);

        killedPieces = new ArrayList<ChessPiece>();

    }

    // Override of removeEntity that also adds the piece to a list of killed pieces
    @Override
    public void removeEntity(final ChessPiece piece) {

        super.removeEntity(piece);

        killedPieces.add(piece);

    }

    // Search for a chess piece based on the pieces color and coordinates
    public ChessPiece findChessPiece(final ChessType color, final int x, final int y) {

        for (ChessPiece piece : entities) {

            if (piece.getColor() == color
            &&  piece.getX() == x
            &&  piece.getY() == y) {

                return piece;

            }

        }

        return null;

    }

    // Or search for a chess piece with just the coordinates
    public ChessPiece findChessPiece(final int x, final int y) {

        for (ChessPiece piece : entities) {

            if (piece.getX() == x
            &&  piece.getY() == y) {

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

    // TODO: Add cool looking reset functionality

    // Reset all pieces
    public void resetAll() {

        for (ChessPiece piece : entities) {

            piece.reset();

            /*
            | Also make sure that the kings get their checked status reset as well to prevent any
            | weird bugs with end conditions and animations
            */
            if (piece.getType() == ChessType.KING) {

                ((King) piece).resetChecked();

            }

        }

        killedPieces.clear();

    }

    public ArrayList<ChessPiece> getKilledPieces() {

        return killedPieces;

    }

}
