// King Chess Piece

package org.oxycblt.chess.game.board.pieces;

import java.util.ArrayList;
import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;

public class King extends ChessPiece {

    private ChessPiece leftRook;
    private ChessPiece rightRook;

    private ArrayList<ChessPiece> checkingPieces;

    private int iterX = 0;

    public King(final ChessList list,
                final ChessType color,
                final int x, final int y) {

        super(list, ChessType.KING, color, x, y);

        checkingPieces = new ArrayList<ChessPiece>();

    }

    @Override
    public boolean validateMove(final int targetX, final int targetY) {

        /*
        | Kings can move in all directions, as long as the distance moved is one.
        | Kings can also perform a move called castling with a rook, which involve
        | the king moving two spaces, and the rook moving to the space that the king passed.
        */

        calculateDistance(targetX, targetY);

        deselectRooks();

        // The absolute x distance and the normal x distance are kept as seperate variables
        // to determine which rook to use during castling
        xDist = Math.abs(xDist);
        yDist = Math.abs(yDist);

        if (xDist + yDist < 2) {

            return true;

        } else if (xDist + yDist == 2) {

            if (xDist == yDist) {

                return true;

            }

        }

        /*
        | Castling Logic
        |
        | The conditions for castling to be valid are:
        | - The king must not have moved or is in check
        | - The rooks must have not moved
        | - There must be no pieces in the path between the king & the rook
        | - The king must not pass through any pieces that are under attack, or result in a check
        |
        | Yes, I know this code is awful but it works.
        */
        if (yDist == 0 && !hasMoved && checkingPieces.size() == 0) {

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

    }

    // Validate that a prospective move is safe
    private boolean validateSafe(final int targetX, final int targetY) {

        for (ChessPiece entity : list.getEntities()) {

            if (entity.getColor() != color) {

                if (entity.validateMove(targetX, targetY)) {

                    return false;

                }

            }

        }

        return true;

    }

    // Validate that a path is safe and wont result in a check, used for castling
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

    // Notify king of the rooks after the home row has been generated
    public void setUpRooks() {

        leftRook = list.findChessPiece(color, 0, y);
        rightRook = list.findChessPiece(color, 7, y);

    }

    public void deselectRooks() {

        leftRook.setSelected(false);
        rightRook.setSelected(false);

    }

}
