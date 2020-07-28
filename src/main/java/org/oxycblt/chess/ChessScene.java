// Main chess game scene

package org.oxycblt.chess;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;

import org.oxycblt.chess.board.BoardPane;
import org.oxycblt.chess.menu.MenuPane;

public class ChessScene extends Scene {

    public ChessScene(final Group root) {

        super(root, 322, 354);
        setFill(new LinearGradient(
            0, 19, 0, 355, false,
            CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("252525")),
            new Stop(1, Color.web("eaeaea"))
        ));

        MenuPane menu = new MenuPane();
        BoardPane board = new BoardPane(menu);

        root.getChildren().addAll(menu, board);

    }

}
