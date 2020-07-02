// Board that houses the chess pieces and handles their interactions

package org.oxycblt.chess.game.board;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChessPane extends Pane {

    public ChessPane() {

        // W/H/X/Y are static
        relocate(50, 50);
        setPrefSize(350, 350);

        generateCheckerBoard();

    }

    // Generate the decorative checker rectangles
    private void generateCheckerBoard() {

        Rectangle checkerRect;

        for (int x = 0; x <= 350; x += 50) {

            for (int y = 0; y <= 350; y += 50) {

                checkerRect = new Rectangle(x, y, 50, 50);

                // If the added simple coordinates [E.G 4X + 7Y]
                // is divisible by two, than that checker must be
                // a lighter one. Otherwise it needs to be a dark one
                if (((x / 50) + (y / 50)) % 2 == 0) {

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

}
