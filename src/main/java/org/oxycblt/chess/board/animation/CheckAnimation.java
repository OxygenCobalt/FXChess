// Animation for checking

package org.oxycblt.chess.board.animation;

import javafx.animation.AnimationTimer;

import org.oxycblt.chess.board.pieces.King;

public class CheckAnimation extends AnimationTimer {

    private King king;

    private int offset = -1;
    private int ticks = 0;

    public CheckAnimation(final King king) {

        this.king = king;

    }

    // Main AnimationTimer loop
    public void handle(final long now) {

        ticks++;

        /*
        | Every five ticks [due AnimationTimers being called quite rapidly], move the King back
        | and forward to create a shaking effect.
        */
        if (ticks == 5) {

            offset = offset == -1 ? 1 : -1;

            king.offsetView(offset);

            ticks = 0;

        }

    }

}
