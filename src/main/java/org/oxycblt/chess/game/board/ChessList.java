// List of chess pieces
// TODO: Depending on what background you choose, this will likely need to
// be fragmented into an abstract class

package org.oxycblt.chess.game.board;

import java.util.ArrayList;
import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.pieces.ChessPiece;

public class ChessList {

    private final ArrayList<ChessPiece> pieces;

    public ChessList() {

        pieces = new ArrayList<ChessPiece>();

    }

    public void addEntity(final ChessPiece entity) {

        pieces.add(entity);

    }

    public void removeEntity(final ChessPiece entity) {

        pieces.remove(entity);

    }

    public ChessPiece findEntity(final ChessType color, final int x, final int y) {

        for (ChessPiece piece : pieces) {

            if (piece.isMatching(color, x, y)) {

                return piece;

            }

        }

        return null;

    }

    public int size() {

        return pieces.size();

    }

}
