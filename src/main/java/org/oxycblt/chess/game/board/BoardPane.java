// Pane that houses the chess pieces and handles their interactions

package org.oxycblt.chess.game.board;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.oxycblt.chess.game.board.pieces.Pawn;
import org.oxycblt.chess.game.board.pieces.ChessType;
import org.oxycblt.chess.game.board.pieces.ChessPiece;

public class BoardPane extends Pane {

    private final ArrayList<ChessPiece> pieces;

    public BoardPane() {

        // W/H/X/Y are static
        relocate(33, 49);
        setPrefSize(256, 256);
        setStyle(

              "-fx-border-style: solid outside;"
            + "-fx-border-width: 6px;"
            + "-fx-border-color: #8F8F8F"

        );

        pieces = new ArrayList<ChessPiece>();

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

        Pawn pawn1 = new Pawn(pieces, ChessType.BLACK, 0, 1);
        Pawn pawn2 = new Pawn(pieces, ChessType.WHITE, 0, 6);

        getChildren().addAll(pawn1, pawn2);

    }

}
