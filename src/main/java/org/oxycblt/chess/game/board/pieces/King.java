// King Chess Piece

package org.oxycblt.chess.game.board.pieces;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;
import org.oxycblt.chess.game.board.EndListener;
import org.oxycblt.chess.game.board.EndListener.EndType;

public class King extends ChessPiece {

    private ChessPiece leftRook;
    private ChessPiece rightRook;

    private EndListener endListener;

    private ChessPiece checkingPiece = null;
    private boolean isChecked = false;

    private int checkX = 0;
    private int checkY = 0;

    public King(final ChessList list,
                final ChessType color,
                final int x, final int y) {

        super(list, ChessType.KING, color, x, y);

    }

    @Override
    public boolean validateMove(final int targetX, final int targetY) {

        /*
        | Kings can move in all directions, as long as the distance moved is one and the space is
        | safe [Although that doesn't apply if the king is checked, for added challenge]. Kings can
        | also perform a move called castling with a rook, which involve the king moving two
        | spaces, and the rook moving to the space that the king passed.
        */

        if (doDistanceLogic(targetX, targetY)) {

            if (isChecked) {

                return true;

            } else {

                return validateSafe(targetX, targetY);

            }

        }

        /*
        | Castling Logic
        |
        | The conditions for castling to be valid are:
        | - The king must not have moved or is in check
        | - The rooks must have not moved
        | - There must be no pieces in the path between the king & the rook
        | - The king must not pass through any pieces that are under attack
        */
        if (yDist == 0 && !hasMoved && checkingPiece == null) {

            if (targetX == 2 && !leftRook.getMoved()) {

                if (!findBlockingPieces(targetX - 2, targetY)) {

                    if (validateSafePath(targetX)) {

                        return true;

                    }

                }

            } else if (targetX == 6 && !rightRook.getMoved()) {

                if (!findBlockingPieces(targetX + 1, targetY)) {

                    if (validateSafePath(targetX)) {

                        return true;

                    }

                }

            }

        }

        return false;

    }

    // Distance logic, separated in order to prevent recursion errors in validateSafe().
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

    @Override
    public void confirmMove(final int targetX, final int targetY) {

        calculateDistance(targetX, targetY);

        // If a castling move is performed, also move the rook to their new location
        if (Math.abs(xDist) == 2 && yDist == 0) {

            if (xDist < 0) {

                leftRook.confirmMove(targetX + 1, targetY);

            } else {

                rightRook.confirmMove(targetX - 1, targetY);

            }

            doMove(targetX, targetY, null);

            return;

        }

        doMove(targetX, targetY, list.findChessPiece(ChessType.inverseOf(color), targetX, targetY));

    }

    @Override
    public void update(final ChessPiece changedPiece) {

        /*
        | Check if the current position of the king is safe. If not, then set the king
        | as checked, and check if theres any way out of the check. If not, its an
        | automatic checkmate. Is there is a way out, the player must figure it out
        | within the next move, otherwise its still an auto-checkmate. If you make
        | a move from another piece that results in a check, its an auto-checkmate as well.
        | The above is actually illegal to do in formal chess rules, but it would be a
        | nightmare to implement so its just a checkmate instead ¯\_(ツ)_/¯
        */
        checkingPiece = findCheckingPieces();

        if (checkingPiece != null) {

            if (isChecked) {

                endListener.onEnd(ChessType.inverseOf(color), EndType.CHECKMATE);

            } else {

                if (validateCheckmate()) {

                    endListener.onEnd(ChessType.inverseOf(color), EndType.CHECKMATE);

                } else {

                    if (changedPiece.getColor() == color) {

                        endListener.onEnd(ChessType.inverseOf(color), EndType.CHECKMATE);

                    }

                    isChecked = true;

                }

            }

            return;

        } else if (isChecked) {

            isChecked = false;

        }

        /*
        | If the king is current unchecked, the king also has to check if theres any valid moves
        | left in general [If its the King's turn, that is]. If not, then its a stalemate.
        */
        if (changedPiece.getColor() != color) {

            if (!validatePossibleMoves()) {

                endListener.onEnd(color, EndType.STALEMATE);

            }

        }

    }

    // Variant of validateSafe() that returns chess pieces instead of a boolean
    // Returns a chess piece that checks the king, otherwise nothing
    public ChessPiece findCheckingPieces() {

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

    // Check for any moves are possible if the king is checked
    // Returns true if yes, false if no
    public boolean validateCheckmate() {

        /*
        | First, iterate through each of the kings moves and check if any of those will result in
        | the check ending.
        */
        for (int nearX = Math.max(x - 1, 0); nearX < Math.min(x + 2, 8); nearX++) {

            for (int nearY = Math.max(y - 1, 0); nearY < Math.min(y + 2, 8); nearY++) {

                if (list.findChessPiece(color, nearX, nearY) == null) {

                    if (validateSafe(nearX, nearY)) {

                        return false;

                    }

                }

            }

        }

        /*
        | If that fails, check if any of the other pieces of the king's color can block the advance
        | of the checking piece. This is only possible if the checking piece is a Rook, Bishop, or
        | Queen
        */
        if (checkingPiece.getType() == ChessType.ROOK
        ||  checkingPiece.getType() == ChessType.BISHOP
        ||  checkingPiece.getType() == ChessType.QUEEN) {

            calculateDistance(checkingPiece.getX(), checkingPiece.getY());

            if (xDist + yDist != 0) {

                for (ChessPiece piece : list.getEntities()) {

                    iterX = x;
                    iterY = y;

                    checkX = checkingPiece.getX();
                    checkY = checkingPiece.getY();

                    while (iterX != checkX || iterY != checkY) {

                        if (iterX > checkX) {

                            iterX--;

                        } else if (iterX < checkX) {

                            iterX++;

                        }

                        if (iterY > checkY) {

                            iterY--;

                        } else if (iterY < checkY) {

                            iterY++;

                        }

                        if (iterX != checkX || iterY != checkY) {

                            if (piece.validateMove(iterX, iterY)) {

                                return false;

                            }

                        }

                    }

                }

            }

        }

        // If both checks fail, theres no valid moves and a checkmate must be declared.
        return true;

    }

    // Check if any possible moves are remaining for the King's color.
    // Returns true if yes, false if no.
    public boolean validatePossibleMoves() {

        for (ChessPiece piece : list.getEntities()) {

            if (piece.getColor() == color) {

                for (int boardX = 0; boardX < 8; boardX++) {

                    for (int boardY = 0; boardY < 8; boardY++) {

                        if (piece.validateMove(boardX, boardY)) {

                            return true;

                        }

                    }

                }

            }

        }

        return false;

    }

    public void setGameEndListener(final EndListener listener) {

        endListener = listener;

    }

    // Set up references to the rooks for castling
    public void rookSetup() {

        leftRook = list.findChessPiece(color, 0, y);
        rightRook = list.findChessPiece(color, 7, y);

    }

}
