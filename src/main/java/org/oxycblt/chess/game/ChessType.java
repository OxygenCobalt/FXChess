// Enum of all chess piece types, chess colors, and player turns.

package org.oxycblt.chess.game;

public enum ChessType {

    // Each enum stores their coordinates on TextureAtlas
    // X values for chess piece types
    PAWN(0), ROOK(1), KNIGHT(2), BISHOP(3), QUEEN(4), KING(5),

    // Y values for colors
    BLACK(0), WHITE(1);

    private final int coordinate;

    ChessType(final int coordinate) {

        this.coordinate = coordinate;

    }

    public int getTextureCoordinate() {

        return coordinate;

    }

    // Return the opposite of the given color
    public static ChessType inverseOf(final ChessType color) {

        if (color != ChessType.BLACK && color != ChessType.WHITE) {

            throw new IllegalArgumentException("Chess color is not BLACK or WHITE");

        }

        return color == WHITE ? BLACK : WHITE;
    }

}
