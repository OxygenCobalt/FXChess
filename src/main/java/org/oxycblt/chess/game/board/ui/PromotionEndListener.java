// Required interface for detecting when the PromotionMenu gets an option confirmed

package org.oxycblt.chess.game.board.ui;

import org.oxycblt.chess.game.ChessType;

public interface PromotionEndListener {

    void onPromotionEnd(ChessType newType);

}
