// Pawn chess piece

package org.oxycblt.chess.game.board.pieces;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;

public class Pawn extends ChessPiece {

    private ChessPiece passPiece = null;

    private boolean xValid = false;
    private boolean yValid = false;

    public Pawn(final ChessList list,
                final ChessType color,
                final int x, final int y) {

        super(list, ChessType.PAWN, color, x, y);

    }

    @Override
    public boolean validateMove(final int targetX, final int targetY) {

        /*
        | A pawn can only move foward, one square at a time, except for its first move,
        | where moving two squares is valid as long as the entire path is unoccupied.
        | Pawns can also perform a move called en passant, where they can capture a pawn
        | that has just advanced two squares by moving to the square that they passed
        | during their move.
        */

        // TODO: Also add promotion later on

        calculateDistance(targetX, targetY);

        xDist = Math.abs(xDist);

        xValid = true;
        yValid = true;

        if (xDist != 0) {

            xValid = false;

            if (xDist == 1 && passPiece != null) {

                if (passPiece.getX() == targetX
                 && passPiece.getY() != targetY) {

                    xValid = true;

                }

            }

        }

        /*
        | Due to being on opposite sides of the board, white pieces and black pieces have
        | seperate logic, one to make sure the piece is always going up, and another to
        | make sure the piece is always going down.
        */

        if (color == ChessType.WHITE) {

            if (yDist < -1 || yDist > 0) {

                yValid = false;

                if (yDist == -2 && !hasMoved) {

                    if (list.findChessPiece(x, y - 1) == null
                    && list.findChessPiece(x, y - 2) == null) {

                        yValid = true;

                    }

                }

            }

        } else {

            if (yDist > 1 || yDist < 0) {

                yValid = false;

                if (yDist == 2 && !hasMoved) {

                    if (list.findChessPiece(x, y + 1) == null
                    && list.findChessPiece(x, y + 2) == null) {

                        yValid = true;

                    }

                }

            }

        }

        return xValid && yValid;

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
