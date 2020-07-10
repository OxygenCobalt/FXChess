// Enum of all chess piece types, chess colors, and player turns. Also has helper functions.

package org.oxycblt.chess.game;

public enum ChessType {

    // Each enum stores their coordinates on TextureAtlas
    // X values for chess piece types
    PAWN(0), ROOK(1), KNIGHT(2), BISHOP(3), QUEEN(4), KING(5),

    // Y values for colors
    BLACK(0), WHITE(1);

    public static final ChessType[] PIECE_ORDER = {
        ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, ROOK, KNIGHT
    };

    public static final ChessType[] PROMOTION_ORDER = {
        ROOK, KNIGHT, BISHOP, QUEEN
    };

    private final int coordinate;

    ChessType(final int coordinate) {

        this.coordinate = coordinate;

    }

    public int getCoordinate() {

        return coordinate;

    }

    // Return the opposite of the given color
    public static ChessType inverseOf(final ChessType color) {

        validateColor(color);

        return color == WHITE ? BLACK : WHITE;
    }

    // Function used to make sure that a given color is actually BLACK or WHITE
    public static void validateColor(final ChessType color) {

        if (color != ChessType.BLACK && color != ChessType.WHITE) {

            throw new IllegalArgumentException("Expected BLACK or WHITE, got " + color.toString());

        }

    }

}
