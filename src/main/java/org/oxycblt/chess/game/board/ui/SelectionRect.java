// Decorative rectangle shown when selecting something

package org.oxycblt.chess.game.board.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.animation.FadeAnimation;

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

        if (color == ChessType.WHITE) {

            setStroke(Color.web("eaeaea"));

        } else {

            setStroke(Color.web("252525"));

        }

        fadeAnim.fadeIn();
        isShown = true;

    }

    public void hide() {

        fadeAnim.fadeOut();
        isShown = false;

    }

    public void hideNoAnim() {

        setStroke(Color.TRANSPARENT);
        isShown = true;

    }

    public boolean getShown() {

        return isShown;

    }

}
