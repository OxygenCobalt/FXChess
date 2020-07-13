// Pawn chess piece

package org.oxycblt.chess.game.board.pieces;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;

public class Pawn extends ChessPiece {

    private ChessPiece passPiece = null;

    public Pawn(final ChessList list,
                final ChessType color,
                final int x, final int y) {

        super(list, ChessType.PAWN, color, x, y);

    }

    @Override
    public boolean validateMove(final int targetX, final int targetY) {

        /*
        | A pawn can move foward to the unoccupied space directly in front of it, or
        | move two spaces on their first turn. Pawns can also move diagonally to capture
        | an opponents piece. A pawn can also perform a move called "En Passant", where
        | a pawn can move into the space that an opponents pawn just passed during a
        | two-space advance and capture that piece.
        */

        calculateDistance(targetX, targetY);

        xDist = Math.abs(xDist);

        /*
        | If a diagonal capture move is possible, return true instead of running
        | the other logic.
        */
        if (xDist + Math.abs(yDist) == 2) {

            if (list.findChessPiece(ChessType.inverseOf(color), targetX, targetY) != null) {

                return true;

            }

        }

        if (xDist != 0) {

            if (xDist == 1 && passPiece != null) {

                if (!(passPiece.getX() == targetX
                   && passPiece.getY() != targetY)) {

                    return false;

                }

            } else {

                return false;

            }

        }

        /*
        | Due to being on opposite sides of the board, white pieces and black pieces have
        | seperate logic, one to make sure the piece is always going up, and another to
        | make sure the piece is always going down.
        */

        if (color == ChessType.WHITE) {

            if (yDist < -1 || yDist > 0) {

                if (yDist == -2 && !hasMoved) {

                    if (list.findChessPiece(x, y - 2) != null) {

                        return false;

                    }

                } else {

                    return false;

                }

            }

            if (list.findChessPiece(x, y - 1) != null) {

                return false;

            }

        } else {

            if (yDist > 1 || yDist < 0) {

                if (yDist == 2 && !hasMoved) {

                    if (list.findChessPiece(x, y + 2) != null) {

                        return false;

                    }

                } else {

                    return false;

                }

            }

            if (list.findChessPiece(x, y + 1) != null) {

                return false;

            }

        }

        return true;

    }

    @Override
    public void confirmMove(final int targetX, final int targetY) {

        /*
        | If theres a piece that can be removed via En Passant,
        | knock that one out, otherwise just search for any chess pieces
        | that may be on the destinartion square, and knock them out instead
        */

        if (passPiece != null) {

            if (passPiece.getX() == targetX
            && passPiece.getY() != targetY) {

                doMove(targetX, targetY, passPiece);

                return;

            }

        }

        doMove(targetX, targetY, list.findChessPiece(ChessType.inverseOf(color), x, y));

    }

    @Override
    public void update(final ChessPiece changedPiece) {

        /*
        | If a pawn makes its only 2-square movement, and its the currently updated
        | pawn shares a Y coordinate & an adjacent X position, then its possible to
        | take the piece out w/an "En Passant" movement
        */

        passPiece = null;

        if (changedPiece.getType() == ChessType.PAWN
        && changedPiece.getColor() == ChessType.inverseOf(color)) {

            if (Math.abs(changedPiece.getX() - x) == 1
            && Math.abs(changedPiece.getYDist()) == 2
            && changedPiece.getY() == y) {

                passPiece = changedPiece;

            }

        }

    }

}
