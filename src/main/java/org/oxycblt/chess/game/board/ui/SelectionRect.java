// Decorative rectangle shown when selecting something

package org.oxycblt.chess.game.board.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.animation.SelectionAnimation;

public class SelectionRect extends Rectangle {

    SelectionAnimation selectAnim;

    public SelectionRect(final ChessType color, final int x, final int y) {

        super(32, 32);

        relocate(x * 32, y * 32);
        setFill(Color.TRANSPARENT);

        show(color);
        setStrokeWidth(3);
        setStrokeType(StrokeType.INSIDE);

    }

    public void show(final ChessType color) {

        if (selectAnim == null) {

            selectAnim = new SelectionAnimation(this);

        }

        selectAnim.fadeIn(color);

    }

    public void hide() {

        selectAnim.fadeOut();

    }

    public void hideNoAnim() {

        setStroke(Color.TRANSPARENT);

    }

}
