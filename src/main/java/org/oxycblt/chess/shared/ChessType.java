// Enum of all chess piece types, chess colors, and player turns. Also has helper functions.

package org.oxycblt.chess.shared;

import java.util.Random;

public enum ChessType {

    // Each enum stores their coordinates on TextureAtlas
    PAWN(0), ROOK(1), KNIGHT(2), BISHOP(3), QUEEN(4), KING(5),

    BLACK(0), WHITE(1);

    public static final ChessType[] HOME_ORDER = {
        ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK
    };

    public static final ChessType[] PROMOTION_ORDER = {
        ROOK, KNIGHT, BISHOP, QUEEN
    };

    private final int coordinate;
    private static Random rand = new Random();

    ChessType(final int coordinate) {

        this.coordinate = coordinate;

    }

    public int getCoordinate() {

        return coordinate;

    }

    public static ChessType randomColor() {

        if (rand.nextBoolean()) {

            return WHITE;

        }

        return BLACK;

    }

    // Return the opposite of the given color
    public static ChessType inverseOf(final ChessType color) {

        validateColor(color);

        return color == WHITE ? BLACK : WHITE;
    }

    // Turn a chesstype color into a hex color
    public static String toHex(final ChessType color) {

        validateColor(color);

        if (color == WHITE) {

            return "eaeaea";

        }

        return "252525";

    }

    // Function used to make sure that a given color is actually BLACK or WHITE
    public static void validateColor(final ChessType color) {

        if (color != ChessType.BLACK && color != ChessType.WHITE) {

            throw new IllegalArgumentException("Expected BLACK or WHITE, got " + color.toString());

        }

    }

}
