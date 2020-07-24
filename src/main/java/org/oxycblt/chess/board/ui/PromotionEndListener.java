// Required interface for detecting when the PromotionMenu gets an option confirmed

package org.oxycblt.chess.board.ui;

import org.oxycblt.chess.shared.ChessType;

public interface PromotionEndListener {

    void onPromotionEnd(ChessType newType);

}