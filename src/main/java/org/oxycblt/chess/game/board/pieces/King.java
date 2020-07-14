// King Chess Piece

package org.oxycblt.chess.game.board.pieces;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;

public class King extends ChessPiece {

    private ChessPiece leftRook;
    private ChessPiece rightRook;

    private ChessPiece checkingPiece = null;
    private boolean isChecked = false;

    public King(final ChessList list,
                final ChessType color,
                final int x, final int y) {

        super(list, ChessType.KING, color, x, y);

    }

    @Override
    public boolean validateMove(final int targetX, final int targetY) {

        /*
        | Kings can move in all directions, as long as the distance moved is one.
        | Kings can also perform a move called castling with a rook, which involve
        | the king moving two spaces, and the rook moving to the space that the king passed.
        */

        deselectRooks();

        if (doDistanceLogic(targetX, targetY)) {

            return validateSafe(targetX, targetY);

        }

        /*
        | Castling Logic
        |
        | The conditions for castling to be valid are:
        | - The king must not have moved or is in check
        | - The rooks must have not moved
        | - There must be no pieces in the path between the king & the rook
        | - The king must not pass through any pieces that are under attack, or result in a check
        */
        if (yDist == 0 && !hasMoved && checkingPiece == null) {

            if (targetX == 2 && !leftRook.getMoved()) {

                if (!findBlockingPieces(targetX - 2, targetY)) {

                    if (validateSafePath(targetX)) {

                        leftRook.setSelected(true);

                        return true;

                    }

                }

            } else if (targetX == 6 && !rightRook.getMoved()) {

                if (!findBlockingPieces(targetX + 1, targetY)) {

                    if (validateSafePath(targetX)) {

                        rightRook.setSelected(true);

                        return true;

                    }

                }

            }

        }

        return false;

    }

    // Distance logic, seperated in order to prevent recursion errors in validateSafe().
    // Returns true if valid, false if not
    public boolean doDistanceLogic(final int targetX, final int targetY) {

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

    // Validate that a prospective move is safe
    // Returns true if yes, false if no
    private boolean validateSafe(final int targetX, final int targetY) {

        for (ChessPiece entity : list.getEntities()) {

            if (entity.getColor() != color) {

                // Pawns need to assume that the king is already at the location
                // it wants to go to in order to check for diagonal captures.
                if (entity.getType() == ChessType.PAWN) {

                    if (((Pawn) entity).validateMoveWithPiece(targetX, targetY)) {

                        return false;

                    }

                // If checking a king, only do the distance logic to prevent endless
                // recursive calls to validateSafe()
                } else if (entity.getType() == ChessType.KING) {

                    if (((King) entity).doDistanceLogic(targetX, targetY)) {

                        return false;

                    }

                } else {

                    if (entity.validateMove(targetX, targetY)) {

                        return false;

                    }

                }

            }

        }

        return true;

    }

    // Validate that a path is safe and wont result in a check, used for castling
    // Returns true if path is safe, false if not
    public boolean validateSafePath(final int targetX) {

        iterX = x;

        while (iterX != targetX) {

            if (iterX > targetX) {

                iterX--;

            } else if (iterX < targetX) {

                iterX++;

            }

            if (!validateSafe(iterX, y)) {

                return false;

            }

        }

        return true;

    }

    @Override
    public void confirmMove(final int targetX, final int targetY) {

        calculateDistance(targetX, targetY);

        // If a castling move is performed, also move the rook to their new location
        if (Math.abs(xDist) == 2 && yDist == 0) {

            if (xDist < 0) {

                leftRook.confirmMove(targetX + 1, targetY);
                leftRook.setSelected(false);

            } else {

                rightRook.confirmMove(targetX - 1, targetY);
                rightRook.setSelected(false);

            }

            doMove(targetX, targetY, null);

            return;

        }

        doMove(targetX, targetY, list.findChessPiece(ChessType.inverseOf(color), x, y));

    }

    @Override
    public void update(final ChessPiece changedPiece) {

        // TODO: Add check/checkmate

        // WIP Rules:
        // - Checkmate, either from one move from opposing side or failure to counter check
        // - Stalemate, no moves possible on one sides turn.
        // - 50-Move rule, no captures or pawn moves in 50 moves -> automatic draw

        // Check if the current position of the king is safe. If not, then set the king
        // as checked, and check if theres any way out of the check. If not, its an
        // automatic checkmate. Is there is a way out, the player must figure it out
        // within the next move, otherwise its still an auto-checkmate.
        checkingPiece = findCheckingPieces();

        if (checkingPiece != null) {

            if (isChecked) {

                System.out.println("Checkmate");

            } else {

                System.out.println("Checked.");

                isChecked = true;

            }

        } else {

            System.out.println("Unchecked.");

            isChecked = false;

        }

    }

    // Variant of validateSafe() that returns chesspieces instead of a boolean
    // Returns a chesspiece that checks the king, otherwise nothing
    private ChessPiece findCheckingPieces() {

        for (ChessPiece entity : list.getEntities()) {

            if (entity.getColor() != color) {

                if (entity.getType() == ChessType.KING) {

                    if (((King) entity).doDistanceLogic(x, y)) {

                        return entity;

                    }

                } else {

                    if (entity.validateMove(x, y)) {

                        return entity;

                    }

                }

            }

        }

        return null;

    }

    // Notify king of the rooks after the home row has been generated
    public void setUpRooks() {

        leftRook = list.findChessPiece(color, 0, y);
        rightRook = list.findChessPiece(color, 7, y);

    }

    // Deselect the stored rooks
    public void deselectRooks() {

        leftRook.setSelected(false);
        rightRook.setSelected(false);

    }

}
