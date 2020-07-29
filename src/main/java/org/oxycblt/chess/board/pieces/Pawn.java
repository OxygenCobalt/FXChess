// Pawn chess piece

package org.oxycblt.chess.board.pieces;

import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.board.ChessList;

public class Pawn extends ChessPiece {

    private ChessPiece passPiece = null;

    private final int relOne;
    private final int relTwo;

    private boolean did2Step = false;
    private boolean vmwpReturn = false;
    private boolean assumePieceExists = false;

    public Pawn(final ChessList list,
                final ChessType color,
                final int x, final int y) {

        super(list, ChessType.PAWN, color, x, y);

        /*
        | Determine which placeholder values to use in validateMove() to make sure that the pawn is
        | always moving forward. Up for white pieces, down for black pieces.
        */
        if (color == ChessType.WHITE) {

            relOne = -1;
            relTwo = -2;

        } else {

            relOne = 1;
            relTwo = 2;

        }

    }

    @Override
    public boolean validateMove(final int targetX, final int targetY) {

        /*
        | A pawn can move forward to the unoccupied space directly in front of it, or move two
        | spaces on their first turn. Pawns can however move diagonally one space at a time
        | to capture  an opponents piece. A pawn can also perform a move called "En Passant",
        | where a pawn can move into the space that an opponents pawn just passed during a
        | two-space advance and capture that piece.
        */
        calculateDistance(targetX, targetY);

        if (Math.abs(xDist) == 1 && yDist == relOne) {

            if (passPiece != null) {

                if (passPiece.getX() == targetX
                 && passPiece.getY() != targetY) {

                    return true;

                }

            }

            if (list.findChessPiece(ChessType.inverseOf(color), targetX, targetY) != null
                || assumePieceExists) {

                return true;

            }

            return false;

        } else if (xDist != 0) {

            return false;

        }

        if (yDist == relOne) {

            if (list.findChessPiece(targetX, targetY) == null
             ^ assumePieceExists) {

                return true;

            }

        } else if (yDist == relTwo && !hasMoved) {

            if ((list.findChessPiece(targetX, targetY - relOne) == null
            &&  list.findChessPiece(targetX, targetY) == null)
             ^  assumePieceExists) {

                return true;

            }

        }

        return false;

    }

    /*
    | Validate a move while assuming that a piece can be captured using diagonal capture, this is
    | used to check if a King's target destination wont result in a check through a pawn.
    */
    public boolean validateMoveWithPiece(final int targetX, final int targetY) {

        assumePieceExists = true;

        vmwpReturn = validateMove(targetX, targetY);

        assumePieceExists = false;

        return vmwpReturn;

    }

    @Override
    public void confirmMove(final int targetX, final int targetY) {

        /*
        | If the piece has just made a 2-step advance, set did2Step to true so that other
        | pieces can perform En Passant properly. This variable is set back to false in
        | the next move.
        */
        did2Step = false;

        if (Math.abs(yDist) == 2) {

            did2Step = true;

        }

        /*
        | If theres a piece that can be removed via En Passant, knock that one out, otherwise just
        | search for any chess pieces that may be on the destination square, and knock them out
        | instead.
        */
        if (passPiece != null) {

            if (passPiece.getX() == targetX
            && passPiece.getY() != targetY) {

                doMove(targetX, targetY, passPiece);

                return;

            }

        }

        doMove(targetX, targetY, list.findChessPiece(ChessType.inverseOf(color), targetX, targetY));

    }

    @Override
    public void update(final ChessPiece changedPiece) {

        /*
        | If a pawn makes its only 2-square movement, and its the currently updated pawn shares
        | a Y coordinate & an adjacent X position, then its possible to take the piece out w/an
        | "En Passant" movement
        */

        passPiece = null;

        if (changedPiece.getType() == ChessType.PAWN
        && changedPiece.getColor() != color) {

            if (Math.abs(changedPiece.getX() - x) == 1
            && ((Pawn) changedPiece).get2Step()
            && changedPiece.getY() == y) {

                passPiece = changedPiece;

            }

        }

    }

    // Getters
    public boolean get2Step() {

        return did2Step;

    }

}
