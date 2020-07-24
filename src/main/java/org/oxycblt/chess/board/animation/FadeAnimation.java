// Basic fade-in animation for SelectionRect, EndScreen, & PromotionMenu

package org.oxycblt.chess.board.animation;

import javafx.scene.Node;
import javafx.util.Duration;
import javafx.animation.FadeTransition;

public class FadeAnimation {

    private FadeTransition internalAnim;

    public FadeAnimation(final Node node) {

        internalAnim = new FadeTransition(Duration.seconds(0.2));
        internalAnim.setNode(node);

    }

    public void fadeIn() {

        internalAnim.stop();

        internalAnim.setFromValue(0.0);
        internalAnim.setToValue(1.0);

        internalAnim.play();

    }

    public void fadeOut() {

        internalAnim.stop();

        internalAnim.setFromValue(1.0);
        internalAnim.setToValue(0.0);

        internalAnim.play();

    }

}
