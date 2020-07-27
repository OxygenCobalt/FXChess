// List of chess pieces

package org.oxycblt.chess.board;

import java.util.ArrayList;

import org.oxycblt.chess.model.ChessType;

import org.oxycblt.chess.board.pieces.King;
import org.oxycblt.chess.board.pieces.ChessPiece;

import org.oxycblt.chess.entity.EntityList;
import org.oxycblt.chess.entity.EntityChangeListener;

public class ChessList extends EntityList<ChessPiece> {

    private ArrayList<ChessPiece> killedPieces;
    private ArrayList<ChessPiece> promotedPieces;

    public ChessList(final EntityChangeListener<ChessPiece> listener) {

        super(listener);

        killedPieces = new ArrayList<ChessPiece>();
        promotedPieces = new ArrayList<ChessPiece>();

    }

    // Kill a piece, adding it to a list of killed pieces
    public void killPiece(final ChessPiece piece) {

        super.removeEntity(piece);

        killedPieces.add(piece);

    }

    // Add a newly promoted piece
    public void addPromotedPiece(final ChessPiece piece) {

        promotedPieces.add(piece);

        pushChange(piece);

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

    // Check if a turn's king is checked
    public boolean isTurnChecked(final ChessType turn) {

        for (ChessPiece piece : entities) {

            if (piece.getType() == ChessType.KING
            &&  piece.getColor() == turn) {

                return ((King) piece).getChecked();

            }

        }

        return false;

    }

    // Update other chess pieces of a changed piece
    public void pushChange(final ChessPiece changedPiece) {

        for (ChessPiece piece : entities) {

            piece.update(changedPiece);

        }

    }

    // Reset all pieces
    public void resetAll() {

        // Remove all promoted pieces first, as the pawns they game from will be unkilled
        for (ChessPiece piece : promotedPieces) {

            piece.kill();

            super.removeEntity(piece);

        }

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

    // Getters
    public ArrayList<ChessPiece> getKilledPieces() {

        return killedPieces;

    }

}
