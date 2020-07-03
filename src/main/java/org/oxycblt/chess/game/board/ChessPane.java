// Pane that houses the chess pieces and handles their interactions

package org.oxycblt.chess.game.board;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.oxycblt.chess.game.board.pieces.Pawn;
import org.oxycblt.chess.game.board.pieces.ChessType;

public class ChessPane extends Pane {

    public ChessPane() {

        // W/H/X/Y are static
        relocate(22, 22);
        setPrefSize(256, 256);

        generateCheckerBoard();
        generateChessPieces();

    }

    // Generate the decorative checker rectangles
    private void generateCheckerBoard() {

        Rectangle checkerRect;

        for (int x = 0; x <= 224; x += 32) {

            for (int y = 0; y <= 224; y += 32) {

                checkerRect = new Rectangle(x, y, 32, 32);

                // If the added simple coordinates [E.G 4X + 7Y]
                // is divisible by two, than that checker must be
                // a lighter one. Otherwise it needs to be a dark one
                if (((x / 32) + (y / 32)) % 2 == 0) {

                     checkerRect.setFill(Color.web("9F9F9F"));

                } else {

                    checkerRect.setFill(Color.web("5F5F5F"));

                }

                getChildren().add(

                    checkerRect

                );

            }

        }

    }

    private void generateChessPieces() {

        getChildren().add(new Pawn(ChessType.BLACK, 0, 0));

    }

}
