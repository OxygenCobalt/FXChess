// Main chess game scene

package org.oxycblt.chess.game;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import org.oxycblt.chess.game.board.BoardPane;

public class ChessScene extends Scene {

    private final Group root;
    private BoardPane chess;

    private ChessType turn;

    public ChessScene(final Group root) {

        super(root, 322, 354);
        setFill(Color.rgb(50, 50, 50));

        this.root = root;

        chess = new BoardPane();

        root.getChildren().addAll(chess);

        // TODO: Determine the first turn randomly
        turn = ChessType.BLACK;

    }

}
