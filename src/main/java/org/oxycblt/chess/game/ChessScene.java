// Main chess game scene

package org.oxycblt.chess.game;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import org.oxycblt.chess.game.board.ChessPane;

public class ChessScene extends Scene {

    private final Group root;

    private final ChessPane chess;

    public ChessScene(final Group root) {

        super(root, 500, 500);
        setFill(Color.rgb(192, 192, 192));

        this.root = root;

        chess = new ChessPane();

        root.getChildren().addAll(chess);

    }

}
