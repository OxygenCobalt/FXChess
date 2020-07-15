// Interface for listening for a checkmate

package org.oxycblt.chess.game.board.pieces;

public interface GameEndListener {

    void onCheckmate(King loserKing);

}
