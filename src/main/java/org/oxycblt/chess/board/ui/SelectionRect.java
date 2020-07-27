// Decorative rectangle shown when selecting something

package org.oxycblt.chess.board.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import org.oxycblt.chess.shared.ChessType;

import org.oxycblt.chess.board.animation.FadeAnimation;

public class SelectionRect extends Rectangle {

    private FadeAnimation fadeAnim;
    private boolean isShown;

    public SelectionRect(final ChessType color, final int x, final int y) {

        super(32, 32);

        relocate(x * 32, y * 32);
        setFill(Color.TRANSPARENT);

        fadeAnim = new FadeAnimation(this);

        show(color);
        setStrokeWidth(3);
        setStrokeType(StrokeType.INSIDE);

    }

    public void show(final ChessType color) {

        if (!isShown) {

            setStroke(Color.web(ChessType.toHex(color)));

            fadeAnim.fadeIn();
            isShown = true;

        }

    }

    public void hide() {

        if (isShown) {

            fadeAnim.fadeOut();
            isShown = false;

        }

    }

    public void hideNoAnim() {

        if (isShown) {

            setStroke(Color.TRANSPARENT);
            isShown = true;

        }

    }

}
