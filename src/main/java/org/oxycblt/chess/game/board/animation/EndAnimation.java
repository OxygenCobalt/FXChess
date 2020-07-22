// Slide + Fade animation used by EndScreen

package org.oxycblt.chess.game.board.animation;

import javafx.util.Duration;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.scene.shape.MoveTo;
import javafx.animation.PathTransition;

import org.oxycblt.chess.game.board.ui.EndScreen;

public class EndAnimation extends FadeAnimation {

    private PathTransition internalSlide;

    public EndAnimation(final EndScreen screen) {

        super(screen);

        internalSlide = new PathTransition();
        internalSlide.setNode(screen);
        internalSlide.setDuration(Duration.seconds(0.2));

    }

    public void in() {

        internalSlide.setPath(
            new Path(
                new MoveTo(64, 6),
                new VLineTo(-16)
            )
        );

        fadeIn();
        internalSlide.play();

    }

    public void out() {

        internalSlide.setPath(
            new Path(
                new MoveTo(64, -10),
                new VLineTo(16)
            )
        );

        fadeOut();
        internalSlide.play();

    }

}
