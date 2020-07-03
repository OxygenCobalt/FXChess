// Pawn chess piece

package org.oxycblt.chess.game.board.pieces;

public class Pawn extends ChessPiece {

    public Pawn(final ChessType color, final int x, final int y) {

        super(ChessType.PAWN, color, x, y);

    }

    @Override
    boolean validateMove() {

        return true;

    }

    @Override
    void update() {



    }

}
