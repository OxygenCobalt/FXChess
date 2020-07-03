// Pane that houses the chess pieces and handles their interactions

package org.oxycblt.chess.game.board;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;
import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.pieces.Pawn;
import org.oxycblt.chess.game.board.pieces.ChessPiece;

public class BoardPane extends Pane {

    private ChessList pieces;

    private Rectangle2D mouseRect;
    private ChessPiece selectedPiece;

    public BoardPane() {

        // W/H/X/Y are static
        relocate(33, 49);
        setPrefSize(256, 256);
        setOnMouseClicked(mouseClickHandler);
        setStyle(
              "-fx-border-style: solid outside;"
            + "-fx-border-width: 6px;"
            + "-fx-border-color: #8F8F8F"
        );

        pieces = new ChessList();
        mouseRect = new Rectangle2D(
            getLayoutX(),
            getLayoutY(),
            getPrefWidth(),
            getPrefHeight()
        );

        generateCheckerBoard();
        generateChessPieces();

    }

    EventHandler<MouseEvent> mouseClickHandler = event -> {

        MouseButton button = event.getButton();

        // Left mouse button to select a chess piece/confirm chess piece movement
        // Right mouse button to deselect a chess piece
        if (button == MouseButton.PRIMARY) {

            // Normalize the pointer so that only
            // the pointer positions in the rectangle
            // are used
            int x = (int) (event.getSceneX() - getLayoutX());
            int y = (int) (event.getSceneY() - getLayoutY());

            if (x > 0 && x < getPrefWidth() && y > 0 && y < getPrefHeight()) {

                // Find a chess piece that matches the coordinates
                // --- TODO ---
                // and the current player turn,
                // ------------
                // and select that if there is one

                ChessPiece piece = pieces.findEntity(ChessType.WHITE, x / 32, y / 32);

                if (piece != null && piece != selectedPiece) {

                    // If no piece is selected, select the one clicked.
                    // If a piece is selected, deselect that one and select the one clicked.
                    if (selectedPiece == null) {

                        piece.setSelected(true);

                    } else {

                        selectedPiece.setSelected(false);
                        piece.setSelected(true);

                    }

                    selectedPiece = piece;

                } else if (selectedPiece != null) {

                    // --- TODO ---
                    // If an empty square is pressed while a piece is selected,
                    // confirm the last valid move as long as the mouse hasnt
                    // strayed *too far* from the destination square
                    // ------------

                    selectedPiece.confirmMove(x, y);

                    selectedPiece = null;

                }

            }

        } else if (button == MouseButton.SECONDARY && selectedPiece != null) {

            selectedPiece.setSelected(false);

            selectedPiece = null;

        }

    };

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

                getChildren().add(checkerRect);

            }

        }

    }

    private void generateChessPieces() {

        Pawn pawn1 = new Pawn(pieces, ChessType.BLACK, 0, 1);
        Pawn pawn2 = new Pawn(pieces, ChessType.WHITE, 0, 6);
        Pawn pawn3 = new Pawn(pieces, ChessType.WHITE, 1, 6);

        getChildren().addAll(pawn1, pawn2, pawn3);

    }

}
