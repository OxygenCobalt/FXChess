// Factory for generating chess pieces

package org.oxycblt.chess.game.board.pieces;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;

public class ChessFactory {

    private final ChessList pieces;
    private ChessType color = ChessType.WHITE;

    private King lastGenKing = null;

    public ChessFactory(final ChessList pieces) {

        this.pieces = pieces;

    }

    // Generate a row of pawns
    public void genPawnRow(final int y) {

        for (int i = 0; i < 8; i++) {

            addAt(i, y, ChessType.PAWN);

        }

    }

    // Generate the back row of other non-pawn pieces
    public void genHomeRow(final int y) {

        for (int i = 0; i < 8; i++) {

            addAt(i, y, ChessType.HOME_ORDER[i]);

        }

        // Also set up the king's references to the rooks for the castling move
        lastGenKing.setUpRooks();

    }

    // Add a piece
    public void addAt(final int x, final int y, final ChessType type) {

        // Create the given types respective piece at the x/y coords
        switch (type) {

            case PAWN: new Pawn(pieces, color, x, y); break;
            case ROOK: new Rook(pieces, color, x, y); break;
            case KNIGHT: new Knight(pieces, color, x, y); break;
            case BISHOP: new Bishop(pieces, color, x, y); break;
            case QUEEN: new Queen(pieces, color, x, y); break;
            case KING: lastGenKing = new King(pieces, color, x, y); break;

        }

    }

    // Replace a piece
    public void replaceAt(final int x, final int y, final ChessType type) {

        pieces.removeEntity(pieces.findChessPiece(x, y));
        addAt(x, y, type);

    }

    // Update the color of the generated pieces
    public void setColor(final ChessType newColor) {

        ChessType.validateColor(color);

        if (newColor != color) {

            color = newColor;

        }

    }

}