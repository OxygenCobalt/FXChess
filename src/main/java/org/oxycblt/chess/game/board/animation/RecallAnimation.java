// Recall animation

package org.oxycblt.chess.game.board.animation;

import javafx.util.Duration;
import javafx.scene.shape.Path;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.animation.PathTransition;

import org.oxycblt.chess.game.board.pieces.ChessPiece;

public class RecallAnimation {

    PathTransition internalAnim;

    public RecallAnimation(final ChessPiece piece) {

        internalAnim = new PathTransition();

        internalAnim.setNode(piece);
        internalAnim.setDuration(Duration.seconds(0.3));

    }

    // Start the animation with the current position and the target coordinates
    public void start(final int nowX, final int nowY, final int x, final int y) {

        // Don't run if the piece hasn't meaningfully changed in position
        if (nowX != x || nowY != y) {

            /*
            | Recalculate the path to be taken based on the distance from the original position of
            | the piece and the current position of the piece.
            */
            internalAnim.setPath(
                new Path(
                    new MoveTo(16, 16),
                    new LineTo(
                        ((x + 16) - nowX),
                        ((y + 16) - nowY)
                    )
                )
            );

            internalAnim.play();

        }

    }

}
