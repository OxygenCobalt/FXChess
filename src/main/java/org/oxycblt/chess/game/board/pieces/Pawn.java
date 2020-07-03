// Pawn chess piece

package org.oxycblt.chess.game.board.pieces;

import java.util.ArrayList;

public class Pawn extends ChessPiece {

    public Pawn(final ArrayList<ChessPiece> list,
                final ChessType color,
                final int x, final int y) {

        super(list, ChessType.PAWN, color, x, y);

    }

    @Override
    boolean validateMove() {

        return true;

    }

    @Override
    void update() {



    }

}
