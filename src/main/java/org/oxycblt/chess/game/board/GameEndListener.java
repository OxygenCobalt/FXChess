// Interface for listening for the end of the game

package org.oxycblt.chess.game.board;

import org.oxycblt.chess.game.ChessType;

public interface GameEndListener {

    void onEnd(ChessType color, EndType type);

    enum EndType {

        CHECKMATE, STALEMATE, DRAW

    }

}
