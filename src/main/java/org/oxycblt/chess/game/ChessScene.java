// Main chess game scene

package org.oxycblt.chess.game;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import org.oxycblt.chess.game.board.BoardPane;

public class ChessScene extends Scene {

    public ChessScene(final Group root) {

        super(root, 276, 374);
        setFill(Color.rgb(50, 50, 50));

        BoardPane chess = new BoardPane();

        root.getChildren().addAll(chess);

    }

}
