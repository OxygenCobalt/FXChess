// Animation for SelectionRect

package org.oxycblt.chess.game.board.animation;

import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.animation.StrokeTransition;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ui.SelectionRect;

public class SelectionAnimation {

    private StrokeTransition internalAnim;
    private Color currentColor;

    public SelectionAnimation(final SelectionRect selectRect) {

        internalAnim = new StrokeTransition(Duration.seconds(0.2), selectRect);

    }

    public void fadeIn(final ChessType color) {

        ChessType.validateColor(color);

        if (color == ChessType.WHITE) {

            currentColor = Color.web("eaeaea");

        } else {

            currentColor = Color.web("252525");

        }

        internalAnim.stop();

        internalAnim.setFromValue(Color.TRANSPARENT);
        internalAnim.setToValue(currentColor);

        internalAnim.play();

    }

    public void fadeOut() {

        internalAnim.stop();

        internalAnim.setFromValue(currentColor);
        internalAnim.setToValue(Color.TRANSPARENT);

        internalAnim.play();

    }

}
