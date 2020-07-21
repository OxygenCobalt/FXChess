// Interface for listening for the end of the game

package org.oxycblt.chess.game.board;

import org.oxycblt.chess.game.ChessType;

public interface EndListener {

    void onEnd(ChessType color, EndType type);

    /*
    | Enum for types of game-end scenarios. The three I've implemented are:
    | - Checkmate, either from one move from opposing side or failure to counter check
    | - Stalemate, no moves possible on one sides turn.
    | - 50-Move rule, no captures or pawn moves in 50 moves -> automatic draw
    | - Five-Fold repetition, if the same position occurs five times in a row -> automatic draw
    | There are other ways a game of chess can end, but Ive chosen not to implement
    | them as FXChess is a learning project, not a fully-featured chess simulator.
    */
    enum EndType {

        CHECKMATE(0), STALEMATE(1), DRAW(2);

        private final int coordinate;

        EndType(final int coordinate) {

            this.coordinate = coordinate;

        }

        public int getCoordinate() {

            return coordinate;

        }

    }

}
