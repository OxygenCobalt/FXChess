// Animation for checking

package org.oxycblt.chess.game.board.animation;

import javafx.animation.AnimationTimer;

import org.oxycblt.chess.game.board.pieces.King;

public class CheckAnimation extends AnimationTimer {

    private King king;

    private int offset = -1;
    private int ticks = 0;

    public CheckAnimation(final King king) {

        this.king = king;

    }

    public void handle(final long now) {

        ticks++;

        if (ticks == 5) {

            offset = offset == -1 ? 1 : -1;

            king.offsetView(offset);

            ticks = 0;

        }

    }

}
